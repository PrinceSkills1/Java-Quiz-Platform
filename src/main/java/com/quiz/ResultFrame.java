package com.quiz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// Result screen showing final score, saving to database, and returning to dashboard
public class ResultFrame extends JFrame {

    private String username;
    private DatabaseHelper db;

    public ResultFrame(String username, int score, int total) {
        this.username = username;
        this.db = new DatabaseHelper();

        // Save score to database using DatabaseHelper.saveScore()
        db.saveScore(username, score, total);

        // Basic JFrame setup
        setTitle("Java Quiz Platform - Result");
        setSize(440, 340);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(15, 20));
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Header
        JLabel titleLabel = new JLabel("Quiz Finished!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Center panel for score information
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1, 8, 12));

        JLabel userLabel = new JLabel("User: " + username, SwingConstants.CENTER);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        JLabel scoreLabel = new JLabel("Your Final Score: " + score + " / " + total, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(new Color(0, 102, 204));

        JLabel statusLabel = new JLabel("Your score has been saved to the database.", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 13));
        statusLabel.setForeground(new Color(0, 128, 0)); // Green text

        centerPanel.add(userLabel);
        centerPanel.add(scoreLabel);
        centerPanel.add(statusLabel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom panel with Back button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Back to Dashboard");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Plain ActionListener to return to DashboardFrame
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DashboardFrame dashboard = new DashboardFrame(username);
                dashboard.setVisible(true);
                dispose();
            }
        });
    }
}
