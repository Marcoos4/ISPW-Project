package it.ispw.unilife.bean;

import it.ispw.unilife.model.ReservationStatus;
import it.ispw.unilife.model.Tutor; // Riferimento esplicito al Model

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationBean {
    private String studentName;

    // Riferimento all'istanza del Tutor (Model)
    private Tutor tutor;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    private double totalPrice;
    private ReservationStatus status = ReservationStatus.PENDING;

    // Costruttore VUOTO
    public ReservationBean() {}

    // Costruttore COMPLETO
    public ReservationBean(String studentName, Tutor tutor,
                           LocalDate date, LocalTime startTime, LocalTime endTime,
                           double totalPrice, ReservationStatus status) {
        this.studentName = studentName;
        this.tutor = tutor;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    // --- GETTERS & SETTERS ---

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public Tutor getTutor() { return tutor; }
    public void setTutor(Tutor tutor) { this.tutor = tutor; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }
}