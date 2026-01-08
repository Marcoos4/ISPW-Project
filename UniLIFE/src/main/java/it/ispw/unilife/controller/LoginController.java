package it.ispw.unilife.controller;

import it.ispw.unilife.bean.UserBean;
import it.ispw.unilife.boundary.AppleAuthBoundary;
import it.ispw.unilife.boundary.ExternalAuthBoundary;
import it.ispw.unilife.boundary.GoogleAuthBoundary;
import it.ispw.unilife.dao.DAOFactory;
import it.ispw.unilife.dao.UserDAO;
import it.ispw.unilife.exception.DuplicateUserException;
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
        } catch (Exception e) {
            throw new LoginException("Errore di sistema durante il login");
        }
    }

    public void googleLogin(UserBean userBean) throws Exception {
        ExternalAuthBoundary boundary = new GoogleAuthBoundary();
        userBean =  boundary.authenticate();
    }

    public void appleLogin(UserBean userBean){
        ExternalAuthBoundary appleBoundary = new AppleAuthBoundary();
    }

    public void register(UserBean userBean) throws RegistrationException {

        String pwd = userBean.getPassword();

        if (pwd == null || pwd.length() < 8) {
            throw new RegistrationException("At least 8 chars");
        }

        String usrName = userBean.getUserName();
        String name = userBean.getName();
        String surname = userBean.getSurname();

        String email = userBean.getEmail();
        Role role = userBean.getRole();

        User user = new User(usrName, name, surname, email, pwd, role);

        try {
            UserDAO dao = DAOFactory.getDAOFactory().getUserDAO();
            dao.insert(user);
        } catch (Exception e) {
            throw new RegistrationException("Errore tecnico durante la registrazione");
        }
    }
}

