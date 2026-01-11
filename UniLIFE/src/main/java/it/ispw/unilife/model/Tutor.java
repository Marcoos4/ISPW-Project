package it.ispw.unilife.model;

import it.ispw.unilife.bean.TutorBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity che rappresenta un Tutor nel sistema.
 * L'ID è stato rimosso per design del team.
 */
public class Tutor extends User {

    private List<String> subjects;
    private double hourlyRate;
    private int rating;

    /**
     * Costruttore completo usato dal DAO o per la logica di business.
     */
    public Tutor(String name, String surname, List<String> subjects, double hourlyRate, int rating, String username, String password) {
        super(username, name, surname, password,Role.TUTOR);
        this.subjects = (subjects != null) ? subjects : new ArrayList<>();
        this.hourlyRate = hourlyRate;
        this.rating = rating;
    }


    /**
     * Restituisce le materie come stringa separata da virgole.
     */
    public String getSubjectsAsString() {
        if (this.subjects.isEmpty()) return "Nessuna materia";
        return String.join(", ", this.subjects);
    }

    /**
     * Aggiunge una materia alla lista, evitando duplicati.
     */
    public void addSubject(String subject) {
        if (subject != null && !subject.trim().isEmpty()) {
            if (!this.subjects.contains(subject)) {
                this.subjects.add(subject);
            }
        }
    }

    /**
     * Controlla se il tutor insegna una determinata materia (case-insensitive).
     */
    public boolean teachesSubject(String querySubject) {
        if (querySubject == null || querySubject.equals("Tutte")) return true;

        for (String s : this.subjects) {
            if (s.equalsIgnoreCase(querySubject)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se il tutor rientra in una fascia di prezzo.
     */
    public boolean isPriceInRange(double min, double max) {
        return this.hourlyRate >= min && this.hourlyRate <= max;
    }

    public List<String> getSubjects() { return subjects; }
    public double getHourlyRate() { return hourlyRate; }
    public int getRating() { return rating; }

    public void setRating(int rating) {
        if (rating < 0) this.rating = 0;
        else if (rating > 5) this.rating = 5;
        else this.rating = rating;
    }
}