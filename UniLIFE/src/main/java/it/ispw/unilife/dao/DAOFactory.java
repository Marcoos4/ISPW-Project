package it.ispw.unilife.dao;

import it.ispw.unilife.config.Configuration;
import it.ispw.unilife.config.PersistencyMode;

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

    public abstract ReservationDAO getReservationDAO();
}
