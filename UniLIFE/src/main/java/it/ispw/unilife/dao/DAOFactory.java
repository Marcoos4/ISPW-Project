package it.ispw.unilife.dao;

import it.ispw.unilife.config.Configuration;
import it.ispw.unilife.config.PersistencyMode;

public abstract class DAOFactory {

    public static DAOFactory getDAOFactory(){

        Configuration config = Configuration.getInstance();
        PersistencyMode mode = config.getPersistencyMode();

        if(mode.equals(PersistencyMode.JDBC))
            return new DBDAOFactory();
        else
            return new JSONDAOFactory();

    }

    public abstract CourseDAO getCourseDAO();

    public abstract UniversityDAO getUniversityDAO();


}
