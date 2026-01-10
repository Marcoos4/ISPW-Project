package it.ispw.unilife;

import it.ispw.unilife.config.Configuration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    // Definisco la scena iniziale
    private static final String LOGIN_FXML = "/it/ispw/unilife/Login.fxml";

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(LOGIN_FXML));

        // Controllo di sicurezza: se il percorso è sbagliato, avvisa subito
        if (fxmlLoader.getLocation() == null) {
            throw new IOException("ERRORE CRITICO: Impossibile trovare il file FXML in: " + LOGIN_FXML);
        }

        Parent root = fxmlLoader.load();

        // 2. Crea la Scena
        Scene scene = new Scene(root);

        // 3. Configura lo Stage (la finestra)
        stage.setTitle("UniLife - Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try {
            Configuration.getInstance().loadConfiguration(args);
        } catch (IllegalStateException e) {
            System.err.println("Tried to reload App config: " + e.getMessage());
            return;
        }
        launch();
    }
}