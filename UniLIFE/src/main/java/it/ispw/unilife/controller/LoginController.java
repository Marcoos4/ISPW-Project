package it.ispw.unilife.controller;

import com.google.api.client.auth.oauth2.Credential;
import it.ispw.unilife.bean.UserBean;
import it.ispw.unilife.boundary.GithubAuthBoundary;
import it.ispw.unilife.boundary.ExternalAuthBoundary;
import it.ispw.unilife.boundary.GoogleAuthBoundary;
import it.ispw.unilife.dao.DAOFactory;
import it.ispw.unilife.dao.UserDAO;
import it.ispw.unilife.exception.LoginException;
import it.ispw.unilife.exception.RegistrationException;
import it.ispw.unilife.exception.UserNotFoundException;
import it.ispw.unilife.model.Role;
import it.ispw.unilife.model.User;
import it.ispw.unilife.model.session.SessionManager;

public class LoginController {

    public void login(UserBean userBean) throws LoginException {
        try {

            String username = userBean.getUserName();
            String password = userBean.getPassword();

            UserDAO userDao = DAOFactory.getDAOFactory().getUserDAO();
            User user = userDao.findUserByUsernameAndPassword(username, password);

            SessionManager.getInstance().createSession(user);

        } catch (UserNotFoundException e) {
            throw new LoginException("Credenziali non valide");
        } catch (LoginException e) {
            throw new LoginException("Errore di sistema durante il login");
        }
    }

    public void externalLogin(UserBean userBean, String service){
        ExternalAuthBoundary boundary;

        if("Apple".equalsIgnoreCase(service))
            boundary = new GithubAuthBoundary();
        else
            boundary = new GoogleAuthBoundary();

        try {
            UserBean userB = boundary.authenticate();
            User user = convertUserBeanToModel(userB);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void register(UserBean userBean) throws RegistrationException {

        String pwd = userBean.getPassword();

        if (pwd == null || pwd.length() < 8) {
            throw new RegistrationException("At least 8 chars");
        }

        String usrName = userBean.getUserName();
        String name = userBean.getName();
        String surname = userBean.getSurname();

        Role role = userBean.getRole();

        try {
            UserDAO userDao = DAOFactory.getDAOFactory().getUserDAO();
            userDao.registerUser(usrName, name, surname, pwd, role);

        } catch (RegistrationException e) {
            throw new RegistrationException("Errore tecnico durante la registrazione");
        }
    }

    private User convertUserBeanToModel(UserBean bean){
        return null;
    }
}

