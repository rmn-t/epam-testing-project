package com.epam.db.model;

/**
 * Class represents the table "subject" in the database. Table fields in the DB are: id, name.
 */
public class Subject extends Model {
    private String name;

    /**
     * Default constructor that uses all available fields of the class. It is also used in the builder nested class.
     *
     * @param id   id of the role in db
     * @param name name of the role in the system (max length 50 chars)
     */
    public Subject(int id, String name) {
        super(id);
        this.name = name;
    }

    public Subject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Subject{" +
                super.toString() +
                "name='" + name + '\'' +
                '}';
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
            return new Subject(this.id, this.name);
        }
    }
}


