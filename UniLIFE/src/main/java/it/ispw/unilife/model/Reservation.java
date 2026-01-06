package it.ispw.unilife.model;

import it.ispw.unilife.bean.ReservationBean;
import java.time.LocalDate;

public class Reservation {

    // Riferimento diretto all'istanza del Tutor
    private Tutor tutor;
    private String studentName;
    private LocalDate date;
    private long durationInHours;
    private String paymentId;
    private ReservationStatus status;

    // -------------------------------------------------------------------------
    // COSTRUTTORI
    // -------------------------------------------------------------------------

    public Reservation(Tutor tutor, String studentName, LocalDate date, long duration) {
        this.tutor = tutor;
        this.studentName = studentName;
        this.date = date;
        this.durationInHours = duration;
        this.status = status;
        this.paymentId = null;
    }

    // -------------------------------------------------------------------------
    // OPERAZIONI DI BUSINESS
    // -------------------------------------------------------------------------

    public long calculateTotalCostInCents() {
        // Usiamo il riferimento al tutor per prendere la tariffa aggiornata
        if (this.tutor == null) return 0;
        return (long) (this.tutor.getHourlyRate() * this.durationInHours * 100);
    }

    public void updatePayment(ReservationStatus status, String payment_id){
        this.status = status;
        this.paymentId = payment_id;
    }

    public void cancelBooking() {
        if (this.date != null && this.date.isBefore(LocalDate.now())) {
            throw new IllegalStateException("Errore: Impossibile cancellare una lezione passata.");
        }
        this.status = ReservationStatus.CANCELLED;
    }

    // -------------------------------------------------------------------------
    // GETTERS
    // -------------------------------------------------------------------------

    public Tutor getTutor() { return tutor; }
    public String getStudentName() { return studentName; }
    public LocalDate getDate() { return date; }
    public long getDurationInHours() { return durationInHours; }
    public String getPaymentId() { return paymentId; }
    public ReservationStatus getStatus() { return this.status; }
}