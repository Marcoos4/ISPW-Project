package it.ispw.unilife.view;

import it.ispw.unilife.bean.ReservationBean;
import it.ispw.unilife.bean.TutorBean;
import javafx.event.ActionEvent;
import javafx.event.Event;
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

    public void goToPayment(ActionEvent event, ReservationBean reservationBean) throws IOException {
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
    }

    public void goToStudentReservation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(StudentReservationView.class.getResource("/it/ispw/unilife/StudentReservationPage.fxml"));
        Parent root = loader.load();

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.getScene().setRoot(root);
    }
}
