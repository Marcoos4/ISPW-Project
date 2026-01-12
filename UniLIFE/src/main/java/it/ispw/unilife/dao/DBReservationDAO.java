package it.ispw.unilife.dao;

import com.google.api.client.util.DateTime;
import it.ispw.unilife.exception.DAOException;
import it.ispw.unilife.model.Reservation;
import it.ispw.unilife.model.ReservationStatus;
import it.ispw.unilife.model.Student;
import it.ispw.unilife.model.Tutor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DBReservationDAO implements ReservationDAO{

    private static List<Tutor> reservations = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(DBTutorDAO.class.getName());

    @Override
    public List<Reservation> getAll() {

        return List.of();
    }

    @Override
    public void insert(Reservation reservation) {
        String query = "INSERT INTO `reservation`(`student_username`, `tutor_username`, `dateTime`, `duration`, `payment_id`, `status`) VALUES (?,?,?,?,?,?)";

        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            System.out.println(reservation.getTutor().getUserName());
            stmt.setString(1, reservation.getStudent().getUserName());
            stmt.setString(2, reservation.getTutor().getUserName());
            stmt.setTimestamp(3, Timestamp.valueOf(reservation.getDate()));
            stmt.setInt(4, (int) reservation.getDurationInHours());
            stmt.setString(5, null);
            stmt.setString(6, ReservationStatus.statusToString(reservation.getStatus()));


            stmt.executeUpdate(); // Esegue il salvataggio nel DB
            logger.info("Reservation salvata correttamente nel database!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
