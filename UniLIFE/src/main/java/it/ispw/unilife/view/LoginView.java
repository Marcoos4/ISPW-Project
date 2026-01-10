package it.ispw.unilife.view; // NOTA IL PACKAGE

import it.ispw.unilife.bean.UserBean;
import it.ispw.unilife.controller.LoginController; // Questo è l'unico vero controller
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.regex.Pattern;

public class LoginView {
    @FXML private Hyperlink registrationLink;

    @FXML private TextField emailField;
    @FXML private TextField passwordField;

    // Riferimento al Controller Applicativo
    // La View "conosce" il Controller per passargli i comandi
    private LoginController loginController;

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    @FXML
    public void initialize() {
        this.loginController = new LoginController();
    }

    @FXML
    void onLoginClick(ActionEvent event) {
        String userOrEmail = emailField.getText();
        String pwd = passwordField.getText();

        if (userOrEmail.isEmpty() || pwd.isEmpty()) {
            mostraErrore("Campi mancanti", "Inserisci le tue credenziali per accedere.");
            return;
        }

        if (pwd.length() < 8) {
            mostraErrore("Password troppo corta", "La password deve contenere almeno 8 caratteri.");
            passwordField.clear();
            return;
        }

        if (userOrEmail.contains("@")) {
            if (!isValidEmail(userOrEmail)) {
                mostraErrore("Formato Email Errato", "L'indirizzo email inserito non è valido.");
                return;
            }
        } else {
            if (userOrEmail.length() < 8) {
                mostraErrore("Username non valido", "Se usi un username, deve essere di almeno 8 caratteri.");
                return;
            }
        }

        try {
            UserBean userBean = new UserBean();
            userBean.setUserName(userOrEmail);
            userBean.setPassword(pwd);
            this.loginController.login(userBean);
        } catch (Exception e) {
            mostraErrore("Login Fallito", "Credenziali non valide. Riprova.");
        }
    }

    @FXML
    public void onGoToRegistration(ActionEvent event) throws IOException {
        UserBean userBean = new UserBean();
        if (emailField != null) userBean.setUserName(emailField.getText());
        if (passwordField!= null) userBean.setPassword(passwordField.getText());
        Navigator.getNavigatorInstance().goToRegistration(event, userBean);
    }

    @FXML
    public void onGoogleLoginClick(ActionEvent event) throws IOException{
        System.out.println("Login Google cliccato");
        this.loginController.externalLogin(event, "Google");
    }

    @FXML
    public void onGithubLoginClick(ActionEvent event) throws IOException {
        System.out.println("Login Github cliccato");
        loginController.externalLogin(event, "GitHub");
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void initLoginPage(UserBean userBean) {
        if (userBean != null) {
            if (userBean.getUserName() != null) emailField.setText(userBean.getUserName());
        }
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return pattern.matcher(email).matches();
    }

    private void mostraErrore(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore di Accesso");
        alert.setHeaderText(titolo);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}