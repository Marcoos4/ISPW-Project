package it.ispw.unilife.controller;

import com.stripe.exception.StripeException;
import com.stripe.param.PaymentIntentCreateParams;
import it.ispw.unilife.bean.PaymentBean;
import it.ispw.unilife.bean.ReservationBean;
import it.ispw.unilife.bean.StudentBean;
import it.ispw.unilife.bean.TutorBean;
import it.ispw.unilife.boundary.StripeBoundary;
import it.ispw.unilife.dao.ReservationDAO;
import it.ispw.unilife.exception.DAOException;
import it.ispw.unilife.model.*;
import it.ispw.unilife.model.session.SessionManager;
import it.ispw.unilife.dao.factory.DAOFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookingCtrl {

    public static ReservationBean selectTutor(TutorBean tutorBean) {
        ReservationBean reservationBean = new  ReservationBean();
        reservationBean.setTutor(tutorBean);
        return reservationBean;
    }

    /**
     * Rende persistente la prenotazione utilizzando il DAO configurato (File System o DB).
     * Questo metodo viene invocato internamente solo dopo la conferma del pagamento.
     *
     * @param reservation Il model contenente i dati della prenotazione da salvare.
     */
    private void updateReservation(Reservation reservation) {
        ReservationDAO reservationDAO = DAOFactory.getDAOFactory().getReservationDAO();
        reservationDAO.update(reservation);
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
        Reservation reservationModel = new Reservation(convertTutorBeanToModel(reservationBean.getTutor()), (Student) DAOFactory.getDAOFactory().getStudentDAO(), LocalDateTime.of(reservationBean.getDate(), reservationBean.getStartTime()) ,
                                            calculateDuration(reservationBean.getStartTime(), reservationBean.getEndTime()));
        long amountInCents = reservationModel.calculateTotalCostInCents();

        if (amountInCents <= 0) {
            System.err.println("Errore: Impossibile procedere con importo nullo o negativo.");
            return false;
        }

        PaymentIntentCreateParams params = stripeBoundary.setUpPayment(amountInCents, "eur", "pm_card_visa");

        try {
            boolean isPaid = stripeBoundary.doPayment(params);
            Payment payment = new Payment();
            //TODO implementare qui il prima possibile

            if (isPaid) {
                reservationModel.updatePayment(ReservationStatus.CONFIRMED, payment);
                updateReservation(reservationModel);
                return true;
            } else {
                reservationModel.updatePayment(ReservationStatus.CONFIRMED, payment);
                updateReservation(reservationModel);
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

    public List<ReservationBean> getPendingReservation(){
        Tutor user = (Tutor) SessionManager.getInstance().getSession().getUser();
        List<Reservation> reservations = DAOFactory.getDAOFactory().getReservationDAO().findByTutor(user);
        List<ReservationBean> beanList = new ArrayList<>();
        for (Reservation model : reservations) {
            beanList.add(convertReservationToBean(model));
        }

        return beanList;
    }

    private ReservationBean convertReservationToBean(Reservation reservation) {
        ReservationBean reservationBean = new ReservationBean();
        StudentBean studentBean = new StudentBean();
        studentBean.setName(reservation.getStudent().getName());
        studentBean.setSurname(reservation.getStudent().getSurname());
        TutorBean tutorBean = new TutorBean();
        tutorBean.setSubjects(reservation.getTutor().getSubjects());
        tutorBean.setSurname(reservation.getTutor().getSurname());
        tutorBean.setName(reservation.getTutor().getName());
        tutorBean.setRating(reservation.getTutor().getRating());
        tutorBean.setHourlyRate(reservation.getTutor().getHourlyRate());
        reservationBean.setStudent(studentBean);
        reservationBean.setTutor(tutorBean);
        reservationBean.setDate(reservation.getDate().toLocalDate());
        reservationBean.setStartTime(reservation.getDate().toLocalTime());
        reservationBean.setEndTime(reservation.getDate().toLocalTime().plusHours(reservation.getDurationInHours()));

        return reservationBean;
    }

    public void pendingReservation(ReservationBean reservationBean) throws DAOException {
        Reservation reservation = convertReservationBeanToModel(reservationBean);
        reservation.updatePayment(ReservationStatus.PENDING, null);
        DAOFactory.getDAOFactory().getReservationDAO().insert(reservation);
    }

    private Tutor convertTutorBeanToModel(TutorBean tutorBean){
        User user = retrieveActiveUser();
        return new Tutor(tutorBean.getName(), tutorBean.getSurname(), tutorBean.getSubjects(), tutorBean.getHourlyRate(), tutorBean.getRating(), user.getUserName(), user.getPassword());
    }

    private Reservation convertReservationBeanToModel(ReservationBean reservationBean){
        User user = retrieveActiveUser();
        return new Reservation(convertTutorBeanToModel(reservationBean.getTutor()), new Student(user.getName(), user.getSurname(), user.getUserName(), user.getPassword()), LocalDateTime.of(reservationBean.getDate(), reservationBean.getStartTime()), getHoursDifference(reservationBean.getStartTime(), reservationBean.getEndTime()));
    }

    private long getHoursDifference(LocalTime start, LocalTime end) {
        return ChronoUnit.HOURS.between(start, end);
    }

    private User retrieveActiveUser(){
        return SessionManager.getInstance().getSession().getUser();
    }

    public void manageReservation(ReservationBean bean, ReservationStatus reservationStatus) {
        Reservation reservation = convertReservationBeanToModel(bean);
        reservation.updatePayment(reservationStatus, null);
        updateReservation(reservation);
    }

    public List<ReservationBean> getAcceptedReservationsForStudent() {
        Student user = (Student) SessionManager.getInstance().getSession().getUser();
        List<Reservation> reservations = DAOFactory.getDAOFactory().getReservationDAO().findByStudent(user);
        List<ReservationBean> beanList = new ArrayList<>();
        for (Reservation model : reservations) {
            beanList.add(convertReservationToBean(model));
        }

        return beanList;
    }
}