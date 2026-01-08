package it.ispw.unilife.bean;

// Se hai un enum per il ruolo, usalo, altrimenti usa String
import it.ispw.unilife.model.Role;

public class UserBean {

    private String userName;
    private String name;
    private String surname;
    private String password;
    private Role role;

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getPassword() { return password; }
    public void setPassword(String password) {this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}