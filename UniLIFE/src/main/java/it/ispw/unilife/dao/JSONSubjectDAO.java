package it.ispw.unilife.dao;

import it.ispw.unilife.exception.DAOException;
import it.ispw.unilife.model.Tutor;

import java.util.List;

public class JSONSubjectDAO implements SubjectDAO {
    @Override
    public List<Tutor> getAll() throws DAOException {
        return List.of();
    }

    @Override
    public void insert(Tutor tutor) throws DAOException {

    }

    @Override
    public void delete(Tutor tutor) throws DAOException {

    }

    @Override
    public List<String> findSubjectByUsername(String userName) {
        return List.of();
    }
}
