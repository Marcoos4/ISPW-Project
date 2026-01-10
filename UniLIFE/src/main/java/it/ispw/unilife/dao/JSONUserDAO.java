package it.ispw.unilife.dao;

import it.ispw.unilife.exception.DAOException;
import it.ispw.unilife.exception.RegistrationException;
import it.ispw.unilife.exception.UserNotFoundException;
import it.ispw.unilife.model.User;

import java.io.*;

import java.util.List;
import java.util.logging.Level;


public class JSONUserDAO extends JSONDAO<User> implements UserDAO {

    public JSONUserDAO(){
        super("data/users.json", User.class);
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) throws UserNotFoundException {
        List<User> users;

        try {
            users = super.getAll();
        } catch (DAOException e) {
            logger.log(Level.SEVERE, "Critical error reading users file", e);
            throw new RuntimeException();
        }

        for (User user : users) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        throw new UserNotFoundException();
    }

    @Override
    public User findUserForExternalLogin(String username) throws UserNotFoundException {
        List<User> users;

        try {
            users = super.getAll();
        } catch (DAOException e) {
            logger.log(Level.SEVERE, "Critical error reading users file", e);
            throw new RuntimeException();
        }

        for (User user : users) {
            if (user.getUserName().equalsIgnoreCase(username)) {
                return user;
            }
        }

        throw new UserNotFoundException();
    }

    @Override
    public void registerUser(User user) throws RegistrationException {
        try {
            super.insert(user);
        } catch (DAOException e) {
            throw new RegistrationException("Impossibile registrare l'utente: " + e.getMessage());
        }
    }
}

