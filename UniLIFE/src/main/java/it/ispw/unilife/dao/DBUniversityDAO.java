package it.ispw.unilife.dao;

import it.ispw.unilife.dao.factory.ConnectionFactory;
import it.ispw.unilife.exception.DAOException;
import it.ispw.unilife.model.University;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUniversityDAO implements UniversityDAO {

    private static final Logger logger = Logger.getLogger(DBUniversityDAO.class.getName());

    private static final String SELECT_ALL_QUERY = "SELECT name, nation FROM universities";
    private static final String FIND_BY_NAME_QUERY = "SELECT name, nation FROM universities WHERE name = ?";
    private static final String INSERT_QUERY = "INSERT INTO universities (name, nation) VALUES (?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM universities WHERE name = ?";

    @Override
    public List<University> getAll() throws DAOException {
        List<University> universities = new ArrayList<>();
        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_QUERY)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                University uni = mapRowToUniversity(rs);
                universities.add(uni);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "ERROR: Can't Access University DB", e);
            throw new DAOException("ERROR: Impossible to obtain Universities list .");
        }
        return universities;
    }


    @Override
    public University findByName(String name) throws DAOException {
        if (name == null || name.trim().isEmpty()) return null;

        Connection conn = ConnectionFactory.getConnection();
        University university = null;

        try (PreparedStatement stmt = conn.prepareStatement(FIND_BY_NAME_QUERY)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                university = mapRowToUniversity(rs);
            } else {

                throw new DAOException("University not Found: " + name);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "University DB error searching");
            throw new DAOException("ERROR: Can't Access University DB to findByName");
        }
        return university;
    }

    @Override
    public void insert(University university) throws DAOException {
        if (university == null) return;

        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(INSERT_QUERY)) {

            stmt.setString(1, university.getName());
            stmt.setString(2, university.getNation());

            stmt.executeUpdate();
            logger.info("Inserted Uni: " + university.getName());

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062 || e.getMessage().contains("Duplicate")) {
                throw new DAOException("There's already a Uni: " + university.getName());
            }
            throw new DAOException("ERROR: Can't insert Uni: " + university.getName());
        }
    }

    @Override
    public void delete(University university) throws DAOException {
        if (university == null || university.getName() == null) return;

        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(DELETE_QUERY)) {

            stmt.setString(1, university.getName());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                logger.info("Deleted: " + university.getName());
            } else {
                logger.warning("Tentativo di eliminazione fallito (non trovata): " + university.getName());
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1451 || e.getMessage().contains("Constraint")) {
                throw new DAOException("Can't delete University due to Constraints");
            }
            throw new DAOException("ERROR: can't delete course");
        }
    }

    private University mapRowToUniversity(ResultSet rs) throws SQLException {
        return new University(
                rs.getString("name"),
                rs.getString("nation")
        );
    }
}