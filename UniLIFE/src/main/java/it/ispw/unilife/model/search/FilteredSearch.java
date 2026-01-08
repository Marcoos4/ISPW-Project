package it.ispw.unilife.model.search;

import it.ispw.unilife.dao.CourseDAO;
import it.ispw.unilife.exception.DAOException;
import it.ispw.unilife.model.Course;
import java.util.List;

public class FilteredSearch implements Search{

    private String university;
    private String faculty;
    private String courseType;
    private String region;
    private Double maxCost;

    public FilteredSearch(String university, String faculty, String courseType, String region, Double maxCost) {
        this.university = university;
        this.faculty = faculty;
        this.courseType = courseType;
        this.region = region;
        this.maxCost = maxCost;
    }
    @Override
    public List<Course> execute(CourseDAO dao) throws DAOException {
        return null;
    }
}
