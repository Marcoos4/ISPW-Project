package it.ispw.unilife.config;

import it.ispw.unilife.bean.UserBean;
import it.ispw.unilife.view.HomePageView;
import it.ispw.unilife.view.Navigator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UniLIFEApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 1. Configurazioni iniziali (Titolo, Icona, ecc.)
        primaryStage.setTitle("UniLIFE");

        HomePageView homePage = new HomePageView();
        homePage.displayHome(null, primaryStage);
    }
}
