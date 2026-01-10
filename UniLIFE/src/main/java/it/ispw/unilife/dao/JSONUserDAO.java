package it.ispw.unilife.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.ispw.unilife.exception.RegistrationException;
import it.ispw.unilife.exception.UserNotFoundException;
import it.ispw.unilife.model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JSONUserDAO implements UserDAO {

    private final File file;
    private final Gson gson;
    private List<User> users;
    private static final Logger logger = Logger.getLogger(JSONUserDAO.class.getName());

    public JSONUserDAO() {
        // Cambiamo il nome del file in user.json
        this.file = new File("data/user.json");

        // Per User non servono (per ora) gli adapter per le date, basta il pretty printing
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        this.users = new ArrayList<>();

        initFile();
    }

    private void initFile() {
        try {
            if (!file.exists()) {
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs();
                }

                if(!file.createNewFile()){
                    logger.log(Level.SEVERE, "Errore nel caricamento del file");
                }

                // Inizializza con parentesi quadre per il JSON Array vuoto
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("[]");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE,"Errore critico: Impossibile creare il file di storage user.json.", e);
        }
    }

    /**
     * Carica tutti gli utenti dal file JSON in memoria.
     */
    private void loadAll() {
        try (Reader reader = new FileReader(this.file)) {
            Type listType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> fromFile = gson.fromJson(reader, listType);

            if (fromFile != null) {
                this.users = fromFile;
            } else {
                this.users = new ArrayList<>();
            }

        } catch (FileNotFoundException e) {
            this.users = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Salva la lista corrente su file.
     */
    private void saveTo() {
        try (Writer writer = new FileWriter(this.file)) {
            gson.toJson(this.users, writer);
        } catch (IOException e) {
            throw new RuntimeException("Errore durante il salvataggio su user.json", e);
        }
    }

    // --- IMPLEMENTAZIONE METODI DELL'INTERFACCIA USERDAO ---

    @Override
    public User findUserByUsernameAndPassword(String username, String password) throws UserNotFoundException {
        loadAll(); // Ricarica sempre per avere i dati aggiornati

        for (User user : this.users) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        throw new UserNotFoundException();
    }

    @Override
    public User findUserForExternalLogin(String username) throws UserNotFoundException {
        loadAll();

        for (User user : this.users) {
            // Per il login esterno controlliamo solo lo username (email)
            if (user.getUserName().equalsIgnoreCase(username)) {
                return user;
            }
        }
        throw new UserNotFoundException();
    }

    @Override
    public void registerUser(User rUser) throws RegistrationException {
        loadAll();

        // Controllo duplicati
        for (User user : this.users) {
            if (user.getUserName().equalsIgnoreCase(rUser.getUserName())) {
                throw new RegistrationException("Utente già registrato con questa email.");
            }
        }

        this.users.add(rUser);

        saveTo();
    }
}