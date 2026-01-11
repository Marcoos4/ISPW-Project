package it.ispw.unilife.view;

import it.ispw.unilife.bean.UserBean;
import it.ispw.unilife.controller.LoginController;
import it.ispw.unilife.model.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.regex.Pattern;

public class RegistrationView {

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Button registerButton;

    @FXML
    private Hyperlink loginLink;

    private LoginController loginController;

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    @FXML
    public void onRegistrationClick(ActionEvent event) {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String userOrEmail = emailField.getText();
        String pwd = passwordField.getText();
        String role = roleComboBox.getValue();

        if (name.isEmpty() || surname.isEmpty() || userOrEmail.isEmpty() || pwd.isEmpty() || role == null) {
            mostraErrore("Campi mancanti", "Per favore, compila tutti i campi del modulo.");
            return;
        }

        if (pwd.length() < 8) {
            mostraErrore("Password debole", "La password deve contenere almeno 8 caratteri.");
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
                mostraErrore("Username troppo corto", "Se usi un username, deve essere di almeno 8 caratteri.");
                return;
            }
        }

        try {
            UserBean userBean = new UserBean();
            userBean.setUserName(userOrEmail);
            userBean.setRole(Role.stringToRole(role));
            userBean.setSurname(surname);
            userBean.setName(name);
            userBean.setPassword(pwd);

            loginController.register(event, userBean);

        } catch (Exception e) {
            mostraErrore("Errore di Registrazione", e.getMessage());
        }
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return pattern.matcher(email).matches();
    }

    private void mostraErrore(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore di Validazione");
        alert.setHeaderText(titolo);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    @FXML
    public void onGoToLogin(ActionEvent event) throws IOException {
        UserBean userBean = new UserBean();
        if (emailField != null) userBean.setUserName(emailField.getText());
        if (passwordField!= null) userBean.setPassword(passwordField.getText());
        Navigator.getNavigatorInstance().goToLogin(event, userBean);
    }

    public void initRegistrationPage(UserBean userBean) {
        if (userBean != null) {
            if (userBean.getName() != null) nameField.setText(userBean.getName());
            if (userBean.getSurname() != null) surnameField.setText(userBean.getSurname());
            if (userBean.getUserName() != null) emailField.setText(userBean.getUserName());
        }
    }

    @FXML
    public void initialize() {
        this.loginController = new LoginController();
    }
}
