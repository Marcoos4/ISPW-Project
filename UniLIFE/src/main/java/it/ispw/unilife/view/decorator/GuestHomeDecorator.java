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
        // 1. Recupera la vista base (scheletro)
        Pane p = super.draw();

        // 2. Trova il contenitore dei bottoni
        VBox menuBox = (VBox) p.lookup("#menuBox");

        // 3. Crea il pulsante Login (specifico per Guest)
        Button btnLogin = new Button("Login / Register");
        btnLogin.getStyleClass().add("home-button");
        btnLogin.setOnAction(e ->
            Navigator.getNavigatorInstance().goToLogin(e, null));

        // 4. Crea il pulsante Explore (comune a Guest e Student)
        Button btnExplore = new Button("Explore Courses");
        btnExplore.getStyleClass().add("home-button");
        btnExplore.setOnAction(e -> Navigator.getNavigatorInstance().goToExplorePage(e));

        // 5. Aggiungi i bottoni al layout
        menuBox.getChildren().addAll(btnLogin, btnExplore);

        return p;
    }
}