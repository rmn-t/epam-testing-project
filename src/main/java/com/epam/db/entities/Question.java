package com.epam.db.entities;

import java.util.List;

public class Question {
    private int id;
    private String text;
    private int testId;
    private List<Answer> answers;

    public Question(int id, String text, int testId) {
        this.id = id;
        this.text = text;
        this.testId = testId;
    }

    public Question() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int test_id) {
        this.testId = test_id;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", testId=" + testId +
                ", answers=" + answers +
                '}';
    }
}

