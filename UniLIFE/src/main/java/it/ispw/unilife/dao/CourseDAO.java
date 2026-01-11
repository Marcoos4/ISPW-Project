package it.ispw.unilife.dao;

import it.ispw.unilife.exception.DAOException;
import it.ispw.unilife.model.Course;

import java.util.List;

public interface CourseDAO extends DAO<Course>{

    public List<String> getDistinctFaculties() throws DAOException;
    public List<String> getDistinctLanguages() throws DAOException;
    public List<String> getDistinctUniversities() throws DAOException;
    public List<String> getDistinctCourseTypes() throws DAOException;
}
