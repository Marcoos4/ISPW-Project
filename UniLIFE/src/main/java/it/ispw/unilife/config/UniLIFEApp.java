package it.ispw.unilife.config;

import it.ispw.unilife.view.Navigator;
import javafx.application.Application;
import javafx.stage.Stage;

public class UniLIFEApp extends Application {

    public void start(Stage primaryStage) {
        Navigator.getNavigatorInstance().setPrimaryStage(primaryStage);
        Navigator.getNavigatorInstance().goToHome();
    }
}
