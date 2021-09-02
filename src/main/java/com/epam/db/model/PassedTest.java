package com.epam.db.model;

public class PassedTest {
    private int id;
    private int userId;
    private int testId;
    private double grade;
    private int timeSpent;
    private String date;

    public PassedTest() {
    }

    public PassedTest(int id, int userId, int testId, double grade, int timeSpent, String date) {
        this.id = id;
        this.userId = userId;
        this.testId = testId;
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
        private double grade;
        private int timeSpent;
        private String date;

        public void setId(int id) {
            this.id = id;
        }

        public Builder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder setTestId(int testId) {
            this.testId = testId;
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
            return new PassedTest(this.id,this.userId,this.testId,this.grade,this.timeSpent,this.date);
        }
    }
}