package model;

import utils.CreationIdUtils;
import utils.SystemClock;

import java.time.LocalDateTime;

public class User {
    private int id;
    private Person person;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isAdmin;

    public User(Person person, String username, String password) {
        this.person = person;
        this.username = username;
        this.password = password;
        this.createdAt = SystemClock.now();
        this.updatedAt = SystemClock.now();
        this.isAdmin = false;
    }

    public User(int id, Person person, String username, String password, LocalDateTime createdAt, LocalDateTime updatedAt, boolean isAdmin) {
        this.id = id;
        this.person = person;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isAdmin = isAdmin;
    }
    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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

    public void setPassword(String password){
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return "User ID: " + this.getId() +
                " | Username: " + this.getUsername() +
                " | Password: *** \n" +
                "   -> " + this.getPerson().toString();
    }
}