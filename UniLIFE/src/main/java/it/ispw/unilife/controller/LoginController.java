package it.ispw.unilife.controller;

import it.ispw.unilife.bean.UserBean;
import it.ispw.unilife.boundary.*;
import it.ispw.unilife.dao.DAOFactory;
import it.ispw.unilife.dao.UserDAO;
import it.ispw.unilife.exception.LoginException;
import it.ispw.unilife.exception.RegistrationException;
import it.ispw.unilife.exception.UserNotFoundException;
import it.ispw.unilife.model.User;
import it.ispw.unilife.model.session.SessionManager;
import it.ispw.unilife.view.Navigator;
import javafx.event.ActionEvent;

import java.io.IOException;

public class LoginController {

    public void login(UserBean userBean) throws LoginException {
        try {
            String username = userBean.getUserName();
            String password = userBean.getPassword();

            UserDAO userDao = DAOFactory.getDAOFactory().getUserDAO();
            User user = userDao.findUserByUsernameAndPassword(username, password);

            SessionManager.getInstance().createSession(user);
            System.out.println("Sessione creata");

        } catch (UserNotFoundException e) {
            throw new LoginException("Credenziali non valide");
        } catch (LoginException e) {
            throw new LoginException("Errore di sistema durante il login");
        }
    }

    public void externalLogin(ActionEvent event, String service) throws IOException {
        ExternalLogin boundary;

        if ("GitHub".equalsIgnoreCase(service)) {
            boundary = new GithubAuthAdapter();
        } else {
            boundary = new GoogleAuthAdapter();
        }

        UserBean externalUserBean;

        try {
            externalUserBean = boundary.authenticate();
            if (externalUserBean == null) {
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            UserDAO dao = DAOFactory.getDAOFactory().getUserDAO();
            User user = dao.findUserForExternalLogin(externalUserBean.getUserName());

            if (user == null) {
                Navigator.getNavigatorInstance().goToRegistration(event, externalUserBean);
            } else {
                SessionManager.getInstance().createSession(user);
                UserBean sessionBean = convertUserToBean(user);
                Navigator.getNavigatorInstance().goToLogin(event, sessionBean);
            }

        } catch (UserNotFoundException e) {
            Navigator.getNavigatorInstance().goToRegistration(event, externalUserBean);
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    public void register(ActionEvent event, UserBean userBean) throws RegistrationException {

        try {
            UserDAO userDao = DAOFactory.getDAOFactory().getUserDAO();
            userDao.registerUser(convertUserBeanToModel(userBean));
            Navigator.getNavigatorInstance().goToLogin(event, userBean);
        } catch (RegistrationException e) {
            throw new RegistrationException("Errore tecnico durante la registrazione");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private User convertUserBeanToModel(UserBean bean) {
        return new User(bean.getUserName(), bean.getName(), bean.getSurname(), bean.getPassword(), bean.getRole());
    }

    private UserBean convertUserToBean(User user) {
        UserBean userBean = new UserBean();
        userBean.setUserName(user.getUserName());
        userBean.setPassword(user.getPassword());
        userBean.setName(user.getName());
        userBean.setSurname(user.getSurname());
        userBean.setRole(user.getRole());
        return userBean;
    }
}