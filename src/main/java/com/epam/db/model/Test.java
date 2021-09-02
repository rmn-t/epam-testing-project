package com.epam.db.model;

public class Test {
    private int id;
    private String name;
    private String subject;
    private String complexity;
    private int duration;
    private int questionsNum;

    public Test(int id, String name, String subject, String complexity, int duration, int questionsNum) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.complexity = complexity;
        this.duration = duration;
        this.questionsNum = questionsNum;
    }

    public Test() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public static class Builder {
        private int id;
        private String name;
        private String subject;
        private String complexity;
        private int duration;
        private int questionsNum;

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

        public Builder setComplexity(String complexity) {
            this.complexity = complexity;
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

        public Test build() {
            return new Test(this.id,this.name,this.subject,this.complexity,this.duration,this.questionsNum);
        }
    }
}
