package com.quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// All the JDBC queries of the project are written in this class
public class DatabaseHelper {

    // Insert a new user into the users table
    public boolean registerUser(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            // This happens if the username already exists (UNIQUE column)
            System.out.println("Registration failed: " + e.getMessage());
            return false;
        }
    }

    // Check if username and password match a row in the users table
    public boolean loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        boolean found = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                found = true;
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return found;
    }

    // Get 5 random questions from the questions table
    public ArrayList<Question> getQuestions() {
        ArrayList<Question> list = new ArrayList<Question>();
        String sql = "SELECT * FROM questions ORDER BY RAND() LIMIT 5";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Question q = new Question(
                        rs.getInt("id"),
                        rs.getString("question_text"),
                        rs.getString("option_a"),
                        rs.getString("option_b"),
                        rs.getString("option_c"),
                        rs.getString("option_d"),
                        rs.getString("correct_option"));
                list.add(q);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error loading questions: " + e.getMessage());
        }
        return list;
    }

    // Save the score of a quiz attempt
    public void saveScore(String username, int score, int total) {
        String sql = "INSERT INTO quiz_results (username, score, total_questions) VALUES (?, ?, ?)";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setInt(2, score);
            ps.setInt(3, total);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println("Could not save score: " + e.getMessage());
        }
    }

    // Print all past scores of a user
    public void viewHistory(String username) {
        String sql = "SELECT score, total_questions, date_taken FROM quiz_results "
                + "WHERE username = ? ORDER BY date_taken DESC";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            boolean hasRows = false;
            System.out.println("\n--- Past Scores for " + username + " ---");
            while (rs.next()) {
                hasRows = true;
                System.out.println(rs.getTimestamp("date_taken") + "  ->  "
                        + rs.getInt("score") + " / " + rs.getInt("total_questions"));
            }
            if (!hasRows) {
                System.out.println("No quizzes taken yet.");
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error loading history: " + e.getMessage());
        }
    }

    // Returns past quiz scores as an ArrayList for the Swing GUI
    public ArrayList<QuizResult> getHistory(String username) {
        ArrayList<QuizResult> list = new ArrayList<QuizResult>();
        String sql = "SELECT score, total_questions, date_taken FROM quiz_results "
                + "WHERE username = ? ORDER BY date_taken DESC";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int score = rs.getInt("score");
                int total = rs.getInt("total_questions");
                String date = String.valueOf(rs.getTimestamp("date_taken"));
                list.add(new QuizResult(username, score, total, date));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error loading history: " + e.getMessage());
        }
        return list;
    }
}
