package it.ispw.unilife.dao;

public class DAOFactory {
    private static DAOFactory instance = null;

    private DAOFactory(){}

    public synchronized static DAOFactory getDAOFactory(){
        if (DAOFactory.instance == null) {
            DAOFactory.instance = new DAOFactory();
        }

        return instance;
    }

    public ReservationDAO getReservationDAO(String type) throws IllegalArgumentException {
        switch (type.toLowerCase()){
            case "db" -> {
                return new DBResevationDAO();
            }
            case "fs" -> {
                return new JSONReservationDAO();
            }
            default -> throw new IllegalArgumentException();
        }
    }
}
