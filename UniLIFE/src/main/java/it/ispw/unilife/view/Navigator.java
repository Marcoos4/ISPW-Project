package it.ispw.unilife.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigator {

    private static Navigator instance = null;
    private Stage primaryStage;

    private Navigator(){}

    public static synchronized Navigator getInstance() {
        if (instance == null) {
            instance = new Navigator();
        }
        return instance;
    }

    public void goToHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomePage.fxml"));
            Parent root = loader.load();

            if (primaryStage.getScene() == null) {
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
            } else {
                primaryStage.getScene().setRoot(root);
            }

            primaryStage.setTitle("UniLife - Home");
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Errore nel caricamento della Home");
            e.printStackTrace();
        }
    }

    public void goToLogin() {
    }

    public void setPrimaryStage(Stage stage) { this.primaryStage = stage; }
    public Stage getPrimaryStage() { return this.primaryStage; }

}
