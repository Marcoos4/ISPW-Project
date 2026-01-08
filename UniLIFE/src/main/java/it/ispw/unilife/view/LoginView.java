package it.ispw.unilife.view; // NOTA IL PACKAGE

import com.google.api.client.auth.oauth2.Credential;
import it.ispw.unilife.bean.UserBean;
import it.ispw.unilife.boundary.GoogleAuthBoundary;
import it.ispw.unilife.controller.LoginController; // Questo è l'unico vero controller
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class LoginView { // NOME CORRETTO

    @FXML private TextField emailField;
    @FXML private TextField passwordField;

    // Riferimento al Controller Applicativo
    // La View "conosce" il Controller per passargli i comandi
    private final LoginController loginController = new LoginController();

    @FXML
    public void onLoginClick() {
        String email = emailField.getText();
        String pwd = passwordField.getText();

        if(email.isEmpty() || pwd.isEmpty()) {
            showAlert("Errore", "Compila tutti i campi!");
            return;
        }

        try {
            // La View DELEGA al Controller
            // UserBean user = loginController.login(email, pwd);
        } catch (Exception e) {
            showAlert("Errore Login", e.getMessage());
        }
    }

    @FXML
    public void onGoogleLoginClick() {
        try {
            // L'Adapter è un componente tecnico, può essere chiamato dalla View
            // per ottenere i dati grezzi, che poi passiamo al Controller.
            LoginController controller = new LoginController();
            controller.externalLogin(new UserBean(), "Google");

            // Passiamo il bean al controller applicativo per la logica di business
            // loginController.completeExternalLogin(googleUser);

            //showAlert("Successo", "Benvenuto " + googleUser.getName());

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Errore Google", "Impossibile accedere: " + e.getMessage());
        }
    }

    @FXML
    public void onAppleLoginClick() {
        System.out.println("Login Apple cliccato");
    }

    @FXML
    public void goToRegistration() {
        // Logica di navigazione
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}