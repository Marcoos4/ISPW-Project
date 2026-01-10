package it.ispw.unilife.dao.factory;

import it.ispw.unilife.dao.CourseDAO;
import it.ispw.unilife.dao.JSONCourseDAO;
import it.ispw.unilife.dao.JSONUserDAO;
import it.ispw.unilife.dao.UserDAO;

public class JSONDAOFactory extends DAOFactory {

    public JSONDAOFactory() {
        super();
    }

    @Override
    public UserDAO getUserDAO(){ return new JSONUserDAO();}

    @Override
    public CourseDAO getCourseDAO(){ return new JSONCourseDAO();}
}
