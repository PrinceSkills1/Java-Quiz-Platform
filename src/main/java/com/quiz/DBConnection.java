package com.quiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// This class gives us a connection to the MySQL database
public class DBConnection {

    public static Connection getConnection() {
        Connection con = null;
        try {
            // CHANGE THE PASSWORD HERE ("root123") to your own MySQL root password
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/quiz_db", "root", "root123");
        } catch (SQLException e) {
            System.out.println("Could not connect to database: " + e.getMessage());
        }
        return con;
    }
}
