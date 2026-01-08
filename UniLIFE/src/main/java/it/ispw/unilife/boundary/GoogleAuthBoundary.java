package it.ispw.unilife.boundary;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;

import it.ispw.unilife.bean.UserBean;
// import it.ispw.unilife.model.Role; // Decommenta se serve settare un default

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class GoogleAuthBoundary implements ExternalAuthBoundary{

    private static final String CREDENTIALS_FILE_PATH = "/client_secret.json";
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Arrays.asList("email", "profile", "openid");
    private static final String APP_NAME = "UniLife";

    public UserBean authenticate() throws Exception {

        // 1. Setup standard (Identico a prima)
        InputStream in = GoogleAuthBoundary.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new Exception("File client_secret.json mancante in resources!");
        }

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(new GsonFactory(), new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, new GsonFactory(), clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

        Oauth2 oauth2 = new Oauth2.Builder(HTTP_TRANSPORT, new GsonFactory(), credential)
                .setApplicationName(APP_NAME)
                .build();

        Userinfo googleUser = oauth2.userinfo().get().execute();

        // 2. RIEMPIMENTO DEL TUO BEAN
        UserBean user = new UserBean();

        user.setEmail(googleUser.getEmail());
        user.setName(googleUser.getGivenName());
        user.setSurname(googleUser.getFamilyName());

        // Bisogna chiedere all'utente di completare creazione profilo
        user.setUserName(null); // O  mettere user.setEmail(googleUser.getEmail()) temporaneamente
        user.setPassword(null);
        user.setRole(null);

        return user;
    }
}