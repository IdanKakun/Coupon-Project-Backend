package com.idan.coupons.dao.connectionPool;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

//A class that defines the connection to the database.
public class ConnectionPool {


    private static final int NUMBER_OF_CONNECTIONS = 5;
    private static ConnectionPool instance = null;
    private final Stack<Connection> connections = new Stack<>();

    //Constructor- opens all available connections to the database when the instance created.
    private ConnectionPool() throws SQLException {
        System.out.println("Created new connection pool");
        openAllConnections();
    }

    //Returns a new instance of connectionPool.
    public static ConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    //Opens available connections to the database, the connections are pushed into the stack.
    private void openAllConnections() throws SQLException {
        for (int counter = 0; counter < NUMBER_OF_CONNECTIONS; counter++) {
            final Connection connection = DriverManager.getConnection(DbConfig.sqlUrl, DbConfig.sqlUser, DbConfig.sqlPassword);
            connections.push(connection);
        }
    }

    //Closes all available connections,they are removed from the stack.
    public void closeAllConnections() throws InterruptedException {
        synchronized (connections) {
            while (connections.size() < NUMBER_OF_CONNECTIONS) {
                connections.wait();
            }
            connections.removeAllElements();
        }
    }

    //Returns a connection and puts it in the stack if there is room in it.
    public Connection getConnection() throws InterruptedException {
        synchronized (connections) {
            if (connections.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + " is waiting for an available connection");
            }
            while (connections.isEmpty()) {
                connections.wait();
            }
            return connections.pop();
        }
    }

    //Restores an existing connection to the stack.
    public void returnConnection(final Connection connection) {
        synchronized (connections) {
            if (connection == null) {
                System.err.println("Attempt to return null connection terminated");
                return;
            }
            connections.push(connection);
            connections.notifyAll();
        }
    }
}

