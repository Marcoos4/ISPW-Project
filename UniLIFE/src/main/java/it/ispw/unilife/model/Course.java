package it.ispw.unilife.model;
import java.util.List;

public class Course {
    private String name;
    private University university;
    private CourseType courseType;
    private String faculty;
    private String language;
    private int costEstimate;
    private List<String> tags;

    private int studentAffinity;

    public Course(String name, University university, String faculty,
                  String language, CourseType courseType, List<String> tags){
        this.name = name;
        this.university = university;
        this.courseType = courseType;
        this.faculty = faculty;
        this.language = language;
        this.tags = tags;
    }

    public int calculateAffinity(){
        return 0;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public CourseType getCourseType() { return courseType; }
    public void setCourseType(CourseType courseType) { this.courseType = courseType; }

    public University getUniversity() { return university; }
    public void setUniversity(University university) { this.university = university; }

    public String getFaculty() { return faculty; }
    public void setFaculty(String faculty) { this.faculty = faculty; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public int getCostEstimate() { return costEstimate; }
    public void setCostEstimate(int costEstimate) { this.costEstimate = costEstimate; }

    public List<String> getTags() { return tags; }

    public int getStudentAffinity() { return studentAffinity; }
}