package dao;

import model.Person;

public class PersonDao {
    private Person[] personDb;
    private int personCount;

    public PersonDao(int length) {
        this.personDb = new Person[length];
        this.personCount = 0;
    }
}
