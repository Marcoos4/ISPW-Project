package it.ispw.unilife.boundary;

import it.ispw.unilife.bean.UserBean;
import it.ispw.unilife.exception.ExternalAuthenticationException;

import java.io.IOException;

public interface ExternalLogin {
    UserBean authenticate() throws ExternalAuthenticationException, IOException, InterruptedException;
}