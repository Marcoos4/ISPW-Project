package it.ispw.unilife.dao;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.ispw.unilife.model.Reservation;
import it.ispw.unilife.model.ReservationStatus;
import it.ispw.unilife.model.Student;
import it.ispw.unilife.model.Tutor;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class JSONReservationDAO extends JSONDAO<Reservation> implements ReservationDAO{

    private List<Reservation> reservations;


    public JSONReservationDAO() {
        super("data/reservation.json", Reservation.class);
    }


    @Override
    public List<Reservation> getAll() {
        try (Reader reader = new FileReader(this.file)) {
            Type listType = new TypeToken<ArrayList<Reservation>>(){}.getType();

            List<Reservation> fromFile = gson.fromJson(reader, listType);

            if (fromFile != null) {
                this.reservations = fromFile;
            } else {
                this.reservations = new ArrayList<>();
            }

        } catch (FileNotFoundException e) {
            this.reservations = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.reservations;
    }

    @Override
    public void insert(Reservation reservation) {

        if (reservation.getStatus() == null) {
            System.err.println("ERRORE DAO: Tentativo di salvare prenotazione PENDING scartato.");
        }

        getAll();

        this.reservations.add(reservation);

        saveTo();
    }

    public void saveTo() {
        try(Writer writer = new FileWriter(this.file)){
            gson.toJson(this.reservations, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Reservation> findByStudent(Student studentName) {
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
