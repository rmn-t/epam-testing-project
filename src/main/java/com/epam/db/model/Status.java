package com.epam.db.model;

import java.io.Serializable;

public class Status implements Serializable {
    private int id;
    private String name;

    public Status(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Status() {
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

        public Status build() {
            return new Status(this.id,this.name);
        }
    }
}
