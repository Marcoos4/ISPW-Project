package it.ispw.unilife.model.session;

import it.ispw.unilife.model.User;

public class SessionManager {

    private static SessionManager instance = null;

    private Session currentSession;

    private SessionManager() {}

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public synchronized  void createSession(User user) {
        this.currentSession = new Session(user);
    }


    public Session getSession() {
        return this.currentSession;
    }


    public boolean sessionIsValid() {
        return this.currentSession != null;
    }


    public void invalidateSession() {
        this.currentSession = null;
    }
}
