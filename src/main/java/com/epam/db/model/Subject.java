package com.epam.db.model;

public class Subject {
    private int id;
    private String name;

    public Subject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Subject() {
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

    public static class Builder {
        private int id;
        private String name;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Subject build() {
            return new Subject(this.id,this.name);
        }
    }
}


