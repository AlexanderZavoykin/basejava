package com.gmail.aazavoykin.storage;

import com.gmail.aazavoykin.exception.ResumeDoesNotExistStorageException;
import com.gmail.aazavoykin.model.ContactType;
import com.gmail.aazavoykin.model.Resume;
import com.gmail.aazavoykin.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private final static Logger LOGGER = Logger.getLogger(AbstractStorage.class.getName());
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
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
        String resumeQuery = "INSERT INTO resume (uuid, full_name) VALUES (?, ?)";
        String contactQuery = "INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)";
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(resumeQuery)) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            try (PreparedStatement ps = connection.prepareStatement(contactQuery)) {
                for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, e.getKey().toString());
                    ps.setString(3, e.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        LOGGER.info("update " + r);
        String resumeQuery = "UPDATE resume " +
                "SET full_name = ?" +
                "WHERE uuid = ?";
        String contactQuery = "UPDATE contact " +
                "SET type = ?, value = ?" +
                "WHERE resume_uuid = ?";
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(resumeQuery)) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                ps.execute();
                if (ps.executeUpdate() == 0) {
                    throw new ResumeDoesNotExistStorageException(r.getUuid());
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(contactQuery)) {
                for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                    ps.setString(1, e.getKey().toString());
                    ps.setString(2, e.getValue());
                    ps.setString(3, r.getUuid());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOGGER.info("get " + uuid);
        String query = "SELECT * FROM resume r " +
                "LEFT JOIN contact c " +
                "ON r.uuid = c.resume_uuid " +
                "WHERE r.uuid = ?";
        return sqlHelper.execute(query, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new ResumeDoesNotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            while (rs.next()) {
                String value = rs.getString("value");
                ContactType type = ContactType.valueOf(rs.getString("type"));
                resume.addContact(type, value);
            }
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
        List<Resume> resumeList = sqlHelper.execute(resumeQuery, ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return list;
        });

        String contactQuery = "SELECT * FROM contact";
        Map<String, Contact> contactMap = sqlHelper.execute(contactQuery, ps -> {
            ResultSet rs = ps.executeQuery();
            Map<String, Contact> map = new HashMap<>();
            while (rs.next()) {
                rs = ps.executeQuery();
                map.put(rs.getString("resume_uuid"),
                        new Contact(ContactType.valueOf(rs.getString("type")),
                                rs.getString("value")));
            }
            return map;
        });

        for (Resume r : resumeList) {
            if (contactMap.containsKey(r.getUuid())) {
                Contact contact = contactMap.get(r.getUuid());
                r.addContact(contact.getType(), contact.getValue());
            }
        }

        return resumeList;
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

    private class Contact {
        private ContactType type;
        private String value;

        public Contact(ContactType type, String value) {
            this.type = type;
            this.value = value;
        }

        public ContactType getType() {
            return type;
        }

        public String getValue() {
            return value;
        }
    }


}
