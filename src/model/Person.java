package model;

import utils.SystemClock;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Person {
    private int id;
    private String name;
    private LocalDate birthDate;
    private String document;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Person(String name, LocalDate birthDate, String document) {
        this.name = name;
        this.birthDate = birthDate;
        this.document = document;
        this.createdAt = SystemClock.now();
        this.updatedAt = SystemClock.now();
    }

    public Person(int id, String name, LocalDate birthDate, String document, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.document = document;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate){
        this.birthDate = birthDate;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
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

    @Override
    public String toString() {
        return "Person ID: " + this.getId() +
                " | Name: " + this.getName() +
                " | Document: " + this.getDocument() +
                " | Birth Date: " + this.getBirthDate();
    }}
