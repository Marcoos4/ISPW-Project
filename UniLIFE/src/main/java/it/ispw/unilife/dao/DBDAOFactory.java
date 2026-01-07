package it.ispw.unilife.dao;

public class DBDAOFactory extends DAOFactory {


    public DBDAOFactory() {
        super();
    }

    @Override
    public CourseDAO getCourseDAO() { return new DBCourseDAO(); }

    @Override
    public UniversityDAO getUniversityDAO() { return new DBUniversityDAO(); }

}
