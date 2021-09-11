package com.epam.db.model;

import java.io.Serializable;

public class PassedTest implements Serializable {
    private int id;
    private int userId;
    private int testId;
    private String testName;
    private int questionNum;
    private int correctAnswers;
    private double grade;
    private int timeSpent;
    private String date;

    public PassedTest() {
    }

    public PassedTest(int id, int userId, int testId, String testName, int questionNum, int correctAnswers, double grade, int timeSpent, String date) {
        this.id = id;
        this.userId = userId;
        this.testId = testId;
        this.testName = testName;
        this.questionNum = questionNum;
        this.correctAnswers = correctAnswers;
        this.grade = grade;
        this.timeSpent = timeSpent;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static class Builder {
        private int id;
        private int userId;
        private int testId;
        private String testName;
        private int questionNum;
        private int correctAnswers;
        private double grade;
        private int timeSpent;
        private String date;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder setTestId(int testId) {
            this.testId = testId;
            return this;
        }

        public Builder setTestName(String testName) {
            this.testName = testName;
            return this;
        }

        public Builder setQuestionNum(int questionNum) {
            this.questionNum = questionNum;
            return this;
        }

        public Builder setCorrectAnswers(int correctAnswers) {
            this.correctAnswers = correctAnswers;
            return this;
        }

        public Builder setGrade(double grade) {
            this.grade = grade;
            return this;
        }

        public Builder setTimeSpent(int timeSpent) {
            this.timeSpent = timeSpent;
            return this;
        }

        public Builder setDate(String date) {
            this.date = date;
            return this;
        }

        public PassedTest build() {
            return new PassedTest(this.id,this.userId,this.testId,this.testName,this.questionNum,this.correctAnswers,this.grade,this.timeSpent,this.date);
        }
    }
}
