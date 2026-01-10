package it.ispw.unilife.dao.factory;

import it.ispw.unilife.dao.CourseDAO;
import it.ispw.unilife.dao.DBCourseDAO;
import it.ispw.unilife.dao.DBUserDAO;
import it.ispw.unilife.dao.UserDAO;

public class DBDAOFactory extends DAOFactory {


    public DBDAOFactory() {
        super();
    }

    @Override
    public UserDAO getUserDAO(){ return new DBUserDAO();}

    @Override
    public CourseDAO getCourseDAO(){ return new DBCourseDAO(); }

}
