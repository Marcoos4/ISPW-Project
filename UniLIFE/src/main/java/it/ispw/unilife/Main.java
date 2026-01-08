package it.ispw.unilife;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    // Definisco la scena iniziale
    private static final String LOGIN_FXML = "/view/Login.fxml";

    @Override
    public void start(Stage stage) throws IOException {
        // 1. Carica il file FXML
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
        stage.setResizable(false); // Opzionale: blocca il ridimensionamento
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}