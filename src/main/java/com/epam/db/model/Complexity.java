package com.epam.db.model;

/**
 * Class represents the table "complexity" in the database. Table fields in the DB are: id, name.
 */
public class Complexity extends Model {
    private String name;
    private int scale;

    /**
     * Default constructor that uses all available fields of the class. It is also used in the builder nested class.
     *
     * @param id    id of the complexity in db
     * @param name  string value of the test's complexity (max length 25 chars)
     * @param scale numeric value that represents the complexity, then higher value the higher the complexity
     */
    public Complexity(int id, String name, int scale) {
        super(id);
        this.name = name;
        this.scale = scale;
    }

    public Complexity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    @Override
    public String toString() {
        return "Complexity{" +
                "name='" + name + '\'' +
                ", scale=" + scale +
                '}';
    }

    public static class Builder {
        private int id;
        private String name;
        private int scale;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setScale(int scale) {
            this.scale = scale;
            return this;
        }

        public Complexity build() {
            return new Complexity(this.id, this.name, this.scale);
        }
    }
}
