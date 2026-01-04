package it.ispw.unilife.bean;

import java.util.List;

public class Tutor {
    private String id;
    private String name;
    private  String surname;
    private List<String> subjects;
    private double hourlyRate;

    // Costruttore vuoto
    public Tutor(){}

    // Costruttore completo
    public Tutor(String id, String name, String surname, List<String> subjects, double hourlyRate){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.subjects = subjects;
        this.hourlyRate = hourlyRate;
    }

    // --- GETTERS & SETTERS ---

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public List<String> getSubjects() { return subjects; }
    public void setSubjects(List<String> subjects) { this.subjects = subjects; }

    public double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }


}
