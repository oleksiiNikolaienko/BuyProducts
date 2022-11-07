package service;

import exception.JdbcDaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDB {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/java_db";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_PASSWORD, DB_USERNAME);
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }
    }
}
