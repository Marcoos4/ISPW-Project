package it.ispw.unilife.boundary;

import it.ispw.unilife.bean.UserBean;

public interface ExternalAuthBoundary {
    public UserBean authenticate() throws Exception;
}