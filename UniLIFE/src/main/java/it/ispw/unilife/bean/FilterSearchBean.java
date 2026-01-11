package it.ispw.unilife.bean;

public class FilterSearchBean {

    private String universityName;
    private String universityNation;

    private String language;
    private String faculty;
    private String courseType;
    private int maxCost;


    public FilterSearchBean(String universityName, String language, String faculty, String courseType, String universityNation, int maxCost) {
        this.universityName = universityName;
        this.language = language;
        this.faculty = faculty;
        this.courseType = courseType;
        this.universityNation = universityNation;
        this.maxCost = maxCost;
    }

    public String getUniversityName() {
        return universityName;
    }
    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getUniversityNation() {
        return universityNation;
    }
    public void setUniversityNation(String universityNation) {
        this.universityNation = universityNation;
    }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getFaculty() {
        return faculty;
    }
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getCourseType() {
        return courseType;
    }
    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }



    public int getMaxCost() {
        return maxCost;
    }
    public void setMaxCost(int maxCost) {
        this.maxCost = maxCost;
    }


}