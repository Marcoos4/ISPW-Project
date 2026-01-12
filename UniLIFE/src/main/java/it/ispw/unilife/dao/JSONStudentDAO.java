package it.ispw.unilife.dao;

import it.ispw.unilife.exception.UserNotFoundException;
import it.ispw.unilife.model.Student;
import it.ispw.unilife.model.User;

import java.util.List;

public class JSONStudentDAO extends JSONDAO<Student> implements StudentDAO{


    public JSONStudentDAO() {
        super("data/student.json", Student.class);
    }

    @Override
    public List getAll() {
        return List.of();
    }

    @Override
    public void insert(Student student) {
    }

    public int delete() {
        return 0;
    }

    public int update() {
        return 0;
    }

    public void saveTo() {

    }

    @Override
    public User findStudentByUsername(String userName) throws UserNotFoundException {
        return null;
    }
}
