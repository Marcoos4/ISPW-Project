package it.ispw.unilife.view;

import it.ispw.unilife.bean.UserBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigator {

    private static Navigator instance = null;
    private Stage primaryStage;

    private Navigator(){}

    public static synchronized Navigator getNavigatorInstance() {
        if (instance == null) {
            instance = new Navigator();
        }
        return instance;
    }

    public void goToHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/ispw/unilife/HomePage.fxml"));
            Parent root = loader.load();

            switchScene(root, "UniLife - Home");

        } catch (IOException e) {
            System.err.println("ERROR: Can't load HomePage.fxml");
        }
    }

    public void goToLogin(UserBean userBean) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/ispw/unilife/Login.fxml"));
            Parent root = loader.load();

            LoginView controller = loader.getController();
            controller.initLoginPage(userBean);

            double optimalWidth = 600.0;
            double optimalHeight = 600.0;

            this.primaryStage.setMinWidth(optimalWidth);
            this.primaryStage.setMinHeight(optimalHeight);

            boolean needsResize = false;
            if (this.primaryStage.getWidth() < optimalWidth) {
                this.primaryStage.setWidth(optimalWidth);
                needsResize = true;
            }
            if (this.primaryStage.getHeight() < optimalHeight) {
                this.primaryStage.setHeight(optimalHeight);
                needsResize = true;
            }
            if (needsResize)
                this.primaryStage.centerOnScreen();

            switchScene(root, "UniLife - Login");

        } catch (IOException e) {
            System.err.println("ERROR: Can't load Login.fxml");
            e.printStackTrace();
        }
    }

    public void goToRegistration(UserBean userBean) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/ispw/unilife/Registration.fxml"));
            Parent root = loader.load();

            RegistrationView controller = loader.getController();
            controller.initRegistrationPage(userBean);

            double optimalWidth = 600.0;
            double optimalHeight = 600.0;

            if (primaryStage.getWidth() < optimalWidth) primaryStage.setWidth(optimalWidth);
            if (primaryStage.getHeight() < optimalHeight) primaryStage.setHeight(optimalHeight);

            primaryStage.centerOnScreen();

            switchScene(root, "UniLife - Registrazione");

        } catch (IOException e) {
            System.err.println("ERROR: Can't load Registration.fxml");
            e.printStackTrace();
        }
    }
    
    public void setPrimaryStage(Stage stage) { this.primaryStage = stage; }
    public Stage getPrimaryStage() { return this.primaryStage; }

    private void switchScene(Parent root, String title) {
        if (this.primaryStage == null) {
            System.err.println("ERROR: Didn't correctly init the primary stage");
            return;
        }

        if (this.primaryStage.getScene() == null) {
            Scene scene = new Scene(root);
            this.primaryStage.setScene(scene);
        } else {
            this.primaryStage.getScene().setRoot(root);
        }

        this.primaryStage.setTitle(title);
        this.primaryStage.show();
    }
}
