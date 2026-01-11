package it.ispw.unilife.boundary;

import it.ispw.unilife.bean.UserBean;

public interface ExternalLogin {
    UserBean authenticate() throws Exception;
}