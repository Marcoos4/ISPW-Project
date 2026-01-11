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

        // Il Tutor NON ha Explore, ha Agenda
        Button btnAgenda = new Button("Manage Agenda");
        btnAgenda.getStyleClass().add("home-button");
        btnAgenda.setOnAction(e -> {
            try {
                Navigator.getNavigatorInstance().goToPendingReservation(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        menuBox.getChildren().add(btnAgenda);
        return p;
    }
}