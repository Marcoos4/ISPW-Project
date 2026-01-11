package it.ispw.unilife.dao;

import it.ispw.unilife.model.Student;

public class DBDAOFactory extends DAOFactory {


    public DBDAOFactory() {
        super();
    }

    @Override
    public ReservationDAO getReservationDAO() {
        return null;
    }

    @Override
    public UserDAO getUserDAO(){ return new DBUserDAO();}

    @Override
    public Student getStudentDAO() {
        return null;
    }

}
