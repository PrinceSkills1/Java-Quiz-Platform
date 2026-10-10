package com.quiz;

// Simple model class for a quiz result
public class QuizResult {
    private String user;
    private int score;
    private int total;

    public QuizResult(String user, int score, int total) {
        this.user = user;
        this.score = score;
        this.total = total;
    }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
}
