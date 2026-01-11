package it.ispw.unilife.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Student extends User{

    private List<Reservation> reservations;

    public Student(String name, String surname, String username, String password){
        super(username, name, surname, password, Role.STUDENT);
        this.reservations = new ArrayList<>();
    }

    public List<Reservation> getStudentTutor(Student student){
        return student.reservations;
    }

    public void addTutorLesson(Reservation reservation){
        reservations.add(reservation);
    }

    public List<Reservation> notifyImminentBooking(Student student){
        List<Reservation> imminentReservation = new ArrayList<>();
        for(Reservation reservation: student.reservations ){
            if(Student.isWithinTwoHours(reservation.getDate())){
                imminentReservation.add(reservation);
            }
        }
        return imminentReservation;
    }

    public static boolean isWithinTwoHours(LocalDateTime eventTime) {
        long minutesDiff = ChronoUnit.MINUTES.between(LocalDateTime.now(ZoneId.of("Europe/Rome")), eventTime);

        return minutesDiff >= 0 && minutesDiff < 120;
    }

}
