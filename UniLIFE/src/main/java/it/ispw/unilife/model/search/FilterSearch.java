package it.ispw.unilife.model.search;

import it.ispw.unilife.bean.FilterSearchBean;
import it.ispw.unilife.model.Course;
import it.ispw.unilife.model.University;

import java.util.ArrayList;
import java.util.List;

public class FilterSearch {

    private final String universityName;
    private final String universityNation;
    private final String faculty;
    private final String courseType;
    private final String language;
    private final int maxCost;

    public FilterSearch(String universityName, String universityNation, String faculty,
                        String courseType, String language, int maxCost) {
        this.universityName = universityName;
        this.universityNation = universityNation;
        this.faculty = faculty;
        this.courseType = courseType;
        this.language = language;
        this.maxCost = maxCost;
    }

    public List<Course> execute(List<Course> courses) {
        List<Course> filteredList = new ArrayList<>();

        for (Course course : courses) {

            boolean isMatch = checkUniversityName(course.getUniversity());

            if (isMatch && !checkUniversityNation(course.getUniversity())) {
                isMatch = false;
            }

            if (isMatch && !checkString(course.getCourseType().toString(), this.courseType)) {
                isMatch = false;
            }

            if (isMatch && !checkString(course.getFaculty(), this.faculty)) {
                isMatch = false;
            }

            if (isMatch && !checkString(course.getLanguage(), this.language)) {
                isMatch = false;
            }

            if (isMatch && !checkCost(course.getCostEstimate())) {
                isMatch = false;
            }

            if (isMatch) {
                filteredList.add(course);
            }
        }

        return filteredList;
    }

    private boolean checkString(String valueToCheck, String filterValue) {
        if (filterValue == null || filterValue.trim().isEmpty()) {
            return true;
        }
        if (valueToCheck == null) {
            return false;
        }
        return valueToCheck.equalsIgnoreCase(filterValue);
    }

    private boolean checkUniversityName(University uni) {
        if (uni == null && (this.universityName != null && !this.universityName.isEmpty())) {
            return false;
        }

        if (uni == null)
            return true;

        return checkString(uni.getName(), this.universityName);
    }

    private boolean checkUniversityNation(University uni) {
        if (uni == null && (this.universityNation != null && !this.universityNation.isEmpty())) {
            return false;
        }
        if (uni == null)
            return true;

        return checkString(uni.getNation(), this.universityNation);
    }

    private boolean checkCost(int courseCost) {

        if (this.maxCost <= 0) {
            return true;
        }
        return courseCost <= this.maxCost;
    }
}

