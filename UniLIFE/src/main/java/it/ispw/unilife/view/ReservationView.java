package it.ispw.unilife.view;

import it.ispw.unilife.bean.ReservationBean;
import it.ispw.unilife.bean.TutorBean;
import it.ispw.unilife.controller.BookingController;
import it.ispw.unilife.exception.DAOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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
    private BookingController bookingController;
    private LocalDate selectedDate;
    private LocalTime selectedTime;

    @FXML private Label detailSubjectLabel;
    @FXML private Label detailRateLabel;
    @FXML private Label detailNameLabel;
    @FXML private Label detailRatingLabel;
    @FXML private VBox otherTutorsContainer;

    @FXML
    public void initialize() {
        this.bookingController = new BookingController();
    }

    /**
     * Inizializza la pagina con i dati del tutor e imposta i valori di default per la prenotazione.
     * Simula la ricezione di data e ora (che verranno implementate dinamicamente in futuro).
     *
     * @param tutor Il tutor oggetto della prenotazione.
     */
    public void initReservationPage(TutorBean tutor) {
        this.currentTutor = tutor;
        this.reservationBean = BookingController.selectTutor(tutor);

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

        List<TutorBean> allTutors = bookingController.getAvailableTutors("");
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
    void onConfirmAndPayClick(ActionEvent event) throws IOException, DAOException {
        this.reservationBean.setDate(this.selectedDate);
        this.reservationBean.setStartTime(this.selectedTime);
        this.reservationBean.setEndTime(this.selectedTime.plusHours(1));
        this.bookingController.pendingReservation(reservationBean);
        showMessage(
                "Richiesta Inviata",
                "Operazione completata con successo",
                "La tua richiesta è stata inoltrata al tutor.\n" +
                        "Attendi l'accettazione nella tua Dashboard per procedere.",
                Alert.AlertType.INFORMATION
        );
        Navigator.getNavigatorInstance().goToTutor(event);

    }

    /**
     * Gestisce la navigazione a ritroso verso la lista dei tutor.
     *
     * @param event L'evento del click.
     * @throws IOException Se il file FXML di destinazione non viene trovato.
     */
    @FXML
    void onBackToTutor(ActionEvent event) throws IOException {
        Navigator.getNavigatorInstance().goToTutor(event);
    }

    @FXML
    void onSearch(ActionEvent event) {
        // Da implementare
    }

    private void showMessage(String messTitle, String title, String msg, Alert.AlertType alertType ) {
        Alert alert = new Alert(alertType);
        alert.setTitle(messTitle);
        alert.setHeaderText(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}