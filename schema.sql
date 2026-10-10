-- Run this file in MySQL: mysql -u root -p < schema.sql
CREATE DATABASE IF NOT EXISTS quiz_db;
USE quiz_db;

CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS questions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    question_text TEXT,
    option_a VARCHAR(255),
    option_b VARCHAR(255),
    option_c VARCHAR(255),
    option_d VARCHAR(255),
    correct_option CHAR(1)
);

CREATE TABLE IF NOT EXISTS quiz_results (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50),
    score INT,
    total_questions INT,
    date_taken TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_option) VALUES
('Which data type is used to store a whole number in Java?', 'int', 'String', 'boolean', 'double', 'A'),
('Which keyword is used to create an object in Java?', 'class', 'new', 'this', 'static', 'B'),
('What does JVM stand for?', 'Java Visual Machine', 'Java Variable Method', 'Java Virtual Machine', 'Java Verified Module', 'C'),
('Which one contains the compiler (javac) and tools to develop Java programs?', 'JRE', 'JVM', 'JDK', 'JIT', 'C'),
('Which loop is guaranteed to run at least once?', 'for', 'while', 'for-each', 'do-while', 'D'),
('Which OOP concept means hiding data using private variables and getters/setters?', 'Inheritance', 'Encapsulation', 'Polymorphism', 'Recursion', 'B'),
('Which keyword is used to inherit a class in Java?', 'implements', 'inherits', 'extends', 'super', 'C'),
('What is the default value of a boolean variable in Java (class member)?', 'true', 'false', 'null', '0', 'B');
