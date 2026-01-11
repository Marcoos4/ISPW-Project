package it.ispw.unilife.view; // NOTA IL PACKAGE

import it.ispw.unilife.bean.UserBean;
import it.ispw.unilife.controller.LoginController; // Questo è l'unico vero controller
import it.ispw.unilife.exception.ExternalAuthenticationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.regex.Pattern;

public class LoginView {

    @FXML private TextField emailField;
    @FXML private TextField passwordField;

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
            showAlert("Campi mancanti", "Inserisci le tue credenziali per accedere.");
            return;
        }

        if (pwd.length() < 8) {
            showAlert("Password troppo corta", "La password deve contenere almeno 8 caratteri.");
            passwordField.clear();
            return;
        }

        if (userOrEmail.contains("@")) {
            if (!isValidEmail(userOrEmail)) {
                showAlert("Formato Email Errato", "L'indirizzo email inserito non è valido.");
                return;
            }
        } else {
            if (userOrEmail.length() < 8) {
                showAlert("Username non valido", "Se usi un username, deve essere di almeno 8 caratteri.");
                return;
            }
        }

        try {
            UserBean userBean = new UserBean();
            userBean.setUserName(userOrEmail);
            userBean.setPassword(pwd);
            userBean = this.loginController.login(userBean);

            HomePageView homePage = new HomePageView();
            homePage.displayHome(userBean, event);
        } catch (Exception e) {
            showAlert("Login Fallito", "Credenziali non valide. Riprova.");
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
        try {
            UserBean userBean = this.loginController.externalLogin("Google");
            HomePageView homePageView = new HomePageView();
            homePageView.displayHome(userBean, event);
        }catch (ExternalAuthenticationException e){
            showAlert("Login Fallito", "I server di Google non rispondono.\n Riprova o cambia modalità.");
        }
    }

    @FXML
    public void onGithubLoginClick(ActionEvent event) throws IOException {
        System.out.println("Login Google cliccato");
        try {
            UserBean userBean = this.loginController.externalLogin("GitHub");
            HomePageView homePageView = new HomePageView();
            homePageView.displayHome(userBean, event);
        }catch (ExternalAuthenticationException e){
            showAlert("Login Fallito", "I server di GitHub non rispondono.\n Riprova o cambia modalità.");
        }
    }

    @FXML
    public void onBackToHome(ActionEvent event)throws IOException{
        Navigator.getNavigatorInstance().goToHome(event);
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void initLoginPage(UserBean userBean) {
            if (userBean != null && userBean.getUserName() != null) emailField.setText(userBean.getUserName());
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return pattern.matcher(email).matches();
    }
}