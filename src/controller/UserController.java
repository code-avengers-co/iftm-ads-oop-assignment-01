package controller;

import dao.PersonDao;
import dao.UserDao;
import model.Person;
import model.User;

import java.time.LocalDate;
import java.util.List;

public class UserController {
    private UserDao userDao;
    private PersonDao personDao;

    public UserController(UserDao userDao, PersonDao personDao) {
        this.userDao = userDao;
        this.personDao = personDao;
    }

    public boolean registerUser(
            String personName, LocalDate personBirthDate, String personDocument,
            String username, String password) {
        // 1. Create the person
        Person person = new Person(personName, personBirthDate, personDocument);
        boolean personIsCreated = personDao.savePerson(person);
        if (!personIsCreated){
            return false; // Failed to create person, so not proceed with user creation
        }

        // 2. Create the user
        User user = new User(person, username, password);
        boolean userIsCreated = userDao.saveUser(user);

        // 3. Rollback person
        if (!userIsCreated) {
            personDao.deletePerson(person.getId());
            return false;
        }

        return true;
    }

    public List<User> getUsers(){
        return userDao.getUsers();
    }

    public User authenticate(String username, String password) {
        return userDao.authenticate(username, password); 
    }
}
