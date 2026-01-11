package it.ispw.unilife.view;

import it.ispw.unilife.bean.CourseBean;
import it.ispw.unilife.bean.UserBean;
import it.ispw.unilife.bean.ReservationBean;
import it.ispw.unilife.bean.TutorBean;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
    public void goToPayment(ActionEvent event, ReservationBean reservationBean) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/ispw/unilife/PaymentPage.fxml"));
        Parent root = loader.load();
        PaymentView paymentView = loader.getController();
        paymentView.initData(reservationBean);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);

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
    public void goToTutor(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ReservationView.class.getResource("/it/ispw/unilife/BookTutor.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }

        } catch (IOException e) {
            System.err.println("ERROR: Can't load Login.fxml");
            e.printStackTrace();
        }
    }

    public void goToRegistration(UserBean userBean) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/ispw/unilife/Registration.fxml"));
            Parent root = loader.load();
    public void goToReservation(ActionEvent event, TutorBean tutor) throws IOException {
        FXMLLoader loader = new FXMLLoader(ReservationView.class.getResource("/it/ispw/unilife/ReservationPage.fxml"));
        Parent root = loader.load();

            RegistrationView controller = loader.getController();
            controller.initRegistrationPage(userBean);

            double optimalWidth = 600.0;
            double optimalHeight = 600.0;
        ReservationView controller = loader.getController();
        controller.initReservationPage(tutor);

            if (primaryStage.getWidth() < optimalWidth) primaryStage.setWidth(optimalWidth);
            if (primaryStage.getHeight() < optimalHeight) primaryStage.setHeight(optimalHeight);

            primaryStage.centerOnScreen();

            switchScene(root, "UniLife - Registrazione");

        } catch (IOException e) {
            System.err.println("ERROR: Can't load Registration.fxml");
            e.printStackTrace();
        }
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    public void goToExplorePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/ispw/unilife/ExplorePage.fxml"));
            Parent root = loader.load();
    public void goToStudentReservation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(StudentReservationView.class.getResource("/it/ispw/unilife/StudentReservationPage.fxml"));
        Parent root = loader.load();

            double optimalWidth = 900.0;
            double optimalHeight = 600.0;

            boolean needsResize = false;

            if (this.primaryStage.getWidth() < optimalWidth) {
                this.primaryStage.setWidth(optimalWidth);
                needsResize = true;
            }
            if (this.primaryStage.getHeight() < optimalHeight) {
                this.primaryStage.setHeight(optimalHeight);
                needsResize = true;
            }
            if (needsResize) {
                this.primaryStage.centerOnScreen();
            }

            switchScene(root, "UniLife - Esplora Corsi");

        } catch (IOException e) {
            System.err.println("ERROR: Can't load ExplorePage.fxml");
            e.printStackTrace();
        }
    }

    public void goToCourseDetails(CourseBean courseBean){
        return;
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
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.getScene().setRoot(root);
    }
}
