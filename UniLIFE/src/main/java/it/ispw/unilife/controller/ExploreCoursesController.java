package it.ispw.unilife.controller;

import it.ispw.unilife.bean.CourseBean;
import it.ispw.unilife.bean.FilterSearchBean;
import it.ispw.unilife.dao.CourseDAO;
import it.ispw.unilife.dao.factory.DAOFactory;
import it.ispw.unilife.exception.DAOException;
import it.ispw.unilife.model.Course;
import it.ispw.unilife.model.search.FilterSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExploreCoursesController {

    private static final Logger logger = Logger.getLogger(ExploreCoursesController.class.getName());
    DAOFactory daoFactory;
    CourseDAO courseDao;

    public ExploreCoursesController(){
        this.daoFactory = DAOFactory.getDAOFactory();
        this.courseDao = daoFactory.getCourseDAO();
    }

    public List<CourseBean> searchCoursesByFilters(FilterSearchBean bean) {

        List<Course> courses;

        try {
            courses = courseDao.getAll();
        } catch (DAOException e) {
            logger.log(Level.SEVERE, "ERRORE: Can't access courseDAO");
            return new ArrayList<>();

        }

        String uniName = bean.getUniversityName();
        String uniNation = bean.getUniversityNation();
        String faculty = bean.getFaculty();
        String type = bean.getCourseType();
        String lang = bean.getLanguage();
        int cost = bean.getMaxCost();


        FilterSearch search = new FilterSearch(uniName, uniNation, faculty, type, lang, cost);
        List<Course> resCourses = search.execute(courses);

        List<CourseBean> beanList = new ArrayList<>();

        for (Course course : resCourses) {
            CourseBean courseBean = convertCourseToBean(course);
            beanList.add(courseBean);
        }

        return beanList;
    }

    public List<FilterSearchBean> getAvailableFaculties() {
        List<FilterSearchBean> beanList = new ArrayList<>();
        try {
            List<String> faculties = courseDao.getDistinctFaculties();

            for (String faculty : faculties) {
                beanList.add(new FilterSearchBean(null, null, faculty, null, null, -1));
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return beanList;
    }

    public List<FilterSearchBean> getAvailableUniversities() {
        List<FilterSearchBean> beanList = new ArrayList<>();
        try {
            List<String> unis = courseDao.getDistinctUniversities();

            for (String uni : unis) {
                beanList.add(new FilterSearchBean(uni, null, null, null, null, -1));
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return beanList;
    }

    public List<FilterSearchBean> getAvailableLanguages() {
        List<FilterSearchBean> beanList = new ArrayList<>();
        try {
            List<String> langs = courseDao.getDistinctLanguages();

            for (String lang : langs) {
                beanList.add(new FilterSearchBean(null, lang, null, null, null, -1));
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return beanList;
    }


    public List<FilterSearchBean> getAvailableCourseTypes() {
        List<FilterSearchBean> beanList = new ArrayList<>();
        try {
            List<String> types = courseDao.getDistinctCourseTypes();

            for (String type : types) {
                beanList.add(new FilterSearchBean(null, null, null, type, null, -1));
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return beanList;
    }

    private CourseBean convertCourseToBean(Course course){

        CourseBean bean = new CourseBean();

        bean.setName(course.getName());
        bean.setTags(course.getTags());

        if (course.getUniversity() != null) {
            bean.setUniversity(course.getUniversity().getName());
            bean.setNation(course.getUniversity().getNation());
        } else {
            bean.setUniversity("N/A");
            bean.setNation("N/A");
        }

        bean.setFaculty(course.getFaculty());
        bean.setAnnualCost(String.format("%d €", course.getCostEstimate()));
        bean.setCourseType(course.getCourseType());

        int affinity = course.getStudentAffinity();
        if (affinity > 0) {
            bean.setAffinity(affinity + "%");
        } else {
            bean.setAffinity("N/A");
        }

        return bean;
    }
}