package com.tap.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        String dbHost = System.getenv("DB_HOST");

        if (dbHost != null && !dbHost.isBlank()) {
            // Render / Production
            String dbPort = System.getenv("DB_PORT");
            String dbName = System.getenv("DB_NAME");

            URL = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
            USERNAME = System.getenv("DB_USERNAME");
            PASSWORD = System.getenv("DB_PASSWORD");

        } else {
            // Local development
            URL = "jdbc:mysql://localhost:3306/tap_food";
            USERNAME = "root";
            PASSWORD = "Vaishnavi@123";
        }
    }

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );

        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }
}