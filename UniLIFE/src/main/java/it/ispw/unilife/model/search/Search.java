package it.ispw.unilife.model.search;

import it.ispw.unilife.dao.DAO;

import java.util.List;

public interface Search<T> {

    public List<T> execute(DAO<T> dao);
}
