package it.ispw.unilife.dao;

import it.ispw.unilife.exception.RegistrationException;
import it.ispw.unilife.exception.UserNotFoundException;
import it.ispw.unilife.model.Role;
import it.ispw.unilife.model.User;

public interface UserDAO extends DAO<User>{

    User findUserByUsernameAndPassword(String username, String password)
            throws UserNotFoundException;

    void registerUser(User user)
            throws RegistrationException;

    User findUserForExternalLogin(String username) throws UserNotFoundException;
}
