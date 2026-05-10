package controller;

import dao.PersonDao;
import dao.UserDao;
import model.Person;
import model.User;

import java.time.LocalDate;

public class UserController {
    private UserDao userDao;
    private PersonDao personDao;

    public UserController(UserDao userDao, PersonDao personDao) {
        this.userDao = userDao;
        this.personDao = personDao;

        Person person = new Person(99, "Admin", LocalDate.of(1990, 1, 1), "123456789");
        personDao.savePerson(person);

        User admin = new User(99, person, "admin", "admin");
        admin.setAdmin(true);
        userDao.saveUser(admin);
    }

    public boolean registerUser(
            int personId, String personName, LocalDate personBirthDate, String personDocument,
            int userId, String username, String password) {
        // 1. Create the person
        Person person = new Person(personId, personName, personBirthDate, personDocument);
        boolean personIsCreated = personDao.savePerson(person);
        if (!personIsCreated){
            return false; // Failed to create person, so not proceed with user creation
        }

        // 2. Create the user
        User user = new User(userId, person, username, password);
        boolean userIsCreated = userDao.saveUser(user);

        // 3. Rollback person
        if (!userIsCreated) {
            personDao.deletePerson(personId);
            return false;
        }

        return true;
    }

    public User[] getUsers(){
        return userDao.getUsers();
    }

    public User authenticate(String username, String password) {
        User[] users = userDao.getUsers();
        for (User user : users) {
            if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                return user; // Authentication successful
            }
        }

        return null; // Authentication failed
    }
}
