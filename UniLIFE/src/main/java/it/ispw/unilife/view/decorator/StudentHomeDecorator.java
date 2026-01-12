package it.ispw.unilife.view.decorator;

import it.ispw.unilife.view.Navigator;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class StudentHomeDecorator extends ViewDecorator {

    public StudentHomeDecorator(ViewComponent view) {
        super(view);
    }

    @Override
    public Pane draw() {
        Pane p = super.draw();
        VBox menuBox = (VBox) p.lookup("#menuBox");

        // --- AZIONE PRINCIPALE: PRENOTA TUTOR ---
        Button btnBook = new Button("Book a Lesson");
        btnBook.getStyleClass().add("button-primary"); // Arancione
        btnBook.setOnAction(e -> {
            try {
                Navigator.getNavigatorInstance().goToTutor(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // --- AZIONE SECONDARIA: LE MIE PRENOTAZIONI ---
        Button btnMyReservations = new Button("My Reservations");
        btnMyReservations.getStyleClass().add("button-secondary"); // Bianco
        btnMyReservations.setOnAction(e -> {
            try {
                Navigator.getNavigatorInstance().goToStudentReservation(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // --- AZIONE TERZIARIA: EXPLORE ---
        Button btnExplore = new Button("Explore Courses");
        btnExplore.getStyleClass().add("button-secondary"); // Bianco
        btnExplore.setOnAction(e -> Navigator.getNavigatorInstance().goToExplorePage(e));

        menuBox.getChildren().addAll(btnBook, btnMyReservations, btnExplore);
        return p;
    }
}