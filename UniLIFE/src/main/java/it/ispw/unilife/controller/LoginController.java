package it.ispw.unilife.controller;

import it.ispw.unilife.bean.UserBean;
import it.ispw.unilife.boundary.*;
import it.ispw.unilife.dao.factory.DAOFactory;
import it.ispw.unilife.dao.UserDAO;
import it.ispw.unilife.exception.ExternalAuthenticationException;
import it.ispw.unilife.exception.LoginException;
import it.ispw.unilife.exception.RegistrationException;
import it.ispw.unilife.exception.UserNotFoundException;
import it.ispw.unilife.model.Role;
import it.ispw.unilife.model.User;
import it.ispw.unilife.model.session.SessionManager;
import it.ispw.unilife.view.Navigator;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.logging.Logger;

public class LoginController {

    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    public void login(UserBean userBean) throws LoginException {
        try {
            String username = userBean.getUserName();
            String password = userBean.getPassword();

            UserDAO userDao = DAOFactory.getDAOFactory().getUserDAO();
            User user = userDao.findUserByUsernameAndPassword(username, password);

            SessionManager.getInstance().createSession(user);
            logger.info("Sessione creata");

        } catch (UserNotFoundException e) {
            throw new LoginException("Credenziali non valide");
        }
    }

    public void externalLogin(String service) throws IOException {
        ExternalLogin boundary;

        if ("GitHub".equalsIgnoreCase(service)) {
            boundary = new GithubAuthAdapter();
        } else {
            boundary = new GoogleAuthAdapter();
        }

        UserBean externalUserBean;

        try {
            externalUserBean = boundary.authenticate();
            UserDAO dao = DAOFactory.getDAOFactory().getUserDAO();
            User user = dao.findUserForExternalLogin(externalUserBean.getUserName());

            SessionManager.getInstance().createSession(user);

        } catch (ExternalAuthenticationException e){

        } catch (UserNotFoundException e) {
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