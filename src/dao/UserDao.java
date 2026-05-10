package dao;

import model.User;
import utils.SystemClock;

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

    public boolean updateUser(User updatedUser) {
        User user = findById(updatedUser.getId());
        if (user == null) {
            return false; // User not found
        }

        user.setPerson(updatedUser.getPerson());
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setUpdatedAt(SystemClock.now());
        user.setAdmin(updatedUser.isAdmin());

        return true;
    }

    public boolean deleteUser(int id) {
        int indexToDeleted = -1;
        for (int i = 0; i < userCount; i++) {
            if (this.userDb[i].getId() == id){
                indexToDeleted = i;
                break;
            }
        }

        if (indexToDeleted == -1){
            return false; // User not found
        }

        // Shift the last user to index to be deleted and nullify the last position
        userDb[indexToDeleted] = userDb[userCount - 1];
        userDb[userCount - 1] = null;
        userCount--;

        return true;
    }
}
