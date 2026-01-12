package it.ispw.unilife.dao;

import it.ispw.unilife.exception.DAOException;
import it.ispw.unilife.model.Role;
import it.ispw.unilife.model.Tutor;
import it.ispw.unilife.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DBSubjectDAO implements SubjectDAO{

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

    @Override
    public List<String> findSubjectByUsername(String userName) {
        List<String> subject = new ArrayList<>();

        String query = "SELECT username,subject FROM subjects WHERE username = ?";

        Connection conn = ConnectionFactory.getConnection();

        try(PreparedStatement statement = conn.prepareStatement(query)){
            statement.setString(1, userName);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                subject.add(resultSet.getString(2));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return subject;
    }
}
