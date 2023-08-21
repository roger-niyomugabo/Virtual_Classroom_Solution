package com.example.virtualclassroomsolution;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/next_j_scrud?schema=public";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "123456";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(DATABASE_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            // Log exception details
            Log.e("DatabaseError", "Error connecting to database: " + e);
            throw e; // Re-throw the exception
        }
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
