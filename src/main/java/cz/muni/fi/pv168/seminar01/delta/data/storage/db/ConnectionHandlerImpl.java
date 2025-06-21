package cz.muni.fi.pv168.seminar01.delta.data.storage.db;

import cz.muni.fi.pv168.seminar01.delta.error.StorageException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Connection handler which is aware of active transaction on managed connection
 */
public class ConnectionHandlerImpl implements ConnectionHandler {

    private final Connection connection;

    /**
     * Creates new handler over given connection
     * @param connection database connection
     */
    public ConnectionHandlerImpl(Connection connection) {
        this.connection = Objects.requireNonNull(connection, "Missing connection object");
    }

    @Override
    public Connection use() {
        return connection;
    }

    @Override
    public void close() {
        try {
            if (connection.getAutoCommit()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new StorageException("Unable close database connection", e);
        }
    }
}

