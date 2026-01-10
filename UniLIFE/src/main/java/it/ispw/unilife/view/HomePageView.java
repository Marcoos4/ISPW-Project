package it.ispw.unilife.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class HomePageView {

    @FXML
    void goToLogin(ActionEvent event) {
        Navigator.getNavigatorInstance().goToLogin(null);
    }

    @FXML
    void goToSearch(ActionEvent event) {
        Navigator.getNavigatorInstance().goToHome();
    }
}
