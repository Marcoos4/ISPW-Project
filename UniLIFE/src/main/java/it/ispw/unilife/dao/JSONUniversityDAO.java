package it.ispw.unilife.dao;

import it.ispw.unilife.exception.DAOException;
import it.ispw.unilife.model.University;

import java.util.List;
import java.util.logging.Level;

public class JSONUniversityDAO extends JSONDAO<University> implements UniversityDAO {

    public JSONUniversityDAO(){
        super("data/universities.json", University.class);
    }

    @Override
    public University findByName(String uniName) throws DAOException {

        List<University> unis;
        try {
            unis = super.getAll();
        } catch (DAOException e) {
            logger.log(Level.SEVERE, "Critical error reading users file", e);
            throw new RuntimeException();
        }

        for (University u : unis) {

            if (u.getName().equalsIgnoreCase(uniName)) {
                return u;
            }
        }
        throw new DAOException("Can't find University: " + uniName);
    }
}
