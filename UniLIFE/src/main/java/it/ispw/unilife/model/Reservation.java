package it.ispw.unilife.model;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Reservation {

    private Tutor tutor;
    private Student student;
    private LocalDateTime date;
    private long durationInHours;
    private Payment payment;
    private ReservationStatus status;

    // -------------------------------------------------------------------------
    // COSTRUTTORI
    // -------------------------------------------------------------------------

    public Reservation(Tutor tutor, Student student, LocalDateTime date, long duration) {
        this.tutor = tutor;
        this.student = student;
        this.date = date;
        this.durationInHours = duration;
        this.status = status;
        this.payment = null;
    }

    // -------------------------------------------------------------------------
    // OPERAZIONI DI BUSINESS
    // -------------------------------------------------------------------------

    public long calculateTotalCostInCents() {
        // Usiamo il riferimento al tutor per prendere la tariffa aggiornata
        if (this.tutor == null) return 0;
        return (long) (this.tutor.getHourlyRate() * this.durationInHours * 100);
    }

    public void updatePayment(ReservationStatus status, Payment payment){
        this.status = status;
        this.payment = payment;
    }

    public void cancelBooking() {
        if (this.date != null && this.date.isBefore(LocalDateTime.now(ZoneId.of("Europe/Rome")))) {
            throw new IllegalStateException("Errore: Impossibile cancellare una lezione passata.");
        }
        this.status = ReservationStatus.CANCELLED;
    }

    // -------------------------------------------------------------------------
    // GETTERS
    // -------------------------------------------------------------------------

    public Tutor getTutor() { return tutor; }
    public Student getStudent() { return student; }
    public LocalDateTime getDate() { return date; }
    public long getDurationInHours() { return durationInHours; }
    public Payment getPayment() { return payment; }
    public ReservationStatus getStatus() { return this.status; }
}