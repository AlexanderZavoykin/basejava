package com.gmail.aazavoykin.storage;

import com.gmail.aazavoykin.exception.ResumeDoesNotExistStorageException;
import com.gmail.aazavoykin.model.ContactType;
import com.gmail.aazavoykin.model.Resume;
import com.gmail.aazavoykin.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
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
        String query = "INSERT INTO resume (uuid, full_name) VALUES (?, ?)";
        sqlHelper.execute(query, (ps) -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
            return null;
        });
        for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
            String q = "INSERT INTO contact (type, value, resume_uuid) VALUES (?, ?, ?)";
            sqlHelper.execute(q, ps -> {
                ps.setString(1, e.getKey().getTitle());
                ps.setString(2, e.getValue());
                ps.setString(3, r.getUuid());
                ps.execute();
                return null;
            });
        }
    }

    @Override
    public void update(Resume r) {
        LOGGER.info("update " + r);
        String query = "UPDATE resume SET full_name = ?\n" +
                "WHERE uuid = ?";
        sqlHelper.execute(query, ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new ResumeDoesNotExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOGGER.info("get " + uuid);
        String query = "SELECT * FROM resume WHERE uuid = ?\n" +
                "LEFT JOIN contact c\n" +
                "ON r.uuid = c.resume_uuid\n" +
                "WHERE r.uuid = ?";
        return sqlHelper.execute(query, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new ResumeDoesNotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                String value = rs.getString("value");
                ContactType type = ContactType.valueOf(rs.getString("type"));
                resume.addContact(type, value);
            } while (rs.next());
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        LOGGER.info("delete " + uuid);
        String query = "DELETE FROM resume\n" +
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
        String query = "SELECT * FROM resume\n" +
                "ORDER BY full_name, uuid";
        return sqlHelper.execute(query, ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return list;
        });
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
}
