package com.epam.db.model;

public class User {
    private int id;
    private String username;
    private String password;
    private int salt;
    private String role;
    private String status;

    public User(int id, String username, String password, int salt, String role, String status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.role = role;
        this.status = status;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
        private String role;
        private String status;

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

        public Builder setRole(String role) {
            this.role = role;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public User build() {
            return new User(this.id,this.username,this.password,this.salt,this.role,this.status);
        }
    }
}
