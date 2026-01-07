package it.ispw.unilife.dao;

public class JSONDAOFactory extends DAOFactory{

    @Override
    public ReservationDAO getReservationDAO() {
        return new JSONReservationDAO();
    }
}
