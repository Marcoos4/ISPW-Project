package it.ispw.unilife.dao;

import it.ispw.unilife.exception.DAOException;
import it.ispw.unilife.model.Tutor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DBSubjectDAO implements DAO<Tutor>{

    private static List<String> subjects = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(DBUserDAO.class.getName());

    @Override
    public List<Tutor> getAll() {
        return List.of();
    }

    @Override
    public void insert(Tutor tutor) {
        String query = "INSERT INTO subjects (username, subject) VALUES (?, ?)";

        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            for(String subject : tutor.getSubjects()) {
                stmt.setString(1, tutor.getUserName());
                stmt.setString(2, subject);

                stmt.addBatch(); // Esegue il salvataggio nel DB
            }

            stmt.executeBatch();

            logger.info("Materie salvate correttamente nel database!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Tutor tutor) throws DAOException {

    }
}
