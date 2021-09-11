package com.epam.db.model;

import java.io.Serializable;

public class User implements Serializable {
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

    public User(int id, String username, String password, int salt, String firstName, String lastName, String role, int roleId,String status, int statusId) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
            return new User(this.id,this.username,this.password,this.salt,this.firstName,this.lastName,this.role,this.roleId,this.status,this.statusId);
        }
    }
}
