package dao;

import model.Person;

public class PersonDao {
    private Person[] personDb;
    private int personCount;

    public PersonDao(int length) {
        this.personDb = new Person[length];
        this.personCount = 0;
    }

    public boolean savePerson(Person person) {
        if (personCount >= personDb.length) {
            return false; // No more space to save new person
        }
        personDb[personCount] = person;
        personCount++;

        return true;
    }

    public Person[] getAllPersons() {
        Person[] allPersons = new Person[personCount];
        int currentIndex = 0;

        for (int i = 0; i < personCount; i++) {
            if (personDb[i] != null) {
                allPersons[currentIndex] = personDb[i];
                currentIndex++;
            }
        }

        return allPersons;
    }
}
