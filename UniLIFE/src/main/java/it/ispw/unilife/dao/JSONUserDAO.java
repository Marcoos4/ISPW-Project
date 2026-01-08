package it.ispw.unilife.dao;

import it.ispw.unilife.exception.RegistrationException;
import it.ispw.unilife.exception.UserNotFoundException;
import it.ispw.unilife.model.Role;
import it.ispw.unilife.model.User;


public class JSONUserDAO implements UserDAO{

    @Override
    public User findUserByUsernameAndPassword(String username, String password) throws UserNotFoundException {
        return null;
    }

    @Override
    public User registerUser(String username, String name, String surname, String password, Role role)
            throws RegistrationException {
        return null;
    }
}
