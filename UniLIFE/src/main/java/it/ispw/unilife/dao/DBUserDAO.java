package it.ispw.unilife.dao;

import it.ispw.unilife.dao.factory.ConnectionFactory;
import it.ispw.unilife.dao.factory.DAOFactory;
import it.ispw.unilife.exception.DAOException;
import it.ispw.unilife.exception.RegistrationException;
import it.ispw.unilife.exception.UserNotFoundException;
import it.ispw.unilife.model.Role;
import it.ispw.unilife.model.Student;
import it.ispw.unilife.model.Tutor;
import it.ispw.unilife.model.User;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUserDAO implements UserDAO{
    private static final Logger logger = Logger.getLogger(DBUserDAO.class.getName());
    private static List<User> users = new ArrayList<>();

    @Override
    public User findUserByUsernameAndPassword(String username, String password) throws UserNotFoundException {
        User user = checkUsers(username);
        if (user != null){
            return user;
        }
        String query = "SELECT username,name,surname,password,role FROM users where username = ? AND password = ?";

        Connection conn = ConnectionFactory.getConnection();

        try(PreparedStatement statement = conn.prepareStatement(query)){
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

        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            // Sostituisci i '?' con i valori reali dell'oggetto User
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getSurname());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, Role.roleToString(user.getRole()));

            stmt.executeUpdate(); // Esegue il salvataggio nel DB
            logger.info("Utente salvato correttamente nel database!");

            switch (user.getRole()){
                case STUDENT -> DAOFactory.getDAOFactory().getStudentDAO().insert(new Student(user.getName(), user.getSurname(), user.getUserName(), user.getPassword()));
                case TUTOR -> DAOFactory.getDAOFactory().getTutorDAO().insert(new Tutor(user.getName(), user.getSurname(), null, 0, 0, user.getUserName(), user.getPassword()));
                //TODO case UNIVERSITY_EMPLOYEE
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findUserForExternalLogin(String username) throws UserNotFoundException {
        User user = checkUsers(username);
        if (user != null){
            return user;
        }
        String query = "SELECT username,name,surname,password,role FROM users where username = ?";

        Connection conn = ConnectionFactory.getConnection();

        try(PreparedStatement statement = conn.prepareStatement(query)){
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

    @Override
    public void delete(User user) throws DAOException{
        return;
    }

    private User checkUsers(String username){
        for (User user : users){
            if(user.checkAccount(username))
                return user;
        }
        return null;
    }


    @Override
    public List<User> getAll() throws DAOException{
        return null;
    }

    @Override
    public void insert(User user) throws DAOException {
        return;
    }

}

