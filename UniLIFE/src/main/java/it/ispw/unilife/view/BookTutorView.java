package it.ispw.unilife.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import it.ispw.unilife.controller.BookingCtrl;
import it.ispw.unilife.bean.TutorBean;
import java.util.List;

/**
 * Graphic Controller associato alla vista FXML per la ricerca e prenotazione dei Tutor.
 * <p>
 * Questa classe agisce come Boundary nel pattern BCE, gestendo l'interazione con l'utente
 * e delegando l'esecuzione della logica di business al {@link BookingCtrl}.
 * </p>
 */
public class BookTutorView {

    @FXML
    private TextField searchField;

    @FXML
    private VBox tutorContainer;

    /**
     * Riferimento al Controller Applicativo per la gestione del caso d'uso "Book Tutor".
     */
    private BookingCtrl bookingCtrl;

    /**
     * Inizializza il controller dopo che l'elemento radice è stato processato.
     * Metodo invocato automaticamente dal loader FXML.
     */
    @FXML
    public void initialize() {
        // Istanziazione del controller applicativo all'avvio della vista
        this.bookingCtrl = new BookingCtrl();
    }

    /**
     * Gestisce l'evento di richiesta ricerca tutor.
     * Recupera la query di ricerca e invoca il controller applicativo per ottenere i risultati.
     *
     * @param event L'evento generato dall'interazione utente (es. click su bottone o invio).
     */
    @FXML
    void onSearch(ActionEvent event) {
        String query = searchField.getText();

        // Delega al controller applicativo il recupero della lista tutor
        List<TutorBean> tutors = bookingCtrl.getAvailableTutors(query);

        // Aggiornamento della vista con i dati ricevuti
        updateTutorContainer(tutors);
    }

    /**
     * Aggiorna il contenitore grafico VBox popolandolo con le card dei tutor trovati.
     *
     * @param tutors Lista di {@link TutorBean} recuperati dalla logica di business.
     */
    private void updateTutorContainer(List<TutorBean> tutors) {
        // Pulizia dei risultati precedenti
        tutorContainer.getChildren().clear();

        if (tutors == null || tutors.isEmpty()) {
            tutorContainer.getChildren().add(new Label("Nessun tutor trovato corrispondente ai criteri."));
            return;
        }

        // Generazione dinamica degli elementi della lista
        for (TutorBean tutor : tutors) {
            HBox row = createTutorRow(tutor);
            tutorContainer.getChildren().add(row);
        }
    }

    /**
     * Crea un componente grafico (HBox) rappresentante una singola riga tutor.
     *
     * @param tutor Il bean contenente i dati del tutor.
     * @return L'oggetto HBox configurato con le label e il pulsante di azione.
     */
    private HBox createTutorRow(TutorBean tutor) {
        HBox row = new HBox(10); // Spaziatura orizzontale di 10px

        Label nameLabel = new Label(tutor.getName() + " " + tutor.getSurname());
        Label subjectLabel = new Label(" - " + tutor.getSubjects());
        Button bookButton = new Button("Prenota");

        // Binding dell'azione di prenotazione
        bookButton.setOnAction(e -> handleBookingAction(tutor));

        row.getChildren().addAll(nameLabel, subjectLabel, bookButton);
        return row;
    }

    /**
     * Gestisce l'azione di avvio prenotazione per uno specifico tutor.
     *
     * @param tutor Il tutor selezionato.
     */
    private void handleBookingAction(TutorBean tutor) {
        // Invocazione asincrona della logica di selezione nel controller
        bookingCtrl.processSelection(tutor);
    }
}