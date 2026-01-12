package it.ispw.unilife.controller;

import it.ispw.unilife.bean.UserBean;
import it.ispw.unilife.model.User;
import it.ispw.unilife.model.session.SessionManager;

public class SessionController {

    public UserBean checkPermission(){
        User user = SessionManager.getInstance().getSession().getUser();
        return convertUserToBean(user);
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
