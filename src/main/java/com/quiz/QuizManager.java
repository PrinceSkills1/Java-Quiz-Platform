package com.quiz;

import java.util.ArrayList;
import java.util.Scanner;

// This class runs the quiz and calculates the marks
public class QuizManager {

    private DatabaseHelper db = new DatabaseHelper();

    public void startQuiz(String username, Scanner sc) {
        ArrayList<Question> questions = db.getQuestions();

        if (questions.size() == 0) {
            System.out.println("No questions found. Did you import schema.sql?");
            return;
        }

        int score = 0;

        // Show each question one by one
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);

            System.out.println("\nQ" + (i + 1) + ". " + q.getText());
            System.out.println("A) " + q.getOptionA());
            System.out.println("B) " + q.getOptionB());
            System.out.println("C) " + q.getOptionC());
            System.out.println("D) " + q.getOptionD());

            // Keep asking until the user enters A, B, C or D
            String answer = "";
            while (true) {
                System.out.print("Your answer (A/B/C/D): ");
                answer = sc.nextLine().trim().toUpperCase();
                if (answer.equals("A") || answer.equals("B")
                        || answer.equals("C") || answer.equals("D")) {
                    break;
                }
                System.out.println("Invalid input! Please enter A, B, C or D.");
            }

            // Compare with the correct answer
            if (answer.equals(q.getCorrectOption())) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong! Correct answer is " + q.getCorrectOption());
            }
        }

        // Show final score and save it
        System.out.println("\n===== Quiz Finished =====");
        System.out.println("Your score: " + score + " / " + questions.size());
        db.saveScore(username, score, questions.size());
    }
}
