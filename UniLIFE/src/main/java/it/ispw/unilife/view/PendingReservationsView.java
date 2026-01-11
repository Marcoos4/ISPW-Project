package it.ispw.unilife.view;

import it.ispw.unilife.bean.ReservationBean;
import it.ispw.unilife.controller.BookingCtrl;
import it.ispw.unilife.model.ReservationStatus;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;

public class PendingReservationsView {

    @FXML
    private VBox requestsContainer; // Il contenitore delle righe
    @FXML
    private Label lblMessage;

    private BookingCtrl bookingCtrl = new BookingCtrl();

    @FXML
    public void initialize() {
        loadPendingRequests();
    }

    private void loadPendingRequests() {
        // 1. Pulisco il contenitore (per evitare duplicati se ricarico)
        requestsContainer.getChildren().clear();

        // 2. Prendo i dati
        List<ReservationBean> pending = bookingCtrl.getPendingReservation();

        if (pending.isEmpty()) {
            lblMessage.setText("Nessuna richiesta in attesa.");
            return;
        }

        lblMessage.setText("Richieste da gestire: " + pending.size());

        // 3. CICLO FOR: Per ogni prenotazione creo una riga grafica
        for (ReservationBean bean : pending) {

            // Creo la riga (HBox)
            HBox row = new HBox();
            row.setAlignment(Pos.CENTER_LEFT);
            row.setSpacing(10);
            row.setPadding(new Insets(10));
            // Un po' di stile per bordi e colore
            row.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-color: white; -fx-background-radius: 5;");

            // -- ELEMENTI --

            // Info Text
            Label nameLabel = new Label(bean.getStudent().getName() + " " + bean.getStudent().getSurname());
            nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            Label dateLabel = new Label(bean.getDate() + " - " + bean.getStartTime() + " - " + bean.getEndTime());

            VBox infoBox = new VBox(5, nameLabel, dateLabel);

            // Spacer (spinge i bottoni a destra)
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            // Bottoni
            Button btnAccept = new Button("Accetta");
            btnAccept.setStyle("-fx-background-color: lightgreen; -fx-cursor: hand;");

            Button btnReject = new Button("Rifiuta");
            btnReject.setStyle("-fx-background-color: #ffcccc; -fx-cursor: hand;");

            // -- AZIONI --

            btnAccept.setOnAction(event -> {
                bookingCtrl.manageReservation(bean, ReservationStatus.ACCEPTED);
                requestsContainer.getChildren().remove(row);
                updateCounter();
            });

            btnReject.setOnAction(event -> {
                bookingCtrl.manageReservation(bean, ReservationStatus.REJECTED);
                requestsContainer.getChildren().remove(row);
                updateCounter();
            });

            // -- ASSEMBLAGGIO --
            row.getChildren().addAll(infoBox, spacer, btnAccept, btnReject);

            // Aggiungo la riga al contenitore principale
            requestsContainer.getChildren().add(row);
        }
    }

    // Metodo helper per aggiornare il contatore in basso
    private void updateCounter() {
        int remaining = requestsContainer.getChildren().size();
        if (remaining == 0) {
            lblMessage.setText("Tutto fatto! Nessuna richiesta in attesa.");
            // Qui puoi mettere il redirect alla dashboard
            // SceneManager.getInstance().changeScene("Dashboard.fxml");
        } else {
            lblMessage.setText("Richieste da gestire: " + remaining);
        }
    }
}