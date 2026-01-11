package it.ispw.unilife.dao;

import it.ispw.unilife.model.Tutor;

import java.util.List;

public class JSONTutorDAO extends  JSONDAO<Tutor> implements TutorDAO{

    protected JSONTutorDAO() {
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
}
