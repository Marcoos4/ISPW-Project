package it.ispw.unilife.dao;

import it.ispw.unilife.model.Reservation;
import it.ispw.unilife.model.Tutor;

import java.time.LocalDate;
import java.util.List;

public class DBReservationDAO implements ReservationDAO{

    @Override
    public Reservation getInstance() {

        return null;
    }

    @Override
    public List<Reservation> getAll() {

        return List.of();
    }

    @Override
    public int insert(Reservation reservation) {
        return 0;
    }

    @Override
    public void saveTo(){

    }

    @Override
    public int delete() {
        return 0;
    }

    @Override
    public int update() {
        return 0;
    }

    @Override
    public List<Reservation> findByStudent(String studentName) {
        return List.of();
    }

    @Override
    public List<Reservation> findByTutorAndDate(Tutor tutor, LocalDate date) {
        return List.of();
    }
}
