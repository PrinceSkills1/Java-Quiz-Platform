package com.quiz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

// History screen displaying user's previous quiz scores in a JTable
public class HistoryFrame extends JFrame {

    public HistoryFrame(String username) {
        DatabaseHelper db = new DatabaseHelper();

        // Basic JFrame setup
        setTitle("Java Quiz Platform - Past Scores");
        setSize(520, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 15));
        mainPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        // Header label
        JLabel titleLabel = new JLabel("Past Scores for: " + username, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Define column headers for JTable
        String[] columnNames = {"Date Taken", "Score / Total"};

        // DefaultTableModel to hold tabular data (non-editable cells)
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Prevent user from editing table cells
            }
        };

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        // Retrieve past scores list from DatabaseHelper
        ArrayList<QuizResult> history = db.getHistory(username);

        // Populate table rows with each quiz attempt
        if (history != null && history.size() > 0) {
            for (int i = 0; i < history.size(); i++) {
                QuizResult result = history.get(i);
                tableModel.addRow(new Object[]{
                    result.getDate(),
                    result.getScore() + " / " + result.getTotal()
                });
            }
        }

        // Put table inside a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with status message (if empty) and Back button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout(5, 10));

        if (history == null || history.size() == 0) {
            JLabel emptyLabel = new JLabel("No quizzes taken yet.", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 13));
            bottomPanel.add(emptyLabel, BorderLayout.NORTH);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Back to Dashboard");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPanel.add(backButton);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Plain ActionListener for Back button
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
