package it.ispw.unilife.view;

import it.ispw.unilife.bean.ReservationBean;
import it.ispw.unilife.bean.TutorBean;
import it.ispw.unilife.controller.BookingCtrl;
import it.ispw.unilife.model.Tutor;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Gestisce l'interfaccia grafica per il riepilogo e la conferma della prenotazione.
 * Questa classe riceve i dati del tutor e della lezione scelta, mostra i dettagli
 * e permette di finalizzare l'operazione.
 */
public class ReservationView {

    private ReservationBean reservationBean;
    private TutorBean currentTutor;
    private BookingCtrl bookingCtrl;
    private LocalDate selectedDate;
    private LocalTime selectedTime;

    @FXML private Label detailSubjectLabel;
    @FXML private Label detailRateLabel;
    @FXML private Label detailNameLabel;
    @FXML private Label detailRatingLabel;
    @FXML private VBox otherTutorsContainer;

    /**
     * Metodo statico per caricare la scena ReservationPage e passare il tutor selezionato.
     * Gestisce il cambio di scena e l'inizializzazione del controller.
     *
     * @param event L'evento che ha scatenato la navigazione.
     * @param tutor Il bean del tutor selezionato.
     * @throws IOException Se il file FXML non viene trovato.
     */
    public static void display(Event event, TutorBean tutor) throws IOException {
        FXMLLoader loader = new FXMLLoader(ReservationView.class.getResource("/it/ispw/unilife/ReservationPage.fxml"));
        Parent root = loader.load();

        ReservationView controller = loader.getController();
        controller.initReservationPage(tutor);

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    @FXML
    public void initialize() {
        this.bookingCtrl = new BookingCtrl();
    }

    /**
     * Inizializza la pagina con i dati del tutor e imposta i valori di default per la prenotazione.
     * Simula la ricezione di data e ora (che verranno implementate dinamicamente in futuro).
     *
     * @param tutor Il tutor oggetto della prenotazione.
     */
    public void initReservationPage(TutorBean tutor) {
        this.currentTutor = tutor;
        this.reservationBean = BookingCtrl.selectTutor(tutor);

        this.selectedDate = LocalDate.now().plusDays(1);
        this.selectedTime = LocalTime.of(15, 0);

        if (detailNameLabel != null) {
            detailNameLabel.setText(tutor.getName() + " " + tutor.getSurname());
        }
        if (detailSubjectLabel != null) {
            String subject = !tutor.getSubjects().isEmpty() ? tutor.getSubjects().get(0) : "Generale";
            detailSubjectLabel.setText(subject);
        }
        if (detailRateLabel != null) {
            detailRateLabel.setText("€ " + tutor.getHourlyRate() + " / h");
        }
        if (detailRatingLabel != null) {
            detailRatingLabel.setText(tutor.getRating() + "/5");
        }

        populateOtherTutors();
    }

    /**
     * Popola la sidebar laterale con una lista di altri tutor suggeriti,
     * escludendo quello attualmente visualizzato.
     */
    private void populateOtherTutors() {
        if (otherTutorsContainer == null) return;
        otherTutorsContainer.getChildren().clear();

        List<TutorBean> allTutors = bookingCtrl.getAvailableTutors("");
        if (allTutors != null) {
            for (TutorBean t : allTutors) {
                if (currentTutor != null && t.getName().equals(currentTutor.getName())) {
                    continue;
                }

                VBox miniCard = new VBox();
                Label name = new Label(t.getName() + " " + t.getSurname());
                name.setStyle("-fx-font-weight: bold;");
                Label rating = new Label("★ " + t.getRating());
                rating.setStyle("-fx-text-fill: #ff9933;");

                miniCard.getChildren().addAll(name, rating);
                miniCard.setStyle("-fx-padding: 5; -fx-border-color: #eee; -fx-border-width: 0 0 1 0;");

                otherTutorsContainer.getChildren().add(miniCard);
            }
        }
    }

    /**
     * Gestisce il click sul pulsante di conferma.
     * Completa il bean con i dati temporali e anagrafici, invoca il salvataggio
     * tramite controller applicativo e reindirizza alla pagina di pagamento.
     *
     * @param event L'evento del click sul bottone.
     */
    @FXML
    void onConfirmAndPayClick(ActionEvent event) {
        try {
            this.reservationBean.setDate(this.selectedDate);
            this.reservationBean.setStartTime(this.selectedTime);
            this.reservationBean.setEndTime(this.selectedTime.plusHours(1));

            if (this.reservationBean.getStudentName() == null) {
                this.reservationBean.setStudentName("Studente Corrente");
            }


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/ispw/unilife/PaymentPage.fxml"));
            Parent root = loader.load();
            PaymentView paymentView = loader.getController();
            paymentView.initData(reservationBean);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gestisce la navigazione a ritroso verso la lista dei tutor.
     *
     * @param event L'evento del click.
     * @throws IOException Se il file FXML di destinazione non viene trovato.
     */
    @FXML
    void onBackToTutor(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ReservationView.class.getResource("/it/ispw/unilife/BookTutor.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    @FXML
    void onSearch(ActionEvent event) {
        // Da implementare
    }
}