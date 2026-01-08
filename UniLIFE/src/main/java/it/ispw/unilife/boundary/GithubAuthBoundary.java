package it.ispw.unilife.boundary;

import it.ispw.unilife.bean.UserBean;

public class GithubAuthBoundary implements ExternalAuthBoundary{

    @Override
    public UserBean authenticate() throws Exception{
        return new UserBean();
    }
}
