package it.ispw.unilife.dao;

import it.ispw.unilife.model.Tutor;
import it.ispw.unilife.model.User;

import java.util.List;

public class JSONTutorDAO extends  JSONDAO<Tutor> implements TutorDAO{

    public JSONTutorDAO() {
        super("data/tutor.json", Tutor.class);
    }

    @Override
    public List getAll() {
        return List.of();
    }

    @Override
    public void insert(Tutor tutor) {
    }

    public void saveTo() {

    }

    @Override
    public User findTutorByUsername(String userName) {
        return null;
    }
}
