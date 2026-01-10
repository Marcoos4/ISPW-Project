package it.ispw.unilife.model.search;

import it.ispw.unilife.dao.CourseDAO;
import it.ispw.unilife.exception.DAOException;
import it.ispw.unilife.model.Course;
import java.util.List;

public class InterestSearch implements Search<Course>{

    private List<String> interests;

    public InterestSearch(List<String> interests) {
        this.interests = interests;
    }

    @Override
    public List<Course> execute(CourseDAO dao) throws DAOException {
        return null;
    }
}
