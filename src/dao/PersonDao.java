package dao;

import model.Person;
import utils.SystemClock;

import java.time.LocalDateTime;

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

    public Person findById(int id) {
        for (int i = 0; i < personCount; i++) {
            if (personDb[i].getId() == id) {
                return personDb[i];
            }
        }

        return null; // Person not found
    }

    public boolean updatePerson(Person updatedPerson){
        Person person = findById(updatedPerson.getId());
        if (person == null) {
            return false; // Person not found
        }

        person.setName(updatedPerson.getName());
        person.setBirthDate(updatedPerson.getBirthDate());
        person.setDocument(updatedPerson.getDocument());
        person.setUpdatedAt(SystemClock.now());

        return true;
    }

    public boolean deletePerson(int id) {
        int indexToDelete = -1;
        for (int i = 0; i < personCount; i++) {
            if (personDb[i].getId() == id) {
                indexToDelete = i;
                break;
            }
        }

        if (indexToDelete == -1){
            return false; // Person not found
        }

        // Shift the last person to the deleted position and nullify the last position
        personDb[indexToDelete] = personDb[personCount - 1];
        personDb[personCount - 1] = null;
        personCount--;

        return true;
    }
}
