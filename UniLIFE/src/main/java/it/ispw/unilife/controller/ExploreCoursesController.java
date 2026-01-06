package it.ispw.unilife.controller;

import it.ispw.unilife.bean.CourseBean;
import it.ispw.unilife.bean.FilterSearchBean;
import it.ispw.unilife.bean.InterestSearchBean;
import it.ispw.unilife.dao.CourseDAO;
import it.ispw.unilife.dao.DAOFactory;
import it.ispw.unilife.model.Course;
import it.ispw.unilife.model.search.FilteredSearch;
import it.ispw.unilife.model.search.InterestSearch;
import it.ispw.unilife.model.search.Search;

import java.util.ArrayList;
import java.util.List;

public class ExploreCoursesController {

    DAOFactory daoFactory;
    CourseDAO courseDao;

    public ExploreCoursesController(){
        this.daoFactory = DAOFactory.getDAOFactory();
        this.courseDao = daoFactory.getCourseDAO();
    }

    public List<CourseBean> searchCourses(FilterSearchBean bean) {

        String uni = bean.getUniversityName();
        String fac = bean.getFaculty();
        String type = bean.getCourseType();
        String reg = bean.getRegion();
        Double cost = bean.getMaxCost();

        Search search = new FilteredSearch(uni, fac, type, reg, cost);
        return executeSearch(search);

    }

    public List<CourseBean> searchCourses(InterestSearchBean bean) {

        List<String> interests = bean.getSelectedInterest();

        Search search = new InterestSearch(interests);
        return executeSearch(search);
    }

    private List<CourseBean> executeSearch(Search search){

        List<CourseBean> beanList = new ArrayList<>();
        List<Course> resCourses =  search.execute(courseDao);

        for (Course course : resCourses) {
            CourseBean courseBean = convertCourseToBean(course);
            beanList.add(courseBean);
        }

        return beanList;
    }

    private CourseBean convertCourseToBean(Course course){

        CourseBean bean = new CourseBean();

        return bean;
    }


}
