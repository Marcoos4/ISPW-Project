package it.ispw.unilife.dao;

import it.ispw.unilife.exception.DAOException;
import it.ispw.unilife.model.Tutor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DBTutorDAO implements TutorDAO{

    private static List<Tutor> tutors = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(DBUserDAO.class.getName());

    @Override
    public List getAll() {
        return List.of();
    }

    @Override
    public void insert(Tutor tutor) {
        String query = "INSERT INTO tutor (username, hourlyrate, rating) VALUES (?, ?, ?)";

        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, tutor.getUserName());
            stmt.setDouble(2, tutor.getHourlyRate());
            stmt.setInt(3, tutor.getRating());


            stmt.executeUpdate(); // Esegue il salvataggio nel DB
            DBSubjectDAO dbSubjectDAO = new DBSubjectDAO();
            dbSubjectDAO.insert(tutor);
            logger.info("Tutor salvato correttamente nel database!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Tutor tutor) throws DAOException {

    }

}
