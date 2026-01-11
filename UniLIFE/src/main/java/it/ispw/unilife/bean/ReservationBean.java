package it.ispw.unilife.bean;

import it.ispw.unilife.model.ReservationStatus;
import it.ispw.unilife.model.Tutor; // Riferimento esplicito al Model

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationBean {
    private TutorBean tutor;
    private StudentBean student;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    private double totalPrice;
    private ReservationStatus status = ReservationStatus.PENDING;

    // Costruttore VUOTO
    public ReservationBean() {}

    // --- GETTERS & SETTERS ---

    public TutorBean getTutor() { return tutor; }
    public void setTutor(TutorBean tutor) { this.tutor = tutor; }

    public StudentBean getStudent() {return student;}
    public void setStudent(StudentBean student) {this.student = student;}

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