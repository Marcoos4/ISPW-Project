package it.ispw.unilife.dao;

import java.util.List;

public interface DAO <T>{

    public T getInstance();

    public List<T> getAll();

    public int insert(T t);

    public int delete();

    public int update();

    public void saveTo();

}
