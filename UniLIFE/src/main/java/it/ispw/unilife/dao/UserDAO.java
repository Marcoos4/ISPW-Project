package it.ispw.unilife.dao;

import it.ispw.unilife.exception.UserNotFoundException;
import it.ispw.unilife.model.User;

public interface UserDAO extends DAO<User>{
    public User findUserByUsernameAndPassword(String username, String password) throws UserNotFoundException;
}
