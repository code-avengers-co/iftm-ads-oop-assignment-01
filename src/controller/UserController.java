package controller;

import dao.PersonDao;
import dao.UserDao;

public class UserController {
    private UserDao userDao;
    private PersonDao personDao;

    public UserController(UserDao userDao, PersonDao personDao) {
        this.userDao = userDao;
        this.personDao = personDao;
    }
}
