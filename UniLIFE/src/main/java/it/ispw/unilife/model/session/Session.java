package it.ispw.unilife.model.session;

import it.ispw.unilife.model.User;

import java.time.LocalDateTime;

public class Session {

    private final User user;
    private final LocalDateTime loginTime;

    protected Session(User user) {
        this.user = user;
        this.loginTime = LocalDateTime.now();
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public boolean isStudent() {
        return "STUDENT".equalsIgnoreCase(user.getRole().toString());
    }

    public boolean isProfessor() {
        return "PROFESSOR".equalsIgnoreCase(user.getRole().toString());
    }

}
