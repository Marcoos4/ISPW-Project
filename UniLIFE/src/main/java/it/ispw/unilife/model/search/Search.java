package it.ispw.unilife.model.search;
import it.ispw.unilife.dao.CourseDAO;
import it.ispw.unilife.exception.DAOException;
import it.ispw.unilife.model.Course;
import java.util.List;

public interface Search {
    List<Course> execute(CourseDAO dao) throws DAOException;
}
