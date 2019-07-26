package com.gmail.aazavoykin.sql;

import com.gmail.aazavoykin.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public interface SqlOperation<T> {
        T operate(PreparedStatement ps) throws SQLException;
    }

    public interface SqlTransaction<T> {
        T transact(Connection connection) throws SQLException;
    }

    public <T> T execute(String query, SqlOperation<T> sqlOperation) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return sqlOperation.operate(ps);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> sqlTransaction) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T result = sqlTransaction.transact(conn);
                conn.commit();
                return result;
            } catch (SQLException e) {
                conn.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

}