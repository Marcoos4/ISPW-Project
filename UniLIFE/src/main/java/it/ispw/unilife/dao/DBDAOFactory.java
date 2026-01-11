package it.ispw.unilife.dao;

public class DBDAOFactory extends DAOFactory {

    @Override
    public ReservationDAO getReservationDAO() {
        return new DBReservationDAO();
    }
}
