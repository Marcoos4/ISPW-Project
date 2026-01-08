package it.ispw.unilife.boundary;

// Gestione Credenziali e Flusso di Login
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;

// Server Locale per aprire il browser (Jetty)
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;

// Utility HTTP e JSON
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
import it.ispw.unilife.bean.UserBean; // Il tuo Bean
import java.io.IOException;

// Java Standard
import java.io.*;
import java.util.Arrays; // Serve per passare la lista degli scope (email, profile)
import java.util.List;

// import it.ispw.unilife.model.Role; // Decommenta se serve settare un default

import java.io.InputStreamReader;

public class GoogleAuthBoundary implements ExternalAuthBoundary{


    private static final List<String> SCOPES = Arrays.asList("email", "profile", "openid");
    private static FileDataStoreFactory dataStoreFactory;

    // 2. Definisce il gestore per leggere i file JSON
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    // 3. Definisce il gestore per le connessioni HTTP
    private static final NetHttpTransport httpTransport = new NetHttpTransport();

    // 4. Blocco statico per inizializzare la cartella dei token
    static {
        try {
            // "tokens" è il nome della cartella che verrà creata nel tuo progetto
            dataStoreFactory = new FileDataStoreFactory(new File("tokens"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Authorizes the installed application to access user's protected data. */
    private static Credential authorize() throws Exception {
        // load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(GoogleAuthBoundary.class.getResourceAsStream("/client_secrets.json")));
        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets,
                SCOPES).setDataStoreFactory(dataStoreFactory)
                .build();
        // authorize
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    @Override
    public UserBean authenticate() throws Exception {
        Credential credential = authorize();
        System.out.println("Recupero informazioni utente da Google...");

        // 1. Costruisci il servizio Oauth2 usando le credenziali appena ottenute
        Oauth2 oauth2 = new Oauth2.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName("UniLIFE")
                .build();

        // 2. Esegui la chiamata HTTP (GET userinfo)
        // Questa è la riga magica che trasforma il Token in Dati (Nome, Email, Foto)
        Userinfo googleUser = oauth2.userinfo().get().execute();

        // 3. Riempi il tuo UserBean con i dati ricevuti
        UserBean user = new UserBean();

        user.setUserName(googleUser.getEmail());

        // Google divide nome e cognome in GivenName e FamilyName
        user.setName(googleUser.getGivenName());
        user.setSurname(googleUser.getFamilyName());

        // Debug: vediamo cosa abbiamo preso
        System.out.println("Utente trovato: " + user.getUserName() + " (" + user.getName() + " " + user.getSurname() + ")");

        return user;
    }
}