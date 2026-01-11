package it.ispw.unilife.dao;


import it.ispw.unilife.exception.DAOException;

import java.util.List;

public interface DAO <T>{

    public List<T> getAll() throws DAOException;

    public void insert(T t) throws DAOException;

    public void delete(T t) throws DAOException;

}
