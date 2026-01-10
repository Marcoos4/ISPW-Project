package it.ispw.unilife.boundary;

import com.google.api.services.oauth2.model.Userinfo;
import it.ispw.unilife.boundary.ExternalLogin;
import it.ispw.unilife.bean.UserBean;

public class GoogleAuthAdapter implements ExternalLogin {

    private final GoogleAuthBoundary googleBoundary = new GoogleAuthBoundary();

    @Override
    public UserBean authenticate() throws Exception {
        Userinfo googleData = googleBoundary.executeLoginAndFetchProfile();

        UserBean user = new UserBean();
        user.setUserName(googleData.getEmail());
        user.setName(googleData.getGivenName());
        user.setSurname(googleData.getFamilyName());

        return user;
    }
}