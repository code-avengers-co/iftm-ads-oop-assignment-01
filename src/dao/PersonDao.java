package dao;

import model.Person;
import utils.DatabaseConnection;
import utils.SystemClock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDao {
    public boolean savePerson(Person person) {
        String sql = "INSERT INTO person (name, birth_date, document, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        DatabaseConnection factory = new DatabaseConnection();

        try(Connection connection = factory.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, person.getName());
            stmt.setDate(2, java.sql.Date.valueOf(person.getBirthDate()));
            stmt.setString(3, person.getDocument());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(person.getCreatedAt()));
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf(person.getUpdatedAt()));

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        person.setId(generatedKeys.getInt(1));
                    }
                }

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Person> getAllPersons() {
        List<Person> allPersons = new ArrayList<>();
        String sql = "SELECT * FROM person";

        DatabaseConnection factory = new DatabaseConnection();

        try(Connection connection = factory.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Person person = new Person(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("birth_date").toLocalDate(),
                        resultSet.getString("document"),
                        resultSet.getTimestamp("created_at").toLocalDateTime(),
                        resultSet.getTimestamp("updated_at").toLocalDateTime()
                );

                allPersons.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allPersons;
    }

    public Person findById(int id) {
        String sql = "SELECT * FROM person WHERE id = ?";

        DatabaseConnection factory = new DatabaseConnection();

        try(Connection connection = factory.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return new Person(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getDate("birth_date").toLocalDate(),
                            resultSet.getString("document"),
                            resultSet.getTimestamp("created_at").toLocalDateTime(),
                            resultSet.getTimestamp("updated_at").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Person not found
    }

    public boolean updatePerson(Person updatedPerson){
        String sql = "UPDATE person SET name = ?, birth_date = ?, document = ?, updated_at = ? WHERE id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try(Connection connection = factory.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, updatedPerson.getName());
            stmt.setDate(2, java.sql.Date.valueOf(updatedPerson.getBirthDate()));
            stmt.setString(3, updatedPerson.getDocument());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(SystemClock.now()));
            stmt.setInt(5, updatedPerson.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deletePerson(int id) {
        String sql = "DELETE FROM person WHERE id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try(Connection connection = factory.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
