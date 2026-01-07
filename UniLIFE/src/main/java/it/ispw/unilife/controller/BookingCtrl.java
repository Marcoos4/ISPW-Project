package it.ispw.unilife.controller;

import com.stripe.exception.StripeException;
import com.stripe.param.PaymentIntentCreateParams;
import it.ispw.unilife.bean.PaymentBean;
import it.ispw.unilife.bean.ReservationBean;
import it.ispw.unilife.bean.TutorBean;
import it.ispw.unilife.boundary.StripeBoundary;
import it.ispw.unilife.dao.DAOFactory;
import it.ispw.unilife.dao.ReservationDAO;
import it.ispw.unilife.model.Reservation;
import it.ispw.unilife.model.ReservationStatus;
import it.ispw.unilife.model.Tutor;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookingCtrl {

    public static ReservationBean selectTutor(TutorBean tutorBean) {
        Tutor tutor = new Tutor(tutorBean.getName(), tutorBean.getSurname(), tutorBean.getSubjects(), tutorBean.getHourlyRate(), tutorBean.getRating());
        ReservationBean reservationBean = new  ReservationBean();
        reservationBean.setTutor(tutor);
        return reservationBean;
    }

    /**
     * Rende persistente la prenotazione utilizzando il DAO configurato (File System o DB).
     * Questo metodo viene invocato internamente solo dopo la conferma del pagamento.
     *
     * @param reservation Il model contenente i dati della prenotazione da salvare.
     */
    private void saveReservation(Reservation reservation) {
        ReservationDAO reservationDAO = DAOFactory.getDAOFactory().getReservationDAO();
        reservationDAO.insert(reservation);
    }

    private long calculateDuration(LocalTime start, LocalTime end){
        return Duration.between(start, end).toHours();
    }

    /**
     * Gestisce l'intero flusso di prenotazione: calcolo del costo, esecuzione del pagamento tramite Stripe
     * e salvataggio della prenotazione in caso di successo.
     *
     * @param reservationBean I dati della lezione (Tutor, Data, Orari).
     * @param paymentBean I dati del metodo di pagamento utilizzato.
     * @return true se il pagamento va a buon fine e la prenotazione viene salvata, false altrimenti.
     */
    public boolean bookLesson(ReservationBean reservationBean, PaymentBean paymentBean) {
        StripeBoundary stripeBoundary = new StripeBoundary();
        Reservation reservationModel = new Reservation(reservationBean.getTutor(), reservationBean.getStudentName(), reservationBean.getDate(),calculateDuration(reservationBean.getStartTime(), reservationBean.getEndTime()));
        long amountInCents = reservationModel.calculateTotalCostInCents();

        if (amountInCents <= 0) {
            System.err.println("Errore: Impossibile procedere con importo nullo o negativo.");
            return false;
        }

        PaymentIntentCreateParams params = stripeBoundary.setUpPayment(amountInCents, "eur", "pm_card_visa");

        try {
            boolean isPaid = stripeBoundary.doPayment(params);
            String payment_id = "something";
            //TODO implementare qui il prima possibile

            if (isPaid) {
                reservationModel.updatePayment(ReservationStatus.CONFIRMED, payment_id);
                saveReservation(reservationModel);
                return true;
            } else {
                reservationModel.updatePayment(ReservationStatus.CONFIRMED, payment_id);
                saveReservation(reservationModel);
                return false;
            }

        } catch (StripeException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Recupera la lista dei tutor disponibili nel sistema.
     * Attualmente restituisce una lista statica (Mock) per fini di test/sviluppo.
     *
     * @param query Stringa di ricerca opzionale per filtrare i risultati.
     * @return Una lista di TutorBean disponibili.
     */
    public List<TutorBean> getAvailableTutors(String query) {
        List<TutorBean> dummyList = new ArrayList<>();

        dummyList.add(new TutorBean("Mario", "Rossi",
                Arrays.asList("Matematica", "Analisi 1", "Geometria"), 15.0, 3));

        dummyList.add(new TutorBean("Luigi", "Verdi",
                Arrays.asList("Fisica", "Meccanica Razionale"), 20.0, 5));

        dummyList.add(new TutorBean("Anna", "Bianchi",
                Arrays.asList("Informatica", "Java", "Basi di Dati"), 18.0, 4));

        dummyList.add(new TutorBean("Giulia", "Neri",
                Arrays.asList("Chimica", "Biologia"), 12.0, 2));

        return dummyList;
    }

    /**
     * Gestisce la logica di selezione di un tutor (es. logging o preparazione dati).
     *
     * @param tutor Il tutor selezionato dall'utente.
     */
    public void processSelection(TutorBean tutor) {
        System.out.println("BookingCtrl: Selezionato tutor " + tutor.getName());
    }
}