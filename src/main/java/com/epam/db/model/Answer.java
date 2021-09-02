package com.epam.db.model;

public class Answer {
    private int id;
    private int questionId;
    private String text;
    private boolean isCorrect;

    public Answer(int id, int questionId, String text, boolean isCorrect) {
        this.id = id;
        this.questionId = questionId;
        this.text = text;
        this.isCorrect = isCorrect;
    }

    public Answer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean correct) {
        isCorrect = correct;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", questionId=" + questionId +
                ", text='" + text + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }

    public static class Builder {
        private int id;
        private int questionId;
        private String text;
        private boolean isCorrect;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setQuestionId(int questionId) {
            this.questionId = questionId;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setCorrect(boolean correct) {
            this.isCorrect = correct;
            return this;
        }

        public Answer build() {
            return new Answer(this.id,this.questionId,this.text,this.isCorrect);
        }
    }
}