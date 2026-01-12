package it.ispw.unilife.view.decorator;

import it.ispw.unilife.view.Navigator;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class TutorHomeDecorator extends ViewDecorator {

    public TutorHomeDecorator(ViewComponent view) {
        super(view);
    }

    @Override
    public Pane draw() {
        Pane p = super.draw();
        VBox menuBox = (VBox) p.lookup("#menuBox");

        // --- AZIONE PRINCIPALE: GESTISCI RICHIESTE ---
        Button btnRequests = new Button("Pending Requests");
        btnRequests.getStyleClass().add("button-primary"); // Arancione
        btnRequests.setOnAction(e -> {
            try {
                Navigator.getNavigatorInstance().goToPendingReservation(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // --- AZIONE SECONDARIA: AGENDA (Placeholder) ---
        Button btnAgenda = new Button("My Agenda");
        btnAgenda.getStyleClass().add("button-secondary"); // Bianco
        // Se hai una pagina agenda, collegala qui, altrimenti lascia vuoto o togli
        // btnAgenda.setOnAction(e -> Navigator.getNavigatorInstance().goToAgenda(e));

        menuBox.getChildren().addAll(btnRequests, btnAgenda);
        return p;
    }
}