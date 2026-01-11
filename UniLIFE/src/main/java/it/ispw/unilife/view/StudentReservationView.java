package it.ispw.unilife.view;

import it.ispw.unilife.bean.ReservationBean;
import it.ispw.unilife.controller.BookingCtrl;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class StudentReservationView {

    @FXML private VBox paymentContainer;
    @FXML private Label lblMessage;

    private BookingCtrl bookingCtrl = new BookingCtrl();

    @FXML
    public void initialize() {
        loadUnpaidReservations();
    }

    private void loadUnpaidReservations() {
        paymentContainer.getChildren().clear();

        // Chiamo il metodo LATO STUDENTE
        List<ReservationBean> toPay = bookingCtrl.getAcceptedReservationsForStudent();

        if (toPay.isEmpty()) {
            lblMessage.setText("Non hai lezioni in attesa di pagamento.");
            return;
        }

        lblMessage.setText("Devi saldare " + toPay.size() + " lezioni.");

        for (ReservationBean bean : toPay) {

            // --- Creazione Riga Grafica ---
            HBox row = new HBox();
            row.setAlignment(Pos.CENTER_LEFT);
            row.setSpacing(15);
            row.setPadding(new Insets(15));
            row.setStyle("-fx-border-color: #e0e0e0; -fx-border-radius: 8; -fx-background-color: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 0);");

            // 1. Info Tutor
            Label lblTutor = new Label("Tutor: " + bean.getTutor().getName() + " " + bean.getTutor().getSurname());
            lblTutor.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

            // 2. Info Data e Ora
            Label lblDate = new Label(bean.getDate().toString() + " alle " + bean.getStartTime().toString());
            lblDate.setStyle("-fx-text-fill: #555;");

            VBox infoBox = new VBox(5, lblTutor, lblDate);

            // Spaziatore
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            // 3. Prezzo
            Label lblPrice = new Label("€ " + String.format("%.2f", bean.getTotalPrice()));
            lblPrice.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #333;");

            // 4. Bottone Paga
            Button btnPay = new Button("Paga Ora");
            btnPay.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 20 8 20; -fx-background-radius: 5;");

            // --- AZIONE ---
            btnPay.setOnAction(e -> {
                // Chiama la logica di pagamento
                try {
                    Navigator.getNavigatorInstance().goToPayment(e, bean);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                // Rimuovi la riga (feedback immediato)
                paymentContainer.getChildren().remove(row);

                // Aggiorna messaggio
                if (paymentContainer.getChildren().isEmpty()) {
                    lblMessage.setText("Pagamenti completati! Buono studio.");
                } else {
                    lblMessage.setText("Pagamento effettuato. Rimangono " + paymentContainer.getChildren().size() + " lezioni.");
                }
            });

            // Assemblaggio
            row.getChildren().addAll(infoBox, spacer, lblPrice, btnPay);
            paymentContainer.getChildren().add(row);
        }
    }
}