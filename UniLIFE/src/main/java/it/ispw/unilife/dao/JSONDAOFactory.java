package it.ispw.unilife.dao;

public class JSONDAOFactory extends DAOFactory{

    @Override
    public CourseDAO getCourseDAO() { return new JSONCourseDAO(); }

    @Override
    public UniversityDAO getUniversityDAO() { return new JSONUniversityDAO(); }
}
