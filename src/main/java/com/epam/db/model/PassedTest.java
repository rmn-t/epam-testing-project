package com.epam.db.model;

/**
 * Class represents the table "passed_tests" in the database. Table fields in the DB are: id, user_id, test_id, questions_num, correct_answers, grade, time_spent, date.
 */
public class PassedTest extends Model {
    private int userId;
    private int testId;
    private String testName;
    private int questionNum;
    private int correctAnswers;
    private double grade;
    private double timeSpent;
    private String date;

    public PassedTest() {
    }

    /**
     * Default constructor that uses all available fields of the class. It is also used in the builder nested class. Fields have same name in the DB,
     * unless it is specified in brackets.
     *
     * @param id             id of the passed test in the DB
     * @param userId         id of the user who passed the test (db field name: user_id)
     * @param testId         id of the test that was passed (db field name: test_id)
     * @param testName       name of the test that is taken from test table upon joining
     * @param questionNum    number of the questions that were in the test at the time of passing it (db field name: question_num)
     * @param correctAnswers numbers of the questions that were correctly answered by user (db field name: correct_answers)
     * @param grade          grade that user received for the test (min 0 max 100), calculated based on the number of correctly answered questions to total number of questions
     * @param timeSpent      % of the time that was spent while doing the test (db field name time_spent)
     * @param date           date and time when the test was finished
     */
    public PassedTest(int id, int userId, int testId, String testName, int questionNum, int correctAnswers, double grade, double timeSpent, String date) {
        super(id);
        this.userId = userId;
        this.testId = testId;
        this.testName = testName;
        this.questionNum = questionNum;
        this.correctAnswers = correctAnswers;
        this.grade = grade;
        this.timeSpent = timeSpent;
        this.date = date;
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

    public double getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(double timeSpent) {
        this.timeSpent = timeSpent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "PassedTest{" +
                "userId=" + userId +
                ", testId=" + testId +
                ", testName='" + testName + '\'' +
                ", questionNum=" + questionNum +
                ", correctAnswers=" + correctAnswers +
                ", grade=" + grade +
                ", timeSpent=" + timeSpent +
                ", date='" + date + '\'' +
                '}';
    }

    public static class Builder {
        private int id;
        private int userId;
        private int testId;
        private String testName;
        private int questionNum;
        private int correctAnswers;
        private double grade;
        private double timeSpent;
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

        public Builder setTimeSpent(double timeSpent) {
            this.timeSpent = timeSpent;
            return this;
        }

        public Builder setDate(String date) {
            this.date = date;
            return this;
        }

        public PassedTest build() {
            return new PassedTest(this.id, this.userId, this.testId, this.testName, this.questionNum, this.correctAnswers, this.grade, this.timeSpent, this.date);
        }
    }
}
