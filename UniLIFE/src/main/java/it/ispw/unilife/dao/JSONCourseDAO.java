package it.ispw.unilife.dao;

import it.ispw.unilife.dao.factory.DAOFactory;
import it.ispw.unilife.exception.DAOException;
import it.ispw.unilife.model.Course;
import it.ispw.unilife.model.University;


import java.util.List;
import java.util.logging.Level;

public class JSONCourseDAO extends JSONDAO<Course> implements CourseDAO {

    private final UniversityDAO universityDAO;

    public JSONCourseDAO(){
        super("data/courses.json", Course.class);
        this.universityDAO = DAOFactory.getDAOFactory().getUniversityDAO();
    }

    @Override
    public List<Course> getAll() throws DAOException {

        List<Course> courses;

        try {
            courses = super.getAll();

            for (Course course : courses) {
                findUniversity(course);
            }

        } catch (DAOException e) {
            throw new DAOException("ERROR: Can't create Course from DAO");
        }

        return courses;
    }

    private void findUniversity(Course course) throws DAOException {

        if (course.getUniversity() == null || course.getUniversity().getName() == null) {
            throw new DAOException("ERROR: Can't find Course University");
        }

        String uniName = course.getUniversity().getName();
        University uniModel;

        try {
            uniModel = universityDAO.findByName(uniName);
        } catch (DAOException e) {
            throw new DAOException("ERROR: Can't find matching University");
        }

        course.setUniversity(uniModel);
    }
}