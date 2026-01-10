package it.ispw.unilife.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class HomePageView {

    @FXML
    void goToLogin(ActionEvent event) {
        // Deleghiamo la navigazione al Singleton
        // Passiamo 'null' perché stiamo andando al login da zero (nessun dato utente da precaricare)
        Navigator.getNavigatorInstance().goToLogin(null);
    }

    @FXML
    void goToSearch(ActionEvent event) {
        // Nel tuo codice precedente "Search" portava alla HomePage
        Navigator.getNavigatorInstance().goToHome();
    }
}
