package it.ispw.unilife.bean;

public class FiltersTutorBean {

    private String subject;     // Es. "Matematica" o "Tutte"
    private String priceRange;  // Es. "0-15" o "Tutti"
    private String timeSlot;    // Es. "9-13" o "Tutti"

    // Costruttore vuoto (Default)
    public FiltersTutorBean() {}

    // --- GETTERS & SETTERS ---

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }
}
