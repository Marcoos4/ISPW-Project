package it.ispw.unilife.dao;

import it.ispw.unilife.dao.factory.ConnectionFactory;
import it.ispw.unilife.exception.DAOException;
import it.ispw.unilife.model.Course;
import it.ispw.unilife.model.CourseType;
import it.ispw.unilife.model.University;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBCourseDAO implements CourseDAO {

    private static final Logger logger = Logger.getLogger(DBCourseDAO.class.getName());

    private static final String SELECT_ALL_QUERY =
            "SELECT c.name, c.faculty, c.language, c.cost_estimate, c.course_type, c.tags, " +
                    "u.name AS uni_name, u.nation, u.city " +
                    "FROM courses c " +
                    "JOIN universities u ON c.university_name = u.name";

    private static final String INSERT_QUERY =
            "INSERT INTO courses (name, faculty, language, cost_estimate, course_type, tags, university_name) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String DELETE_QUERY = "DELETE FROM courses WHERE name = ?";

    private static final String QUERY_DISTINCT_FACULTY =
            "SELECT DISTINCT faculty " +
                    "FROM courses " +
                    "ORDER BY faculty";
    private static final String QUERY_DISTINCT_LANGUAGE =
            "SELECT DISTINCT language " +
                    "FROM courses " +
                    "ORDER BY language";

    private static final String QUERY_DISTINCT_UNI =
            "SELECT DISTINCT university_name " +
            "FROM courses " +
            "ORDER BY university_name";
    private static final String QUERY_DISTINCT_TYPE =
            "SELECT DISTINCT course_type " +
                    "FROM courses " +
                    "ORDER BY course_type";

    @Override
    public List<String> getDistinctFaculties() throws DAOException {
        return getDistinctStrings(QUERY_DISTINCT_FACULTY, "faculty");
    }

    @Override
    public List<String> getDistinctLanguages() throws DAOException {
        return getDistinctStrings(QUERY_DISTINCT_LANGUAGE, "language");
    }

    @Override
    public List<String> getDistinctUniversities() throws DAOException {
        return getDistinctStrings(QUERY_DISTINCT_UNI, "university_name");
    }

    @Override
    public List<String> getDistinctCourseTypes() throws DAOException {
        return getDistinctStrings(QUERY_DISTINCT_TYPE, "course_type");
    }

    @Override
    public List<Course> getAll() throws DAOException {
        List<Course> courses = new ArrayList<>();
        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_QUERY)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                University university = new University(
                        rs.getString("uni_name"),
                        rs.getString("nation")
                );

                String tagsString = rs.getString("tags");
                List<String> tags = new ArrayList<>();
                if (tagsString != null && !tagsString.isEmpty()) {
                    tags = new ArrayList<>(Arrays.asList(tagsString.split(",")));
                }

                String typeStr = rs.getString("course_type");
                CourseType type = CourseType.stringToDegreeType(typeStr);

                Course course = new Course(
                        rs.getString("name"),
                        university,
                        rs.getString("faculty"),
                        rs.getString("language"),
                        type,
                        tags
                );

                course.setCostEstimate(rs.getInt("cost_estimate"));
                courses.add(course);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "DB Error in getAll courses", e);
            throw new DAOException("Can't get Course List from DB");
        }

        return courses;
    }

    @Override
    public void insert(Course course) throws DAOException {
        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(INSERT_QUERY)) {

            stmt.setString(1, course.getName());
            stmt.setString(2, course.getFaculty());
            stmt.setString(3, course.getLanguage());
            stmt.setInt(4, course.getCostEstimate());

            stmt.setString(5, (course.getCourseType() != null) ? course.getCourseType().toString() : null);

            // Tags list is saved as a CSV VARCHAR
            String tagsString = "";
            if (course.getTags() != null && !course.getTags().isEmpty()) {
                tagsString = String.join(",", course.getTags());
            }
            stmt.setString(6, tagsString);

            if (course.getUniversity() != null) {
                stmt.setString(7, course.getUniversity().getName());
            } else {
                throw new DAOException("ERROR: University is Needed to insert a Course");
            }

            stmt.executeUpdate();
            logger.info("Inserted Course: " + course.getName());

        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                throw new DAOException("ERROR: Course already in DB");
            }
            logger.log(Level.SEVERE, "DB Error in insert course", e);
            throw new DAOException("ERROR: Can't save course");
        }
    }

    @Override
    public void delete(Course course) throws DAOException {
        if (course == null || course.getName() == null) {
            return;
        }

        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(DELETE_QUERY)) {

            // Usiamo il nome come chiave per l'eliminazione
            stmt.setString(1, course.getName());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                logger.warning("No Course found with name: " + course.getName());
                // Opzionale: throw new DAOException("Corso non trovato");
            } else {
                logger.info("Deleted Course: " + course.getName());
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "DB Error in delete course", e);
            throw new DAOException("Errore durante l'eliminazione del corso.");
        }
    }

    private List<String> getDistinctStrings(String query, String columnName) throws DAOException {
        List<String> results = new ArrayList<>();
        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String val = rs.getString(columnName);
                if (val != null && !val.trim().isEmpty()) {
                    results.add(val);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can't find filters for: " + columnName);
        }
        return results;
    }
}