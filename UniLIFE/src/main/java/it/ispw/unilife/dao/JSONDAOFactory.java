package it.ispw.unilife.dao;

public class JSONDAOFactory extends DAOFactory{
    public JSONReservationDAO getReservationDAO(){
        return new JSONReservationDAO();
    }
}
