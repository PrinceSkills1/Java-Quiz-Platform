package com.quiz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
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

// Login and Registration screen for the Quiz Platform
public class LoginFrame extends JFrame {

    // GUI Components
    private JTextField userField;
    private JPasswordField passField;
    private JLabel messageLabel;
    private DatabaseHelper db;

    public LoginFrame() {
        // Initialize DatabaseHelper to check credentials with MySQL
        db = new DatabaseHelper();

        // Basic JFrame configuration
        setTitle("Java Quiz Platform - Login");
        setSize(420, 320);
        setLocationRelativeTo(null); // Center window on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Terminate application on window close
        setResizable(false);

        // Main container panel with border layout and margin padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 15));
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        // Top title banner
        JLabel titleLabel = new JLabel("Java Quiz Platform", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Center form panel for username and password inputs
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2, 2, 10, 15));

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userField = new JTextField();
        userField.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passField = new JPasswordField();
        passField.setFont(new Font("Arial", Font.PLAIN, 14));

        formPanel.add(userLabel);
        formPanel.add(userField);
        formPanel.add(passLabel);
        formPanel.add(passField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Bottom section containing message label and buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout(5, 12));

        // Message label for showing feedback (success / error)
        messageLabel = new JLabel(" ", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        bottomPanel.add(messageLabel, BorderLayout.NORTH);

        // Button row
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Plain ActionListener for Login button (anonymous inner class)
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        // Plain ActionListener for Register button (anonymous inner class)
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });
    }

    // Authenticate user with database
    private void handleLogin() {
        String username = userField.getText().trim();
        String password = new String(passField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Please enter both username and password.");
            return;
        }

        boolean success = db.loginUser(username, password);
        if (success) {
            // Open Dashboard and close Login screen
            DashboardFrame dashboard = new DashboardFrame(username);
            dashboard.setVisible(true);
            dispose();
        } else {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Wrong username or password.");
        }
    }

    // Register a new user in database
    private void handleRegister() {
        String username = userField.getText().trim();
        String password = new String(passField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Username and password cannot be empty.");
            return;
        }

        boolean success = db.registerUser(username, password);
        if (success) {
            messageLabel.setForeground(new Color(0, 128, 0)); // Green text
            messageLabel.setText("Registered successfully! Please click Login.");
            passField.setText("");
        } else {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Registration failed. Username may already exist.");
        }
    }
}
