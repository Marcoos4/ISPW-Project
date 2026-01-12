package it.ispw.unilife.view.decorator;

import it.ispw.unilife.view.Navigator;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GuestHomeDecorator extends ViewDecorator {

    public GuestHomeDecorator(ViewComponent view) {
        super(view);
    }

    @Override
    public Pane draw() {
        Pane p = super.draw();
        VBox menuBox = (VBox) p.lookup("#menuBox");

        // --- AZIONE PRINCIPALE: LOGIN ---
        Button btnLogin = new Button("Login / Register");
        btnLogin.getStyleClass().add("button-primary"); // Arancione
        // Passiamo null come userBean perché è un nuovo login
        btnLogin.setOnAction(e -> Navigator.getNavigatorInstance().goToLogin(e, null));

        // --- AZIONE SECONDARIA: EXPLORE ---
        Button btnExplore = new Button("Explore Courses");
        btnExplore.getStyleClass().add("button-secondary"); // Bianco
        btnExplore.setOnAction(e -> Navigator.getNavigatorInstance().goToExplorePage(e));

        menuBox.getChildren().addAll(btnLogin, btnExplore);
        return p;
    }
}