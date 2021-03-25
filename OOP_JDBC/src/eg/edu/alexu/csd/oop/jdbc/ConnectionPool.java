package eg.edu.alexu.csd.oop.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

public class ConnectionPool {

    private static LinkedList<Connection> connectionPool = new LinkedList<>();
    private static LinkedList<Connection> usedConnections = new LinkedList<>();
    private int INITIAL_POOL_SIZE = 10;

    public void createConnections() throws SQLException {
        for (int i = 0; i < INITIAL_POOL_SIZE; i++)
            connectionPool.add(new implConnection());
    }

    public Connection getConnection() {
        if(connectionPool.size()==0)
            return null;
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    public Connection CurrentConnection(){
        return usedConnections.get(usedConnections.size()-1);
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }
}