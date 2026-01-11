package it.ispw.unilife.dao;

import it.ispw.unilife.dao.factory.DAOFactory;
import it.ispw.unilife.exception.DAOException;
import it.ispw.unilife.model.Course;
import it.ispw.unilife.model.CourseType;
import it.ispw.unilife.model.University;


import java.util.ArrayList;
import java.util.Collections;
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

    @Override
    public List<String> getDistinctFaculties() throws DAOException {
        List<Course> courses = this.getAll();
        List<String> distinctFaculties = new ArrayList<>();

        for (Course c : courses) {
            String faculty = c.getFaculty();
            if (faculty != null && !faculty.isEmpty()) {
                if (!distinctFaculties.contains(faculty)) {
                    distinctFaculties.add(faculty);
                }
            }
        }
        Collections.sort(distinctFaculties);
        return distinctFaculties;
    }

    public List<String> getDistinctUniversities() throws DAOException {
        List<Course> courses = this.getAll();
        List<String> distinctUnis = new ArrayList<>();

        for (Course c : courses) {
            if (c.getUniversity() != null && c.getUniversity().getName() != null) {
                String uniName = c.getUniversity().getName();

                if (!distinctUnis.contains(uniName)) {
                    distinctUnis.add(uniName);
                }
            }
        }
        Collections.sort(distinctUnis);
        return distinctUnis;
    }

    @Override
    public List<String> getDistinctLanguages() throws DAOException {
        List<Course> courses = this.getAll();
        List<String> distinctLanguages = new ArrayList<>();

        for (Course c : courses) {
            String lang = c.getLanguage();

            if (lang != null && !lang.isEmpty()) {
                if (!distinctLanguages.contains(lang)) {
                    distinctLanguages.add(lang);
                }
            }
        }
        Collections.sort(distinctLanguages);
        return distinctLanguages;
    }

    @Override
    public List<String> getDistinctCourseTypes() throws DAOException {
        List<Course> courses = this.getAll();
        List<String> distinctTypes = new ArrayList<>();

        for (Course c : courses) {
            if (c.getCourseType() != null) {
                String typeStr = c.getCourseType().toString();

                if (!distinctTypes.contains(typeStr)) {
                    distinctTypes.add(typeStr);
                }
            }
        }

        Collections.sort(distinctTypes);
        return distinctTypes;
    }
}