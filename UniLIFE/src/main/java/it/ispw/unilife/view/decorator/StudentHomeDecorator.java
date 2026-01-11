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
        // 1. Ottengo la vista base (lo scheletro)
        Pane p = super.draw();

        // 2. Cerco il contenitore dove mettere i bottoni (definito nell'FXML con fx:id="menuBox")
        VBox menuBox = (VBox) p.lookup("#menuBox");

        // 3. Creo i bottoni per lo studente
        Button btnExplore = createButton("Explore Courses");
        btnExplore.setOnAction(e -> Navigator.getNavigatorInstance().goToExplorePage(e));

        Button btnBook = createButton("Book Lesson");
        btnBook.setOnAction(e -> {
            try {
                Navigator.getNavigatorInstance().goToTutor(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // 4. Aggiungo i bottoni allo scheletro
        menuBox.getChildren().addAll(btnExplore, btnBook);

        return p;
    }

    // Helper per lo stile
    private Button createButton(String text) {
        Button b = new Button(text);
        b.getStyleClass().add("home-button"); // Classe CSS
        return b;
    }
}