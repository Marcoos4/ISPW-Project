package it.ispw.unilife.boundary;

import com.google.api.services.oauth2.model.Userinfo;
import it.ispw.unilife.bean.UserBean;
import it.ispw.unilife.exception.ExternalAuthenticationException;

import java.io.IOException;

public class GoogleAuthAdapter implements ExternalLogin {

    private final GoogleAuthBoundary googleBoundary = new GoogleAuthBoundary();

    @Override
    public UserBean authenticate() throws ExternalAuthenticationException, IOException, InterruptedException {
        Userinfo googleData = googleBoundary.executeLoginAndFetchProfile();

        UserBean user = new UserBean();
        user.setUserName(googleData.getEmail());
        user.setName(googleData.getGivenName());
        user.setSurname(googleData.getFamilyName());

        return user;
    }
}