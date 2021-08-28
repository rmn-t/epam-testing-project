package com.epam.db.entities;

public class User {
    private int id;
    private String login;
    private String password;
    private int salt;
    private String role;
    private String status;

    public User(int id, String login, String password, int salt, String role, String status) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.status = status;
        this.salt = salt;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
}
