package it.ispw.unilife.bean;

import java.util.List;

public class CourseBean {

    private String name;
    private String university;
    private String affinity;     // Already Formatted: "85%" o "N/A"

    private String description;
    private String annualCost;   // Ex: "2500.0 €"
    private String nation;
    private String faculty;
    private List<String> tags;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUniversity() { return university; }
    public void setUniversity(String university) { this.university = university; }

    public String getAffinity() { return affinity; }
    public void setAffinity(String affinity) { this.affinity = affinity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAnnualCost() { return annualCost; }
    public void setAnnualCost(String annualCost) { this.annualCost = annualCost; }

    public String getNation() { return nation; }
    public void setNation(String nation) { this.nation = nation; }

    public String getFaculty() { return faculty; }
    public void setFaculty(String faculty) { this.faculty = faculty; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

}