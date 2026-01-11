package it.ispw.unilife.model;

public enum ReservationStatus {
    PENDING,    // Creata, in attesa di azione
    CONFIRMED,  // Pagamento OK
    FAILED,     // Pagamento Rifiutato (es. fondi insufficienti)
    CANCELLED,   // Annullata dall'utente (tasto Indietro/Annulla)
    REJECTED,
    ACCEPTED
}