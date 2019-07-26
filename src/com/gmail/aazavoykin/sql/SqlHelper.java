package com.gmail.aazavoykin.util;

import com.gmail.aazavoykin.exception.ResumeAlreadyExistsStorageException;
import com.gmail.aazavoykin.exception.StorageException;
import com.gmail.aazavoykin.sql.ConnectionFactory;
import org.postgresql.util.PSQLException;

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
        } catch (PSQLException e) {
            // https://www.postgresql.org/docs/9.4/errcodes-appendix.html
            // 23505 unique_violation
            if (e.getSQLState().equals("23505")) {
                throw new ResumeAlreadyExistsStorageException(null);
            } else {
                throw new StorageException("PSQL exception " + e.getSQLState(), null);
            }

        } catch (SQLException e) {
            throw new StorageException("SQL connection problem", null, e);

        }
    }
}