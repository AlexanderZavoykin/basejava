package com.gmail.aazavoykin.storage;

import com.gmail.aazavoykin.exception.ResumeDoesNotExistStorageException;
import com.gmail.aazavoykin.exception.StorageException;
import com.gmail.aazavoykin.model.*;
import com.gmail.aazavoykin.sql.SqlHelper;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private final static Logger LOGGER = Logger.getLogger(AbstractStorage.class.getName());
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new StorageException("PostgreSQL Driver is not found");
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        LOGGER.info("save " + r);
        String query = "INSERT INTO resume (uuid, full_name) VALUES (?, ?)";
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            saveContacts(r, connection);
            saveSections(r, connection);
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        LOGGER.info("update " + r);
        String query = "UPDATE resume " +
                "SET full_name = ?" +
                "WHERE uuid = ?";
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                ps.execute();
                if (ps.executeUpdate() == 0) {
                    throw new ResumeDoesNotExistStorageException(r.getUuid());
                }
            }
            deleteContacts(r, connection);
            saveContacts(r, connection);
            deleteSections(r, connection);
            saveSections(r, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOGGER.info("get " + uuid);
        String query = "SELECT * FROM resume r " +
                "LEFT JOIN (SELECT type contact_type, value contact_value, resume_uuid FROM contact) c " +
                "ON r.uuid = c.resume_uuid " +
                "LEFT JOIN (SELECT type section_type, value section_value, resume_uuid FROM section) s " +
                "ON r.uuid = s.resume_uuid " +
                "WHERE r.uuid = ?";
        return sqlHelper.execute(query, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new ResumeDoesNotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                String contactValue = rs.getString("contact_value");
                if (contactValue != null) {
                    ContactType type = ContactType.valueOf(rs.getString("contact_type"));
                    resume.addContact(type, contactValue);
                }
                String sectionValue = rs.getString("section_value");
                if (sectionValue != null) {
                    getSection(resume, rs, "section_type", "section_value");
                }
            } while (rs.next());
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        LOGGER.info("delete " + uuid);
        String query = "DELETE FROM resume " +
                "WHERE uuid = ?";
        sqlHelper.execute(query, ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new ResumeDoesNotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        String resumeQuery = "SELECT * FROM resume " +
                "ORDER BY full_name, uuid";
        String contactQuery = "SELECT * FROM contact ";
        String sectionQuery = "SELECT * FROM section ";
        Map<String, Resume> resumeMap = sqlHelper.transactionalExecute(connection -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            try (PreparedStatement ps = connection.prepareStatement(resumeQuery)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    map.put(rs.getString("uuid"),
                            new Resume(rs.getString("uuid"), rs.getString("full_name")));
                }
            }

            try (PreparedStatement ps = connection.prepareStatement(contactQuery)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    map.get(uuid).addContact(ContactType.valueOf(rs.getString("type")),
                            rs.getString("value"));
                }
            }

            try (PreparedStatement ps = connection.prepareStatement(sectionQuery)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    getSection(map.get(uuid), rs, "type", "value");
                }
            }

            return map;
        });
        return new ArrayList<>(resumeMap.values());
    }

    @Override
    public int size() {
        String query = "SELECT COUNT(*) FROM resume";
        return sqlHelper.execute(query, ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }

    private void deleteContacts(Resume r, Connection connection) throws SQLException {
        String query = "DELETE FROM contact " +
                "WHERE resume_uuid = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private void saveContacts(Resume r, Connection connection) throws SQLException {
        String query = "INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().toString());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteSections(Resume r, Connection connection) throws SQLException {
        String query = "DELETE FROM section " +
                "WHERE resume_uuid = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private void saveSections(Resume r, Connection connection) throws SQLException {
        String query = "INSERT INTO section (resume_uuid, type, value) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            for (Map.Entry<SectionType, AbstractSection> e : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().toString());
                ps.setString(3, e.getValue().toString());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void getSection(Resume r, ResultSet rs, String typeName, String valueName) throws SQLException {
        String sectionValue = rs.getString(valueName);
        SectionType type = SectionType.valueOf(rs.getString(typeName));
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                r.addSection(type, new TextSection(sectionValue));
                break;
            case ACHIEVEMENT:
            case QUALIFICATION:
                List<String> skills = new ArrayList<>(Arrays.asList(sectionValue.split("\n")));
                r.addSection(type, new ListSection(skills));
                break;
        }
    }

}
