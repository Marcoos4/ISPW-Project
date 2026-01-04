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
import it.ispw.unilife.controller.BookingCtrl;
import it.ispw.unilife.bean.TutorBean;

import java.util.List;

/**
 * Graphic Controller per la schermata di ricerca e prenotazione Tutor.
 * Questa classe gestisce esclusivamente la logica di presentazione (View),
 * delegando la logica di business al Controller Applicativo (BookingCtrl).
 */
public class BookTutorView {

    // Riferimenti agli elementi definiti nel file FXML
    @FXML
    private TextField searchField;

    @FXML
    private VBox tutorContainer; // Contenitore scrollabile dove verranno inserite le card

    // Riferimento al Controller Applicativo
    private BookingCtrl bookingCtrl;

    /**
     * Metodo di inizializzazione chiamato automaticamente da JavaFX dopo il caricamento dell'FXML.
     * Qui instanziamo il controller applicativo e richiediamo i dati iniziali.
     */
    @FXML
    public void initialize() {
        // 1. Istanziazione del Controller Applicativo
        this.bookingCtrl = new BookingCtrl();

        // 2. Recupero della lista iniziale (Startup: mostra tutti i tutor ordinati per rating)
        // Passiamo una stringa vuota per indicare "nessun filtro di ricerca".
        List<TutorBean> initialList = bookingCtrl.getAvailableTutors("");

        // 3. Popolamento dinamico dell'interfaccia
        updateTutorContainer(initialList);
    }

    /**
     * Gestore dell'evento di ricerca (click sulla lente o Invio).
     * @param event L'evento generato dall'azione utente.
     */
    @FXML
    void onSearch(ActionEvent event) {
        String query = searchField.getText();

        // Delega al Controller Applicativo il recupero dei dati filtrati
        List<TutorBean> filteredTutors = bookingCtrl.getAvailableTutors(query);

        // Aggiorna la vista con i nuovi risultati
        updateTutorContainer(filteredTutors);
    }

    /**
     * Metodo privato per aggiornare la lista visualizzata nel VBox.
     * Pulisce i vecchi risultati e genera le nuove "card" grafiche.
     * @param tutors La lista di Bean da visualizzare.
     */
    private void updateTutorContainer(List<TutorBean> tutors) {
        // Step 1: Pulizia del contenitore dai risultati precedenti
        tutorContainer.getChildren().clear();

        if (tutors.isEmpty()) {
            // Gestione caso lista vuota: Feedback all'utente
            Label noResultsLabel = new Label("Nessun tutor trovato con questi criteri.");
            noResultsLabel.setStyle("-fx-text-fill: gray; -fx-padding: 10;");
            tutorContainer.getChildren().add(noResultsLabel);
            return;
        }

        // Step 2: Creazione dinamica delle righe (Pattern: Programmatic UI)
        for (TutorBean tutor : tutors) {
            HBox card = createTutorCard(tutor);
            tutorContainer.getChildren().add(card);
        }
    }

    /**
     * Factory method per creare l'elemento grafico (Card) del singolo Tutor.
     * Converte i dati del Bean in nodi JavaFX (Label, Button, Layout).
     * * @param tutor Il bean contenente i dati.
     * @return HBox configurato pronto per essere aggiunto alla scena.
     */
    private HBox createTutorCard(TutorBean tutor) {
        // --- 1. CONFIGURAZIONE LAYOUT RIGA (HBox) ---
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setSpacing(20); // Spazio orizzontale tra gli elementi
        // Stile "Card": sfondo bianco, padding interno, bordo arrotondato e ombra leggera
        row.setStyle("-fx-background-color: white; -fx-padding: 30; " +
                "-fx-background-radius: 10; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");

        // --- 2. COLONNA INFORMAZIONI (VBox) ---
        VBox infoBox = new VBox(5); // 5px spazio verticale tra nome e materie

        Label nameLabel = new Label(tutor.getName() + " " + tutor.getSurname());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #333;");

        // Uniamo la lista delle materie in una stringa separata da virgole
        String subjectsStr = String.join(", ", tutor.getSubjects());
        Label subjectLabel = new Label(subjectsStr);
        subjectLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 12px;");

        infoBox.getChildren().addAll(nameLabel, subjectLabel);

        // --- 3. SPAZIATORE ELASTICO ---
        // Questo elemento invisibile spinge tutto ciò che segue (stelle e bottone) a destra
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // --- 4. VISUALIZZAZIONE RATING (Stelle) ---
        Label starLabel = new Label(getStarsString(tutor.getRating()));
        // Colore Oro (#FFD700) per le stelle
        starLabel.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 18px; -fx-font-weight: bold;");

        // --- 5. BOTTONE DI AZIONE ---
        Button bookBtn = new Button("Prenota");
        // Stile del bottone: Arancione, testo bianco, cursore mano, arrotondato
        bookBtn.setStyle("-fx-background-color: #ff9900; -fx-text-fill: white; " +
                "-fx-background-radius: 20; -fx-cursor: hand; -fx-font-weight: bold;");

        // Collega l'evento click al metodo di gestione
        bookBtn.setOnAction(e -> handleBookingAction(tutor));

        // --- 6. ASSEMBLAGGIO FINALE ---
        row.getChildren().addAll(infoBox, spacer, starLabel, bookBtn);

        return row;
    }

    /**
     * Metodo Helper per generare la stringa grafica delle stelle.
     * Esempio: input 3 -> output "★★★☆☆"
     */
    private String getStarsString(int rating) {
        StringBuilder stars = new StringBuilder();
        // Stelle piene
        for (int i = 0; i < rating; i++) {
            stars.append("★"); // Carattere Unicode Black Star
        }
        // Stelle vuote (fino a 5)
        for (int i = rating; i < 5; i++) {
            stars.append("☆"); // Carattere Unicode White Star
        }
        return stars.toString();
    }

    /**
     * Gestisce l'intenzione dell'utente di prenotare uno specifico tutor.
     * Delega l'operazione al controller applicativo.
     */
    private void handleBookingAction(TutorBean tutor) {
        // Logica di navigazione o conferma
        // TODO: In futuro qui si aprirà la ReservationView
        bookingCtrl.processSelection(tutor);
    }
}