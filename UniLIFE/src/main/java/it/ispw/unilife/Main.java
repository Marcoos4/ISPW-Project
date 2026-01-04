package it.ispw.unilife;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // 1. Definisci il percorso
        String fxmlPath = "/it/ispw/unilife/BookTutor.fxml";

        // 2. Prova a cercarlo e stampa il risultato
        var resource = getClass().getResource(fxmlPath);
        System.out.println("CERCO IL FILE QUI: " + fxmlPath);
        System.out.println("RISULTATO TROVATO: " + resource);

        // Se è null, fermati subito per non avere l'errore confuso dopo
        if (resource == null) {
            throw new RuntimeException("ERRORE FATALE: Il file FXML non è stato trovato! Controlla target/classes");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/ispw/unilife/BookTutor.fxml"));

        Parent root = fxmlLoader.load();
        scene = new Scene(root, 800, 600); // Imposta larghezza e altezza

        stage.setTitle("UniLife - Book Tutor Test");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}