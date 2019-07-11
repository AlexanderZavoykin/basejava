package com.gmail.aazavoykin.storage;

import com.gmail.aazavoykin.exception.ResumeAlreadyExistsStorageException;
import com.gmail.aazavoykin.exception.ResumeDoesNotExistStorageException;
import com.gmail.aazavoykin.exception.StorageException;
import com.gmail.aazavoykin.model.Resume;
import com.gmail.aazavoykin.sql.ConnectionFactory;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private final static Logger LOGGER = Logger.getLogger(AbstractStorage.class.getName());
    private final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new StorageException("DB driver si not found", null);
        }
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume")) {
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException("SQL connection problem", null, e);
        }
    }

    @Override
    public void save(Resume r) {
        LOGGER.info("save " + r);
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
        } catch (PSQLException e) {
            throw new ResumeAlreadyExistsStorageException(r.getUuid());
        } catch (SQLException e) {
            throw new StorageException("SQL connection problem", null, e);
        }
    }

    @Override
    public void update(Resume r) {
        LOGGER.info("update " + r);
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE resume SET uuid = ?, full_name = ? WHERE uuid = ?")) {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.setString(3, r.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new ResumeDoesNotExistStorageException(r.getUuid());
            }
        } catch (SQLException e) {
            throw new StorageException("SQL connection problem", null, e);
        }
    }

    @Override
    public Resume get(String uuid) {
        LOGGER.info("get " + uuid);
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new ResumeDoesNotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException("SQL connection problem", null, e);
        }
    }

    @Override
    public void delete(String uuid) {
        LOGGER.info("delete " + uuid);
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume WHERE uuid = ?")) {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new ResumeDoesNotExistStorageException(uuid);
            }
        } catch (SQLException e) {
            throw new StorageException("SQL connection problem", null, e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
            ResultSet rs = ps.executeQuery();
            List<Resume> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return list;

        } catch (SQLException e) {
            throw new StorageException("SQL connection problem", null, e);
        }
    }

    @Override
    public int size() {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM resume")) {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new StorageException("SQL connection problem", null, e);
        }
    }
}
