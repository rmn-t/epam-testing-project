package com.epam.db.model;

/**
 * Test class represents the table "test" in the database. Table fields in the DB are: id, name, subject_id, complexity_id, duration_sec,
 * is_active.
 * Rest of the class fields are used for convenience of displaying data in the variables jsp views etc., values for these properties are
 * supplied through joining test table with related tables.
 */
public class Test extends Model {
    private String name;
    private String subject;
    private int subjectId;
    private String complexity;
    private int complexityId;
    private int duration;
    private int questionsNum;
    private boolean isActive;

    /**
     * Default constructor that uses all available fields of the class. It is also used in the builder nested class.
     * <p>
     * The names of the fields are same as in the DB, fields that have different names in DB and Java have notes inside brackets.
     *
     * @param id           id of the test in DB
     * @param name         name of the test, max length 150 chars
     * @param subject      name of the subject the test is related to, obtained from subject table in DB (field name: name), max length 50 chars
     * @param subjectId    id of the subject in the DB (field name subject_id)
     * @param complexity   name of the complexity for the test, obtained from complexity table in DB (field name: name), max length 25 chars
     * @param complexityId id of the complexity in DB (field name complexity_id)
     * @param duration     duration of the test in seconds (field name duration_sec)
     * @param questionsNum calculated field that represents the number of the questions in test
     * @param isActive     field represents if the test should be available for passing (field name is_active)
     */
    public Test(int id, String name, String subject, int subjectId, String complexity, int complexityId, int duration, int questionsNum, boolean isActive) {
        super(id);
        this.name = name;
        this.subject = subject;
        this.subjectId = subjectId;
        this.complexity = complexity;
        this.complexityId = complexityId;
        this.duration = duration;
        this.questionsNum = questionsNum;
        this.isActive = isActive;
    }

    /**
     * Default constructor without params.
     */
    public Test() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComplexity() {
        return complexity;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getComplexityId() {
        return complexityId;
    }

    public void setComplexityId(int complexityId) {
        this.complexityId = complexityId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getQuestionsNum() {
        return questionsNum;
    }

    public void setQuestionsNum(int questionsNum) {
        this.questionsNum = questionsNum;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        this.isActive = active;
    }

    @Override
    public String toString() {
        return "Test{" +
                "name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", subjectId=" + subjectId +
                ", complexity='" + complexity + '\'' +
                ", complexityId=" + complexityId +
                ", duration=" + duration +
                ", questionsNum=" + questionsNum +
                ", isActive=" + isActive +
                '}';
    }

    public static class Builder {
        private int id;
        private String name;
        private String subject;
        private int subjectId;
        private String complexity;
        private int complexityId;
        private int duration;
        private int questionsNum;
        private boolean isActive;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder setSubjectId(int subjectId) {
            this.subjectId = subjectId;
            return this;
        }

        public Builder setComplexity(String complexity) {
            this.complexity = complexity;
            return this;
        }

        public Builder setComplexityId(int complexityId) {
            this.complexityId = complexityId;
            return this;
        }


        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder setQuestionsNum(int questionsNum) {
            this.questionsNum = questionsNum;
            return this;
        }

        public Builder setIsActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Test build() {
            return new Test(this.id, this.name, this.subject, this.subjectId, this.complexity, this.complexityId, this.duration, this.questionsNum,this.isActive);
        }
    }
}
