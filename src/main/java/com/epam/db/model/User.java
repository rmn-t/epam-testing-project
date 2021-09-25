package com.epam.db.model;

/**
 * Class represents the table "user" in the database. Table fields in the DB are: id, username, password, salt, first_name, last_name, role_id, status_id.
 * <p>
 * Rest of the class fields are used for convenience of displaying data in the variables jsp views etc., values for these properties are
 * supplied through joining util table with related tables.
 */
public class User extends Model {
    private String username;
    private String password;
    private int salt;
    private String firstName;
    private String lastName;
    private String role;
    private int roleId;
    private String status;
    private int statusId;

    /**
     * Default constructor that uses all available fields of the class. It is also used in the builder nested class.
     * <p>
     * The names of the fields are same as in the DB, fields that have different names in DB and Java have notes inside brackets.
     *
     * @param id        id of the user in the db
     * @param username  username that is set upon registration, unique, username change directly from app is not expected (max length 25 chars)
     * @param password  hashed password for the user (max length 128 chars)
     * @param salt      salt that was used upon hashing the password for the user
     * @param firstName first name of the user (db field name first_name) (max length 25 chars)
     * @param lastName  last name of the user (db field name last_name) (max length 25 chars)
     * @param role      string representation of the role, taken from role table (db field role.name)
     * @param roleId    id of the role of the user
     * @param status    string representation of the status, taken from role table (db field status.name)
     * @param statusId  id of the status of the user
     */
    public User(int id, String username, String password, int salt, String firstName, String lastName, String role, int roleId, String status, int statusId) {
        super(id);
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.roleId = roleId;
        this.status = status;
        this.statusId = statusId;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSalt() {
        return salt;
    }

    public void setSalt(int salt) {
        this.salt = salt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", salt=" + salt +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                ", roleId=" + roleId +
                ", status='" + status + '\'' +
                ", statusId=" + statusId +
                '}';
    }

    public static class Builder {
        private int id;
        private String username;
        private String password;
        private int salt;
        private String firstName;
        private String lastName;
        private String role;
        private int roleId;
        private String status;
        private int statusId;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setSalt(int salt) {
            this.salt = salt;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setRole(String role) {
            this.role = role;
            return this;
        }

        public Builder setRoleId(int roleId) {
            this.roleId = roleId;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setStatusId(int statusId) {
            this.statusId = statusId;
            return this;
        }

        public User build() {
            return new User(this.id, this.username, this.password, this.salt, this.firstName, this.lastName, this.role, this.roleId, this.status, this.statusId);
        }
    }
}
