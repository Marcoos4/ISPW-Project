package it.ispw.unilife.dao;

import it.ispw.unilife.model.Tutor;

import java.util.List;

public interface SubjectDAO extends DAO<Tutor>{
    List<String> findSubjectByUsername(String userName);
}
