package it.ispw.unilife.dao.factory;

import it.ispw.unilife.dao.*;

public class JSONDAOFactory extends DAOFactory {

    public JSONDAOFactory() {
        super();
    }

    @Override
    public UserDAO getUserDAO(){ return new JSONUserDAO();}
    @Override
    public CourseDAO getCourseDAO(){ return new JSONCourseDAO();}
    @Override
    public UniversityDAO getUniversityDAO(){return new JSONUniversityDAO();}
    @Override
    public JSONReservationDAO getReservationDAO(){
        return new JSONReservationDAO();
    }
    @Override
    public JSONStudentDAO getStudentDAO(){return new JSONStudentDAO();}
    @Override
    public TutorDAO getTutorDAO() {return new JSONTutorDAO();}
    @Override
    public SubjectDAO getSubjectDAO() {return new JSONSubjectDAO();}

}
