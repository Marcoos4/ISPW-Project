package it.ispw.unilife.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
// Ricordati di importare il tuo Bean e il Controller Applicativo
import it.ispw.unilife.bean.TutorBean;
import it.ispw.unilife.controller.BookingCtrl;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Graphic Controller per la gestione della schermata di Pagamento.
 * Questa classe raccoglie i dati di input dell'utente (dati carta simulati)
 * e delega la logica di business al Controller Applicativo (BookingCtrl).
 */
public class PaymentView {

    // --- Riferimenti FXML ---

    @FXML
    private TextField txtCardNumber; // Campo per il numero di carta

    @FXML
    private TextField txtCvv;        // Campo per il CVV

    @FXML
    private TextField txtExpiry;     // Campo scadenza

    @FXML
    private Label lblAmount;         // Label per mostrare l'importo dinamico da pagare

    /**
     * Riferimento al Controller Applicativo.
     * La View NON parla con DAO o Stripe direttamente, ma solo tramite lui.
     */
    private BookingCtrl bookingCtrl;

    /**
     * Il Tutor che l'utente ha selezionato nella schermata precedente.
     * Contiene le informazioni necessarie (prezzo, nome) per procedere.
     */
    private TutorBean selectedTutor;

    /**
     * Metodo di inizializzazione per passare i dati tra le scene.
     * Viene chiamato dal Controller della schermata precedente (BookTutorView).
     *
     * @param tutor Il bean contenente i dati del tutor selezionato.
     */
    public void initData(TutorBean tutor) {
        // 1. Salva il tutor ricevuto nello stato interno della classe
        this.selectedTutor = tutor;

        // 2. Inizializza il Controller Applicativo (Lazy initialization)
        this.bookingCtrl = new BookingCtrl();

        // 3. Aggiorna l'interfaccia grafica
        // Esempio: Setta il testo della lblAmount con il prezzo orario del tutor
    }

    /**
     * Gestisce il click sul bottone "Paga e Conferma".
     *
     * @param event L'evento generato dal click (utile per cambiare scena).
     */
    @FXML
    void onPayClick(ActionEvent event) {
        if(!validateInput()){
            return;
        }

        boolean success = bookingCtrl.bookLesson(
                this.selectedTutor,
                txtCardNumber.getText(),
                txtExpiry.getText(),
                txtCvv.getText()
        );

        if (success) {
            System.out.println("VIEW: Transazione OK. Reindirizzamento...");
            // Qui dovresti chiamare un metodo per tornare alla Home o mostrare un alert
        } else {
            showError("Errore", "Pagamento rifiutato dal sistema.");
        }
    }

    /**
     * Gestisce il click sul bottone "Annulla" o "Indietro".
     */
    @FXML
    void onCancelClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/ispw/unilife/view/BookTutor.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    private boolean validateInput() {
        // Controllo se i campi sono vuoti o formattati male
        if (txtCardNumber.getText().isEmpty() || !txtCardNumber.getText().matches("\\d{16}")) {
            showError("Errore Dati", "Il numero carta deve essere di 16 cifre.");
            return false; // Questo false blocca l'utente
        }
        if (!txtExpiry.getText().matches("(0[1-9]|1[0-2])/[0-9]{2}")) {
            showError("Errore Dati", "Scadenza non valida (Usa MM/YY).");
            return false;
        }
        if (!txtCvv.getText().matches("\\d{3}")) {
            showError("Errore Dati", "Il CVV deve essere di 3 cifre.");
            return false;
        }
        return true; // Tutto ok, lasciamo passare
    }

    private void showError(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Attenzione");
        alert.setHeaderText(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}