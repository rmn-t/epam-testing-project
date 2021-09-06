package com.epam.db.model;

public class Complexity {
    private int id;
    private String name;

    public Complexity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Complexity() {
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

        public Complexity build() {
            return new Complexity(this.id,this.name);
        }
    }
}
