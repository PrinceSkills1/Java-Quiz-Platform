package com.quiz;

import java.util.Scanner;

// Main class: shows the menus and takes input from the user
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DatabaseHelper db = new DatabaseHelper();
        QuizManager quiz = new QuizManager();

        boolean running = true;

        // Initial menu loop
        while (running) {
            System.out.println("\n===== Java Quiz Platform =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Choose a username: ");
                    String newUser = sc.nextLine().trim();
                    System.out.print("Choose a password: ");
                    String newPass = sc.nextLine().trim();
                    if (newUser.isEmpty() || newPass.isEmpty()) {
                        System.out.println("Username and password cannot be empty.");
                    } else if (db.registerUser(newUser, newPass)) {
                        System.out.println("Registered successfully! Please login.");
                    }
                    break;

                case "2":
                    System.out.print("Username: ");
                    String username = sc.nextLine().trim();
                    System.out.print("Password: ");
                    String password = sc.nextLine().trim();
                    if (db.loginUser(username, password)) {
                        System.out.println("Welcome, " + username + "!");
                        userMenu(username, sc, db, quiz);
                    } else {
                        System.out.println("Wrong username or password.");
                    }
                    break;

                case "3":
                    System.out.println("Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
        sc.close();
    }

    // Menu shown after login
    public static void userMenu(String username, Scanner sc, DatabaseHelper db, QuizManager quiz) {
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. Start Quiz");
            System.out.println("2. View Past Scores");
            System.out.println("3. Logout");
            System.out.print("Enter choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    quiz.startQuiz(username, sc);
                    break;
                case "2":
                    db.viewHistory(username);
                    break;
                case "3":
                    System.out.println("Logged out.");
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
}
