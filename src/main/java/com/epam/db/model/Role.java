package com.epam.db.model;

import java.io.Serializable;

/**
 * Class represents the table "role" in the database. Table fields in the DB are: id, name.
 */
public class Role implements Serializable {
    private int id;
    private String name;

    /**
     * Default constructor that uses all available fields of the class. It is also used in the builder nested class.
     *
     * @param id   id of the role in db
     * @param name name of the role in the system (max length 20 chars)
     */
    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role() {
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

        public Role build() {
            return new Role(this.id, this.name);
        }
    }
}
