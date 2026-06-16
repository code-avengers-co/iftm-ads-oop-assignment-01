package dao;

import model.Person;
import model.User;
import utils.DatabaseConnection;
import utils.SystemClock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private PersonDao personDao;
    public UserDao(PersonDao personDao) {
        this.personDao = personDao;
    }

    public boolean saveUser(User user){
        String sql = "INSERT INTO user (person_id, username, password, created_at, updated_at, is_admin) VALUES (?, ?, ?, ?, ?, ?)";
        DatabaseConnection factory = new DatabaseConnection();

        try(Connection connection = factory.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, user.getPerson().getId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(user.getCreatedAt()));
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf(user.getUpdatedAt()));
            stmt.setBoolean(6, user.isAdmin());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                    }
                }

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<User> getUsers(){
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";

        DatabaseConnection factory = new DatabaseConnection();

        try (Connection connection = factory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {

            int personId = resultSet.getInt("person_id");
            Person person = personDao.findById(personId);

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        person,
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getTimestamp("created_at").toLocalDateTime(),
                        resultSet.getTimestamp("updated_at").toLocalDateTime(),
                        resultSet.getBoolean("is_admin")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public User findById(int id){
        String sql = "SELECT * FROM user WHERE id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try(Connection connection = factory.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    int personId = resultSet.getInt("person_id");
                    Person person = personDao.findById(personId);

                    return new User(
                            resultSet.getInt("id"),
                            person,
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getTimestamp("created_at").toLocalDateTime(),
                            resultSet.getTimestamp("updated_at").toLocalDateTime(),
                            resultSet.getBoolean("is_admin")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // User not found
    }

    public boolean updateUser(User updatedUser) {
        String sql = "UPDATE user SET person_id = ?, username = ?, password = ?, updated_at = ?, is_admin = ? WHERE id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try(Connection connection = factory.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, updatedUser.getPerson().getId());
            stmt.setString(2, updatedUser.getUsername());
            stmt.setString(3, updatedUser.getPassword());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(SystemClock.now()));
            stmt.setBoolean(5, updatedUser.isAdmin());
            stmt.setInt(6, updatedUser.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM user WHERE id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try(Connection connection = factory.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // User not found or deletion failed
    }
}
