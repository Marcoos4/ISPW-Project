package it.ispw.unilife.dao;

import it.ispw.unilife.exception.RegistrationException;
import it.ispw.unilife.exception.UserNotFoundException;
import it.ispw.unilife.model.Role;
import it.ispw.unilife.model.User;

public interface UserDAO extends DAO<User>{

    public User findUserByUsernameAndPassword(String username, String password)
            throws UserNotFoundException;

    public User registerUser(String username, String name, String surname, String password, Role role)
            throws RegistrationException;
}
