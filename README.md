# Java Quiz Platform

A Java quiz application with both a Java Swing Graphical User Interface (GUI) and an optional console mode. Users can register, log in, take a 5-question Java quiz, view their score, save attempts to a MySQL database, and inspect their past scores. Built as a college project.

## Tech Stack
- Java 17
- Java Swing (GUI)
- JDBC (Java Database Connectivity)
- MySQL 8.0
- Apache Maven

## Prerequisites
- JDK 17 or newer installed (`java -version`)
- Maven installed (`mvn -version`)
- MySQL Server running on `localhost:3306`

## Setup
1. Import the database and sample questions into MySQL:
   ```powershell
   Get-Content schema.sql | mysql -u root -p
   ```
2. Open `src/main/java/com/quiz/DBConnection.java` and change the password (`root123`) to your own MySQL root password.

## Compile and Run

### Run the Swing GUI (Default)
```powershell
mvn clean compile
mvn exec:java "-Dexec.mainClass=com.quiz.Main"
```

### Run the Console CLI Version
```powershell
mvn exec:java "-Dexec.mainClass=com.quiz.ConsoleMain"
```

## Features
- **User Authentication**: Register new accounts with duplicate checking and secure login.
- **Swing Graphical User Interface**:
  - `LoginFrame`: User-friendly login and registration forms with status messages.
  - `DashboardFrame`: Post-login navigation to Start Quiz, View Past Scores, or Logout.
  - `QuizFrame`: Shows questions one-by-one with 4 options (A-D) using radio buttons, progress tracking ("Question 2 of 5"), selection validation, and score calculation.
  - `ResultFrame`: Displays final score, confirms score persistence to MySQL, and allows return to dashboard.
  - `HistoryFrame`: Displays past quiz attempts (Date Taken and Score / Total) in an interactive `JTable`.
- **Database Integration**: MySQL backend storing users, question bank, and historical quiz scores.

## Project File Structure
```
java-quiz-platform/
├── pom.xml                                    # Maven configuration & MySQL connector dependency
├── schema.sql                                 # Database schema (users, questions, quiz_results)
├── README.md                                  # Project documentation
└── src/main/java/com/quiz/
    ├── DBConnection.java                      # JDBC connection provider
    ├── DatabaseHelper.java                    # Database CRUD operations (register, login, questions, scores, history)
    ├── User.java                              # Model representing user account
    ├── Question.java                          # Model representing quiz question
    ├── QuizResult.java                        # Model representing quiz result entry
    ├── QuizManager.java                       # Quiz execution logic for console version
    ├── ConsoleMain.java                       # Console version entry point
    ├── LoginFrame.java                        # Swing GUI login & registration window
    ├── DashboardFrame.java                    # Swing GUI user home screen
    ├── QuizFrame.java                         # Swing GUI interactive quiz screen
    ├── ResultFrame.java                       # Swing GUI quiz result summary screen
    ├── HistoryFrame.java                      # Swing GUI past scores table window
    └── Main.java                              # Swing GUI main entry point (invokes LoginFrame on EDT)
```
