package it.ispw.unilife.dao;

import it.ispw.unilife.model.Tutor;
import it.ispw.unilife.model.User;

public interface TutorDAO extends DAO<Tutor>{
    User findTutorByUsername(String userName);
}
