package com.epam.db.entities;

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
}
