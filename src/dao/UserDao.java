package dao;

import model.User;

public class UserDao {
    private User[] userDb;
    private int userCount;

    public UserDao(int length) {
        this.userDb = new User[length];
        this.userCount = 0;
    }

    public boolean saveUser(User user){
        if (userCount >= userDb.length) {
            return false; // No more space to save new user
        }
        
        userDb[userCount] = user;
        userCount++;

        return true;
    }
}
