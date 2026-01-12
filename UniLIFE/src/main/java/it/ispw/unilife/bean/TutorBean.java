package it.ispw.unilife.bean;

import java.util.ArrayList;
import java.util.List;

public class TutorBean {
    private  String username;
    private String name;
    private  String surname;
    private List<String> subjects;
    private double hourlyRate;
    private int rating;

    // Costruttore vuoto
    public TutorBean(){}

    //TODO Da rimuovere!!!!
    public TutorBean(String username, String mario, String rossi, List<String> list, double v, int i) {
        this.username = username;
        this.name = mario;
        this.surname = rossi;
        this.subjects = list;
        this.hourlyRate = v;
        this.rating = i;
    }

    // --- GETTERS & SETTERS ---

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public List<String> getSubjects() { return subjects; }
    public void setSubjects(List<String> subjects) { this.subjects = subjects; }

    public double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }

    public int getRating() {return rating;}
    public void setRating(int rating){this.rating = rating;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
}