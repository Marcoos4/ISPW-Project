package it.ispw.unilife.dao;

import it.ispw.unilife.exception.DAOException;
import it.ispw.unilife.model.Reservation;
import it.ispw.unilife.model.Student;
import it.ispw.unilife.model.Tutor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBReservationDAO implements ReservationDAO{

    @Override
    public List<Reservation> getAll() {

        return List.of();
    }

    @Override
    public void insert(Reservation reservation) {
    }

    @Override
    public void delete(Reservation reservation) throws DAOException {

    }


    @Override
    public List<Reservation> findByStudent(Student student) {
        return List.of();
    }

    @Override
    public List<Reservation> findByTutorAndDate(Tutor tutor, LocalDate date) {
        return List.of();
    }

    @Override
    public List<Reservation> findByTutor(Tutor tutor) {
        List<Reservation> tutorReservations = new ArrayList<>();
        List<Reservation> reservs= getAll();
        for(Reservation reservation : reservs){
            if (reservation.getTutor().equals(tutor)){
                tutorReservations.add(reservation);
            }
        }
        return tutorReservations;
    }

    @Override
    public void update(Reservation reservation) {

    }
}
