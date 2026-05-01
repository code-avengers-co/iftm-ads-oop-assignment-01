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

    public User[] getUsers(){
        User[] users = new User[userCount];
        int currentIndex = 0;

        for (int i = 0; i < userCount; i++) {
            if (userDb[i] != null){
                users[currentIndex]= userDb[i];
                currentIndex++;
            }
        }

        return users;
    }

    public User findById(int id){
        for (int i = 0; i < userCount; i++) {
            if (userDb[i].getId() == id) {
                return userDb[i];
            }
        }

        return null; // User not found
    }

    
}
