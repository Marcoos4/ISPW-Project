package it.ispw.unilife.view;

import it.ispw.unilife.bean.UserBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigator {
    private static Navigator instance = null;

    private Navigator(){}

    public static Navigator getNavigatorInstance(){
        if (instance == null)
            instance = new Navigator();
        return instance;
    }

    /*public void goToPayment(ActionEvent event, ReservationBean reservationBean) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/ispw/unilife/PaymentPage.fxml"));
        Parent root = loader.load();
        PaymentView paymentView = loader.getController();
        paymentView.initData(reservationBean);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);

    }

    public void goToTutor(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ReservationView.class.getResource("/it/ispw/unilife/BookTutor.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    public void goToReservation(ActionEvent event, TutorBean tutor) throws IOException {
        FXMLLoader loader = new FXMLLoader(ReservationView.class.getResource("/it/ispw/unilife/ReservationPage.fxml"));
        Parent root = loader.load();

        ReservationView controller = loader.getController();
        controller.initReservationPage(tutor);

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.getScene().setRoot(root);
    }*/

    public void goToRegistration(ActionEvent event, UserBean userBean) throws IOException {
        FXMLLoader loader = new FXMLLoader(RegistrationView.class.getResource("/it/ispw/unilife/Registration.fxml"));
        Parent root = loader.load();

        RegistrationView controller = loader.getController();
        controller.initRegistrationPage(userBean);

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        double optimalWidth = 600.0;
        double optimalHeight = 600.0;

        stage.setMinWidth(optimalWidth);
        stage.setMinHeight(optimalHeight);

        boolean needsResize = false;

        if (stage.getWidth() < optimalWidth) {
            stage.setWidth(optimalWidth);
            needsResize = true;
        }

        if (stage.getHeight() < optimalHeight) {
            stage.setHeight(optimalHeight);
            needsResize = true;
        }

        if (needsResize) {
            stage.centerOnScreen();
        }

        stage.getScene().setRoot(root);
    }

    public void goToLogin(ActionEvent event, UserBean userBean) throws IOException{
        FXMLLoader loader = new FXMLLoader(LoginView.class.getResource("/it/ispw/unilife/Login.fxml"));
        Parent root = loader.load();

        LoginView controller = loader.getController();
        controller.initLoginPage(userBean);

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        double optimalWidth = 600.0;
        double optimalHeight = 600.0;

        stage.setMinWidth(optimalWidth);
        stage.setMinHeight(optimalHeight);

        boolean needsResize = false;

        if (stage.getWidth() < optimalWidth) {
            stage.setWidth(optimalWidth);
            needsResize = true;
        }

        if (stage.getHeight() < optimalHeight) {
            stage.setHeight(optimalHeight);
            needsResize = true;
        }

        if (needsResize) {
            stage.centerOnScreen();
        }

        stage.getScene().setRoot(root);
    }
}
