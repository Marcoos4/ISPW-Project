package it.ispw.unilife.dao;

import it.ispw.unilife.model.Student;

import java.util.List;

public class JSONStudentDAO implements StudentDAO{
    @Override
    public Student getInstance() {
        return null;
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
