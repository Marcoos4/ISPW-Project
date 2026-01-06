package it.ispw.unilife.dao;

import it.ispw.unilife.model.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO extends DAO<Reservation>{

    public List<Reservation> findByStudent(String studentName);

    public List<Reservation> findByTutorAndDate(String tutor, LocalDate date);
}
