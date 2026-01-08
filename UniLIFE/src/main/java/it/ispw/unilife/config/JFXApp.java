package it.ispw.unilife.config;

import it.ispw.unilife.view.Navigator;
import javafx.application.Application;
import javafx.stage.Stage;

public class JFXApp extends Application {

    public void start(Stage primaryStage) {
        Navigator.getInstance().setPrimaryStage(primaryStage);
        Navigator.getInstance().goToHome();

    }
}
