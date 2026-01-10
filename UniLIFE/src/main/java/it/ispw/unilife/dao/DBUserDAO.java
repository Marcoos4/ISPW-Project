package it.ispw.unilife.dao;

import it.ispw.unilife.exception.RegistrationException;
import it.ispw.unilife.exception.UserNotFoundException;
import it.ispw.unilife.model.Role;
import it.ispw.unilife.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUserDAO implements UserDAO{

    private static List<User> users = new ArrayList<>();

    @Override
    public User findUserByUsernameAndPassword(String username, String password) throws UserNotFoundException {
        User user = checkUsers(username);
        if (user != null){
            return user;
        }
        String query = "SELECT * FROM users where username = ? AND password = ?";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement statement = conn.prepareStatement(query)){
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                user = new User(resultSet.getString("username"), resultSet.getString("name"), resultSet.getString("surname"),
                        resultSet.getString("password"),Role.stringToRole(resultSet.getString("role")));
                users.add(user);
                return user;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }


        throw new UserNotFoundException();
    }

    @Override
    public void registerUser(User user)
            throws RegistrationException{
        String query = "INSERT INTO users (username, name, surname, password, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Sostituisci i '?' con i valori reali dell'oggetto User
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getSurname());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole().toString());

            stmt.executeUpdate(); // Esegue il salvataggio nel DB
            System.out.println("Utente salvato correttamente nel database!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findUserForExternalLogin(String username) throws UserNotFoundException {
        User user = checkUsers(username);
        if (user != null){
            return user;
        }
        String query = "SELECT * FROM users where username = ?";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement statement = conn.prepareStatement(query)){
                statement.setString(1, username);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()){
                    user = new User(resultSet.getString("username"), resultSet.getString("name"), resultSet.getString("surname"),
                                        resultSet.getString("password"),Role.stringToRole(resultSet.getString("role")));
                    users.add(user);
                    return user;
                }

        } catch (SQLException e){
            e.printStackTrace();
        }


        throw new UserNotFoundException();
    }

    private User checkUsers(String username){
        for (User user : users){
            if(user.checkAccount(username))
                return user;
        }
        return null;
    }

}

