package com.epam.db.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents the table "question" in the database. Table fields in the DB are: id, text, test_id;
 */
public class Question implements Serializable {
    private int id;
    private String text;
    private int testId;
    private List<Answer> answers;

    /**
     * Default constructor that uses all available fields of the class. It is also used in the builder nested class. Fields have same name in the DB,
     * unless it is specified in brackets.
     *
     * @param id      id of the question in the DB
     * @param text    text of the question (max length 400 chars)
     * @param testId  id of the test that question is related to (db field name: test_id)
     * @param answers a list of answers for the question, not present in db table
     */
    public Question(int id, String text, int testId, List<Answer> answers) {
        this.id = id;
        this.text = text;
        this.testId = testId;
        this.answers = answers;
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

    private List<Answer> getOnlyCorrectAnswers(List<Answer> answers) {
        List<Answer> result = new ArrayList<>();
        for (Answer answer : answers) {
            if (answer.getIsCorrect()) {
                result.add(answer);
            }
        }
        return result;
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

    public static class Builder {
        private int id;
        private String text;
        private int testId;
        private List<Answer> answers;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setTestId(int testId) {
            this.testId = testId;
            return this;
        }

        public Builder setAnswers(List<Answer> answers) {
            this.answers = answers;
            return this;
        }

        public Question build() {
            return new Question(this.id, this.text, this.testId, this.answers);
        }

    }

    public boolean validateAnswers(String[] userAnswers) {
        if (userAnswers == null) {
            return false;
        }
        List<Answer> correctAnswers = getOnlyCorrectAnswers(this.answers);
        if (userAnswers.length != correctAnswers.size()) {
            return false;
        }
        for (Answer answer : correctAnswers) {
            if (!Arrays.asList(userAnswers).contains("" + answer.getId())) {
                return false;
            }
        }
        return true;
    }
}

