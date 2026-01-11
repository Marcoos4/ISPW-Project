package it.ispw.unilife.dao;

import it.ispw.unilife.model.Student;

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
}
