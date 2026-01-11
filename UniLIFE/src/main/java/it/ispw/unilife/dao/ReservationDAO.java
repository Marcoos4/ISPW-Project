package it.ispw.unilife.dao;

import it.ispw.unilife.model.Reservation;
import it.ispw.unilife.model.Student;
import it.ispw.unilife.model.Tutor;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO extends DAO<Reservation>{

    public List<Reservation> findByStudent(Student student);

    public List<Reservation> findByTutorAndDate(Tutor tutor, LocalDate date);

    List <Reservation> findByTutor(Tutor tutor);

    void update(Reservation reservation);
}
