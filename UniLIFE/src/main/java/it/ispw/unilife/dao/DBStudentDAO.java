package it.ispw.unilife.dao;

import it.ispw.unilife.exception.DAOException;
import it.ispw.unilife.model.Student;

import java.util.List;

public class DBStudentDAO implements StudentDAO{

    @Override
    public List getAll() {
        return List.of();
    }

    @Override
    public void insert(Student student) {
        return;
    }

    @Override
    public void delete(Student student) throws DAOException {

    }

}
