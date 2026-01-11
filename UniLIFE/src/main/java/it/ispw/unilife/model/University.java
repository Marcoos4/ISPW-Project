package it.ispw.unilife.model;

public class University {

    private String name;
    private String nation;

    public University(String name, String nation){
        this.name = name;
        this.nation = nation;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getNation() { return nation; }
    public void setNation(String nation) { this.nation = nation; }
}