package it.ispw.unilife.dao;

import it.ispw.unilife.exception.UserNotFoundException;
import it.ispw.unilife.model.User;

import java.util.List;

public class DBUserDAO implements UserDAO{
    @Override
    public User getInstance() {
        return null;
    }

    @Override
    public List<User> getAll() {
        return List.of();
    }

    @Override
    public int insert(User user) {
        return 0;
    }

    @Override
    public int delete() {
        return 0;
    }

    @Override
    public int update() {
        return 0;
    }

    @Override
    public void saveTo() { }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) throws UserNotFoundException {
        return null;
    }
}
