package it.ispw.unilife.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import it.ispw.unilife.controller.BookingController;
import it.ispw.unilife.bean.TutorBean;

import java.io.IOException;
import java.util.List;

/**
 * Graphic Controller per la schermata di ricerca e prenotazione Tutor.
 * Questa classe gestisce esclusivamente la logica di presentazione (View),
 * delegando la logica di business al Controller Applicativo (BookingCtrl).
 */
public class BookTutorView {

    @FXML
    private TextField searchField;

    @FXML
    private VBox tutorContainer;

    private BookingController bookingController;

    /**
     * Metodo di inizializzazione chiamato automaticamente da JavaFX dopo il caricamento dell'FXML.
     */
    @FXML
    public void initialize() {
        this.bookingController = new BookingController();
        List<TutorBean> initialList = bookingController.getAvailableTutors("");
        updateTutorContainer(initialList);
    }

    /**
     * Gestore dell'evento di ricerca (click sulla lente o Invio).
     * @param event L'evento generato dall'azione utente.
     */
    @FXML
    void onSearch(ActionEvent event) {
        String query = searchField.getText();
        List<TutorBean> filteredTutors = bookingController.getAvailableTutors(query);
        updateTutorContainer(filteredTutors);
    }

    /**
     * Metodo privato per aggiornare la lista visualizzata nel VBox.
     * @param tutors La lista di Bean da visualizzare.
     */
    private void updateTutorContainer(List<TutorBean> tutors) {
        tutorContainer.getChildren().clear();

        if (tutors.isEmpty()) {
            Label noResultsLabel = new Label("Nessun tutor trovato con questi criteri.");
            noResultsLabel.setStyle("-fx-text-fill: gray; -fx-padding: 10;");
            tutorContainer.getChildren().add(noResultsLabel);
            return;
        }

        for (TutorBean tutor : tutors) {
            HBox card = createTutorCard(tutor);
            tutorContainer.getChildren().add(card);
        }
    }

    /**
     * Factory method per creare l'elemento grafico (Card) del singolo Tutor.
     * @param tutor Il bean contenente i dati.
     * @return HBox configurato pronto per essere aggiunto alla scena.
     */
    private HBox createTutorCard(TutorBean tutor) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setSpacing(20);
        row.setStyle("-fx-background-color: white; -fx-padding: 30; " +
                "-fx-background-radius: 10; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");

        VBox infoBox = new VBox(5);
        Label nameLabel = new Label(tutor.getName() + " " + tutor.getSurname());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #333;");

        String subjectsStr = String.join(", ", tutor.getSubjects());
        Label subjectLabel = new Label(subjectsStr);
        subjectLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 12px;");

        infoBox.getChildren().addAll(nameLabel, subjectLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label starLabel = new Label(getStarsString(tutor.getRating()));
        starLabel.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 18px; -fx-font-weight: bold;");

        Button bookBtn = new Button("Prenota");
        bookBtn.setStyle("-fx-background-color: #ff9900; -fx-text-fill: white; " +
                "-fx-background-radius: 20; -fx-cursor: hand; -fx-font-weight: bold;");

        bookBtn.setOnAction(e -> {
            handleBookingAction(tutor, e);
        });

        row.getChildren().addAll(infoBox, spacer, starLabel, bookBtn);

        return row;
    }

    /**
     * Metodo Helper per generare la stringa grafica delle stelle.
     */
    private String getStarsString(int rating) {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < rating; i++) {
            stars.append("★");
        }
        for (int i = rating; i < 5; i++) {
            stars.append("☆");
        }
        return stars.toString();
    }

    /**
     * Gestisce l'intenzione dell'utente di prenotare uno specifico tutor.
     */
    private void handleBookingAction(TutorBean tutor, ActionEvent event) {
        try {
            Navigator.getNavigatorInstance().goToReservation(event, tutor);
        } catch (IOException e) {
            System.err.println("Errore nel caricamento della pagina di prenotazione");
            e.printStackTrace();
        }
    }

    /**
     * Gestisce il reset dei filtri di ricerca.
     */
    public void onResetFilters() {
        // Da implementare
    }
}