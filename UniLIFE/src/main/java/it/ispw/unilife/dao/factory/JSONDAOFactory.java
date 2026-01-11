package it.ispw.unilife.dao.factory;

import it.ispw.unilife.dao.*;

public class JSONDAOFactory extends DAOFactory {

    public JSONDAOFactory() {
        super();
    }

    @Override
    public UserDAO getUserDAO(){ return new JSONUserDAO();}
    @Override
    public CourseDAO getCourseDAO(){ return new JSONCourseDAO();}
    @Override
    public UniversityDAO getUniversityDAO(){return new JSONUniversityDAO();}

}
