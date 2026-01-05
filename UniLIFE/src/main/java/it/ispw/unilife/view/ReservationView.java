package it.ispw.unilife.view;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import it.ispw.unilife.bean.TutorBean;
import it.ispw.unilife.controller.BookingCtrl;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ReservationView {

    // --- ELEMENTI FXML SPECIFICI DELLA PAGINA ---
    @FXML private Label detailSubjectLabel;
    @FXML private Label detailRateLabel;
    @FXML private Label detailNameLabel;
    @FXML private Label detailRatingLabel;
    @FXML private VBox otherTutorsContainer;

    // --- ELEMENTI FXML DELL'HEADER (Copiati dall'altra pagina) ---
    @FXML private TextField searchField;

    // --- DATI E CONTROLLER ---
    private BookingCtrl bookingCtrl;
    private TutorBean currentTutor;

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

    public void initReservationPage(TutorBean tutor) {
        this.currentTutor = tutor;

        System.out.println("ReservationView inizializzata con: " + tutor.getName());

        if (detailNameLabel != null) {
            detailNameLabel.setText(tutor.getName() + " " + tutor.getSurname());
        }

        if (detailSubjectLabel != null && !tutor.getSubjects().isEmpty()) {
            detailSubjectLabel.setText(tutor.getSubjects().get(0));
        } else {
            if(detailSubjectLabel != null) detailSubjectLabel.setText("Nessuna materia");
        }

        if (detailRateLabel != null) {
            detailRateLabel.setText("€ " + tutor.getHourlyRate() + " / h");
        }

        if (detailRatingLabel != null) {
            detailRatingLabel.setText(tutor.getRating() + "/5");
        }

        populateOtherTutors();
    }

    private void populateOtherTutors() {
        if (otherTutorsContainer == null) return;

        otherTutorsContainer.getChildren().clear();
        List<TutorBean> allTutors = bookingCtrl.getAvailableTutors("");

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

    // --- GESTIONE EVENTI ---

    @FXML
    void onSearch(ActionEvent event) {
        System.out.println("Ricerca dalla pagina di prenotazione non ancora implementata.");
    }

    @FXML
    void onConfirmAndPayClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/ispw/unilife/PaymentPage.fxml"));
            Parent root = loader.load();

            PaymentView paymentView = loader.getController();
            paymentView.initData(this.currentTutor);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onBackToTutor(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ReservationView.class.getResource("/it/ispw/unilife/BookTutor.fxml"));
        Parent root = loader.load();

        BookTutorView controller = loader.getController();

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        stage.getScene().setRoot(root);
    }

    // Aggiungi qui eventuali altri metodi per i bottoni
}