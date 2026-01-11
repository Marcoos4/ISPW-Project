package it.ispw.unilife.dao.factory;

import it.ispw.unilife.dao.*;

public class DBDAOFactory extends DAOFactory {


    public DBDAOFactory() {
        super();
    }

    @Override
    public UserDAO getUserDAO(){ return new DBUserDAO();}
    @Override
    public CourseDAO getCourseDAO(){ return new DBCourseDAO(); }
    @Override
    public UniversityDAO getUniversityDAO(){ return new DBUniversityDAO(); }

}
