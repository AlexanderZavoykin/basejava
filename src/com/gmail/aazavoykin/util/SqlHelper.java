package com.gmail.aazavoykin.util;

import com.gmail.aazavoykin.exception.StorageException;
import com.gmail.aazavoykin.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public interface Operation<T> {
        T operate(PreparedStatement ps) throws SQLException;
    }

    public <T> T execute(String query, Operation<T> operation) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return operation.operate(ps);
        } catch (SQLException e) {
            throw new StorageException("SQL connection problem", null, e);
        }
    }
}
