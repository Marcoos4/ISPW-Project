package it.ispw.unilife.bean;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationBean {
    private String reservationId;
    private String studentName;
    private TutorBean tutor;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    private double totalPrice;
    private String status;

    // Costruttore VUOTO
    public ReservationBean() {}

    // Costruttore COMPLETO
    public ReservationBean(String reservationId, String studentName, TutorBean tutor,
                           LocalDate date, LocalTime startTime, LocalTime endTime,
                           double totalPrice, String status) {
        this.reservationId = reservationId;
        this.studentName = studentName;
        this.tutor = tutor;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    // --- GETTERS & SETTERS ---

    public String getReservationId() { return reservationId; }
    public void setReservationId(String reservationId) { this.reservationId = reservationId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public TutorBean getTutor() { return tutor; }
    public void setTutor(TutorBean tutor) { this.tutor = tutor; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}