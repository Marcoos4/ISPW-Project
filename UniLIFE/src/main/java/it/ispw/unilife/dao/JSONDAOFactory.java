package it.ispw.unilife.dao;

import it.ispw.unilife.model.Student;

public class JSONDAOFactory extends DAOFactory{

    public JSONDAOFactory() {
        super();
    }

    @Override
    public ReservationDAO getReservationDAO() {
        return null;
    }

    @Override
    public UserDAO getUserDAO(){ return new JSONUserDAO();}

    @Override
    public Student getStudentDAO() {
        return null;
    }
}
