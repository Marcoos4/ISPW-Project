package it.ispw.unilife.dao;

import java.util.List;

public interface DAO <T>{

    T getInstance();

    List<T> getAll();

    void insert(T t);

}
