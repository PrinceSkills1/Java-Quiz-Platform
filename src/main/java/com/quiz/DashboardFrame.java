package com.quiz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// Dashboard screen displayed after a user successfully logs in
public class DashboardFrame extends JFrame {

    private String username;

    public DashboardFrame(String username) {
        this.username = username;

        // Basic JFrame configuration
        setTitle("Java Quiz Platform - Dashboard");
        setSize(420, 340);
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Container panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(15, 20));
        mainPanel.setBorder(new EmptyBorder(25, 35, 25, 35));

        // Welcome banner
        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        // Buttons grid (3 rows, 1 column)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 15));

        JButton startQuizButton = new JButton("Start Quiz");
        startQuizButton.setFont(new Font("Arial", Font.PLAIN, 15));

        JButton historyButton = new JButton("View Past Scores");
        historyButton.setFont(new Font("Arial", Font.PLAIN, 15));

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 15));

        buttonPanel.add(startQuizButton);
        buttonPanel.add(historyButton);
        buttonPanel.add(logoutButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Plain ActionListener to launch QuizFrame
        startQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuizFrame quizFrame = new QuizFrame(username);
                quizFrame.setVisible(true);
                dispose();
            }
        });

        // Plain ActionListener to view past scores
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HistoryFrame historyFrame = new HistoryFrame(username);
                historyFrame.setVisible(true);
                dispose();
            }
        });

        // Plain ActionListener to logout and return to LoginFrame
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
                dispose();
            }
        });
    }
}
