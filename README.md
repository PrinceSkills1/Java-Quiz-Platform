# Java Quiz Platform

A console-based quiz application where users can register, log in, take a 5-question Java quiz, and view their past scores. Built as a college project.

## Tech Stack
- Java 17
- JDBC
- MySQL
- Maven

## Prerequisites
- JDK 17 installed (`java -version`)
- Maven installed (`mvn -version`)
- MySQL Server running on `localhost:3306`

## Setup
1. Import the database and sample questions:
   ```
   mysql -u root -p < schema.sql
   ```
2. Open `src/main/java/com/quiz/DBConnection.java` and change the password (`root123`) to your MySQL root password.

## Compile and Run
```
mvn clean compile
mvn exec:java -Dexec.mainClass="com.quiz.Main"
```
(or in one go: `mvn clean compile exec:java -Dexec.mainClass="com.quiz.Main"`)

## Features
- Register / Login
- 5 random questions per quiz (options A-D, case-insensitive input)
- Score saved to the database
- View past scores
