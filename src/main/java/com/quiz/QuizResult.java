package com.quiz;

// Simple model class for a quiz result
public class QuizResult {
    private String user;
    private int score;
    private int total;

    private String date;

    public QuizResult(String user, int score, int total) {
        this.user = user;
        this.score = score;
        this.total = total;
    }

    public QuizResult(String user, int score, int total, String date) {
        this.user = user;
        this.score = score;
        this.total = total;
        this.date = date;
    }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
