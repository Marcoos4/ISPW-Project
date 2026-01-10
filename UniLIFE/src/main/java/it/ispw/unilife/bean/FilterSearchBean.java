package it.ispw.unilife.bean;

public class FilterSearchBean {

    private String universityName;
    private String faculty;       // Es. "Ingegneria", "Medicina"
    private String courseType;    // Es. "Triennale", "Magistrale"
    private String region;
    private Double maxCost;

    public FilterSearchBean() {
    }

    public FilterSearchBean(String universityName, String faculty, String courseType, String region, Double maxCost) {
        this.universityName = universityName;
        this.faculty = faculty;
        this.courseType = courseType;
        this.region = region;
        this.maxCost = maxCost;
    }

    public String getUniversityName() {
        return universityName;
    }
    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

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

    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }

    public Double getMaxCost() {
        return maxCost;
    }
    public void setMaxCost(Double maxCost) {
        this.maxCost = maxCost;
    }

    // Metodo di utilità per il debug
    @Override
    public String toString() {
        return "FilterSearchBean{" +
                "university='" + universityName + '\'' +
                ", faculty='" + faculty + '\'' +
                ", type='" + courseType + '\'' +
                '}';
    }
}