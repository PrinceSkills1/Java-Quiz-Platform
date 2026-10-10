package com.quiz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
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
import java.util.ArrayList;

// Quiz screen: displays 5 questions one by one with radio button options
public class QuizFrame extends JFrame {

    // User details and state
    private String username;
    private DatabaseHelper db;
    private ArrayList<Question> questions;
    private int currentIndex; // Current question index (0 to 4)
    private int score;        // Simple int variable to track total score

    // Swing GUI Components
    private JLabel progressLabel;
    private JTextArea questionArea;
    private JRadioButton optionARadio;
    private JRadioButton optionBRadio;
    private JRadioButton optionCRadio;
    private JRadioButton optionDRadio;
    private ButtonGroup optionsGroup;
    private JButton nextButton;
    private JLabel warningLabel;

    public QuizFrame(String username) {
        this.username = username;
        this.db = new DatabaseHelper();
        this.questions = db.getQuestions();
        this.currentIndex = 0;
        this.score = 0; // Initialize score to 0

        // JFrame setup
        setTitle("Java Quiz Platform - Quiz");
        setSize(600, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // If no questions exist in database, display error message
        if (questions == null || questions.size() == 0) {
            JPanel emptyPanel = new JPanel(new BorderLayout(10, 10));
            emptyPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
            JLabel errorMsg = new JLabel("No questions available. Please import schema.sql.", SwingConstants.CENTER);
            JButton backBtn = new JButton("Back to Dashboard");
            backBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new DashboardFrame(username).setVisible(true);
                    dispose();
                }
            });
            emptyPanel.add(errorMsg, BorderLayout.CENTER);
            emptyPanel.add(backBtn, BorderLayout.SOUTH);
            add(emptyPanel);
            return;
        }

        // Main layout panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 15));
        mainPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        // Top progress indicator (e.g., "Question 1 of 5")
        progressLabel = new JLabel("", SwingConstants.LEFT);
        progressLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(progressLabel, BorderLayout.NORTH);

        // Center section: question text + 4 radio button options
        JPanel centerPanel = new JPanel(new BorderLayout(10, 15));

        // Question text (using non-editable JTextArea for automatic word-wrap)
        questionArea = new JTextArea();
        questionArea.setFont(new Font("Arial", Font.PLAIN, 15));
        questionArea.setWrapStyleWord(true);
        questionArea.setLineWrap(true);
        questionArea.setOpaque(false);
        questionArea.setEditable(false);
        questionArea.setFocusable(false);
        centerPanel.add(questionArea, BorderLayout.NORTH);

        // Panel for radio buttons A, B, C, D
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 5, 10));
        optionARadio = new JRadioButton();
        optionBRadio = new JRadioButton();
        optionCRadio = new JRadioButton();
        optionDRadio = new JRadioButton();

        optionARadio.setFont(new Font("Arial", Font.PLAIN, 14));
        optionBRadio.setFont(new Font("Arial", Font.PLAIN, 14));
        optionCRadio.setFont(new Font("Arial", Font.PLAIN, 14));
        optionDRadio.setFont(new Font("Arial", Font.PLAIN, 14));

        // ButtonGroup ensures only one option can be selected at a time
        optionsGroup = new ButtonGroup();
        optionsGroup.add(optionARadio);
        optionsGroup.add(optionBRadio);
        optionsGroup.add(optionCRadio);
        optionsGroup.add(optionDRadio);

        optionsPanel.add(optionARadio);
        optionsPanel.add(optionBRadio);
        optionsPanel.add(optionCRadio);
        optionsPanel.add(optionDRadio);

        centerPanel.add(optionsPanel, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom section: warning message and Next / Submit button
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));

        warningLabel = new JLabel(" ", SwingConstants.CENTER);
        warningLabel.setForeground(Color.RED);
        warningLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        bottomPanel.add(warningLabel, BorderLayout.NORTH);

        JPanel buttonBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        buttonBar.add(nextButton);
        bottomPanel.add(buttonBar, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Display the first question
        showQuestion(0);

        // Plain ActionListener for Next/Submit button (anonymous inner class)
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNext();
            }
        });
    }

    // Populate question text and options onto GUI components
    private void showQuestion(int index) {
        Question q = questions.get(index);

        // Show "Question 1 of 5", "Question 2 of 5", etc.
        progressLabel.setText("Question " + (index + 1) + " of " + questions.size());
        questionArea.setText(q.getText());
        optionARadio.setText("A) " + q.getOptionA());
        optionBRadio.setText("B) " + q.getOptionB());
        optionCRadio.setText("C) " + q.getOptionC());
        optionDRadio.setText("D) " + q.getOptionD());

        // Clear previous selection and warning
        optionsGroup.clearSelection();
        warningLabel.setText(" ");

        // On the final question, change button text from "Next" to "Submit"
        if (index == questions.size() - 1) {
            nextButton.setText("Submit");
        } else {
            nextButton.setText("Next");
        }
    }

    // Process answer, update score, and advance to next question or result
    private void handleNext() {
        String selected = "";
        if (optionARadio.isSelected()) {
            selected = "A";
        } else if (optionBRadio.isSelected()) {
            selected = "B";
        } else if (optionCRadio.isSelected()) {
            selected = "C";
        } else if (optionDRadio.isSelected()) {
            selected = "D";
        }

        // Check if an option was selected
        if (selected.isEmpty()) {
            warningLabel.setText("Please select an option before continuing!");
            return;
        }

        // Compare selected option with correct answer
        Question currentQ = questions.get(currentIndex);
        if (selected.equalsIgnoreCase(currentQ.getCorrectOption())) {
            score++;
        }

        currentIndex++;

        // If more questions remain, load the next one
        if (currentIndex < questions.size()) {
            showQuestion(currentIndex);
        } else {
            // Quiz finished: open ResultFrame which displays score and saves it
            ResultFrame resultFrame = new ResultFrame(username, score, questions.size());
            resultFrame.setVisible(true);
            dispose();
        }
    }
}
