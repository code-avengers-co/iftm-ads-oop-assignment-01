package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Person {
    private int id;
    private String name;
    private LocalDate birthDate;
    private String document;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Person(int id, String name, LocalDate birthDate, String document) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.document = document;
        this.createdAt = LocalDateTime.now();
    }
}
