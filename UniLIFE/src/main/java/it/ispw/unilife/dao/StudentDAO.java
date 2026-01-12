package it.ispw.unilife.dao;

import it.ispw.unilife.exception.UserNotFoundException;
import it.ispw.unilife.model.Student;
import it.ispw.unilife.model.User;

public interface StudentDAO extends DAO<Student>{
    User findStudentByUsername(String userName) throws UserNotFoundException;
}
