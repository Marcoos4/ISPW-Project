package it.ispw.unilife.view;

import it.ispw.unilife.bean.CourseBean;
import it.ispw.unilife.bean.UserBean;
import it.ispw.unilife.bean.ReservationBean;
import it.ispw.unilife.bean.TutorBean;
import it.ispw.unilife.controller.SessionController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigator {

    private static Navigator instance = null;

    private Navigator(){}

    public static synchronized Navigator getNavigatorInstance() {
        if (instance == null) {
            instance = new Navigator();
        }
        return instance;
    }

    public void goToHome(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/ispw/unilife/BaseHome.fxml"));
            Parent root = loader.load();
            HomePageView homePageView = loader.getController();
            SessionController sessionController = new SessionController();
            homePageView.displayHome(sessionController.checkPermission(), event);

        } catch (IOException e) {
            System.err.println("ERROR: Can't load BaseHome.fxml");
        }
    }

    public void goToLogin(ActionEvent event, UserBean userBean) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/ispw/unilife/Login.fxml"));
            Parent root = loader.load();

            LoginView controller = loader.getController();
            controller.initLoginPage(userBean);

            Node source = (Node) event.getSource();
            resizeScene(source, 600, 600);
            switchScene((Stage) source.getScene().getWindow(), root, "UniLife - Login");

        } catch (IOException e) {
            System.err.println("ERROR: Can't load Login.fxml");
            e.printStackTrace();
        }
    }

    public void goToRegistration(ActionEvent event, UserBean userBean) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/ispw/unilife/Registration.fxml"));
            Parent root = loader.load();

            RegistrationView controller = loader.getController();
            controller.initRegistrationPage(userBean);

            Node source = (Node) event.getSource();
            resizeScene(source, 600, 600);
            switchScene((Stage) source.getScene().getWindow(), root, "UniLife - Registration");

        } catch (IOException e) {
            System.err.println("ERROR: Can't load Registration.fxml");
            e.printStackTrace();
        }
    }

    public void goToExplorePage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/ispw/unilife/ExplorePage.fxml"));
            Parent root = loader.load();

            Node source = (Node) event.getSource();
            resizeScene(source, 900, 600);
            switchScene((Stage) source.getScene().getWindow(), root, "UniLife - Explore Courses");

        } catch (IOException e) {
            System.err.println("ERROR: Can't load ExplorePage.fxml");
            e.printStackTrace();
        }
    }

    public void goToCourseDetails(CourseBean courseBean){
        return;
    }


    public void goToPayment(ActionEvent event, ReservationBean reservationBean) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/ispw/unilife/PaymentPage.fxml"));
        Parent root = loader.load();
        PaymentView paymentView = loader.getController();
        paymentView.initData(reservationBean);
        Node source = (Node) event.getSource();
        switchScene((Stage) source.getScene().getWindow(), root, "UniLife - Payment");

    }

    public void goToTutor(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ReservationView.class.getResource("/it/ispw/unilife/BookTutor.fxml"));
        Parent root = loader.load();
        Node source = (Node) event.getSource();
        switchScene((Stage) source.getScene().getWindow(), root, "UniLife - Registration");
    }

    public void goToReservation(ActionEvent event, TutorBean tutor) throws IOException {
        FXMLLoader loader = new FXMLLoader(ReservationView.class.getResource("/it/ispw/unilife/ReservationPage.fxml"));
        Parent root = loader.load();

        ReservationView controller = loader.getController();
        controller.initReservationPage(tutor);

        Node source = (Node) event.getSource();
        switchScene((Stage) source.getScene().getWindow(), root, "UniLife - Reservation");
    }

    public void goToStudentReservation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(StudentReservationView.class.getResource("/it/ispw/unilife/StudentReservationPage.fxml"));
        Parent root = loader.load();

        Node source = (Node) event.getSource();
        switchScene((Stage) source.getScene().getWindow(), root, "UniLife - Student Reservation");
    }

    public void show(Stage stage, Pane root) {
        if (stage.getScene() == null) {
            stage.setScene(new Scene(root));
            resizeScene(root, 600, 600);
        } else {
            stage.getScene().setRoot(root);
        }
        stage.show();
    }

    public void show(ActionEvent event, Pane root) {
        Node source = (Node) event.getSource();
        resizeScene(source, 600, 600);
        Stage stage = (Stage) source.getScene().getWindow();

        this.show(stage, root);
    }


    private void resizeScene(Node source, double optimalWidth, double optimalHeight) {
        Stage stage = (Stage) source.getScene().getWindow();

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
    }

    private void switchScene(Stage stage, Parent root, String title){
        stage.setTitle(title);
        stage.getScene().setRoot(root);

    }


    public void goToPendingReservation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ReservationView.class.getResource("/it/ispw/unilife/PendingReservationPage.fxml"));
        Parent root = loader.load();

        PendingReservationsView controller = loader.getController();

        Node source = (Node) event.getSource();
        switchScene((Stage) source.getScene().getWindow(), root, "UniLife - Reservation");
    }
}
