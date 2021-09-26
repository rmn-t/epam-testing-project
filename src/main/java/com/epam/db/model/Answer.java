package com.epam.db.model;

/**
 * Class represents the table "answer" in the database. Table fields in the DB are: id, question_id, text, isCorrect.
 */
public class Answer extends Model {
    private int questionId;
    private String text;
    private boolean isCorrect;

    /**
     * Default constructor that uses all available fields of the class. It is also used in the builder nested class.
     * <p>
     * The names of the fields are same as in the DB, fields that have different names in DB and Java have notes inside brackets.
     *
     * @param id         id of the answer in the DB
     * @param questionId id of the related question, related to question table (field name: question_id)
     * @param text       the text contents of the answer, max length 400 chars
     * @param isCorrect  boolean, represents if this is a correct answer for the related question
     */
    public Answer(int id, int questionId, String text, boolean isCorrect) {
        super(id);
        this.questionId = questionId;
        this.text = text;
        this.isCorrect = isCorrect;
    }

    /**
     * Default constructor without params.
     */
    public Answer() {
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
                super.toString() +
                "questionId=" + questionId +
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
            return new Answer(this.id, this.questionId, this.text, this.isCorrect);
        }
    }
}
