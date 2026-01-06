package it.ispw.unilife.bean;

import java.util.ArrayList;
import java.util.List;

public class TutorBean {
    private String name;
    private  String surname;
    private List<String> subjects;
    private double hourlyRate;
    private int rating;

    // Costruttore vuoto
    public TutorBean(){}

    // Costruttore completo
    public TutorBean(String name, String surname, List<String> subjects, double hourlyRate, int rating){
        this.name = name;
        this.surname = surname;
        this.subjects = subjects;
        this.hourlyRate = hourlyRate;
        this.rating = rating;
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
}