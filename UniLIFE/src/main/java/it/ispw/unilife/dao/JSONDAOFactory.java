package it.ispw.unilife.dao;

public class JSONDAOFactory extends DAOFactory{

    public JSONDAOFactory() {
        super();
    }

    @Override
    public UserDAO getUserDAO(){ return new JSONUserDAO();}
}
