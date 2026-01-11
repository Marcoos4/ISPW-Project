package it.ispw.unilife.dao.factory;

import it.ispw.unilife.config.Configuration;
import it.ispw.unilife.config.PersistencyMode;
import it.ispw.unilife.dao.CourseDAO;
import it.ispw.unilife.dao.UniversityDAO;
import it.ispw.unilife.dao.UserDAO;

public abstract class DAOFactory {

    private static DAOFactory instance = null;

    protected DAOFactory(){}

    public static synchronized DAOFactory getDAOFactory(){

        if(instance == null){
            Configuration config = Configuration.getInstance();
            PersistencyMode mode = config.getPersistencyMode();
            if(mode.equals(PersistencyMode.JDBC))
                instance = new DBDAOFactory();
            else
                instance = new JSONDAOFactory();
        }
        return instance;
    }

    public abstract UserDAO getUserDAO();
    public abstract CourseDAO getCourseDAO();
    public abstract UniversityDAO getUniversityDAO();

}
