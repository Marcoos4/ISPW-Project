package it.ispw.unilife.dao;

import it.ispw.unilife.dao.factory.DAOFactory;
import it.ispw.unilife.exception.DAOException;
import it.ispw.unilife.exception.UserNotFoundException;
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

public class DBTutorDAO implements TutorDAO{

    private static List<Tutor> tutors = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(DBTutorDAO.class.getName());

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

    @Override
    public User findTutorByUsername(String userName) {
        Tutor tutor = checkTutor(userName);
        if (tutor != null){
            return tutor;
        }
        String query = "SELECT username,hourlyrate,rating FROM tutor where username = ?";

        Connection conn = it.ispw.unilife.dao.factory.ConnectionFactory.getConnection();

        try(PreparedStatement statement = conn.prepareStatement(query)){
            statement.setString(1, userName);

            ResultSet resultSet = statement.executeQuery();
            User user = DAOFactory.getDAOFactory().getUserDAO().findUserForExternalLogin(userName);
            List<String> subjects = DAOFactory.getDAOFactory().getSubjectDAO().findSubjectByUsername(userName);
            if (resultSet.next()){
                tutor = new Tutor(user.getName(), user.getSurname(), subjects, resultSet.getDouble(2), resultSet.getInt(3), user.getUserName(), user.getPassword());
                tutors.add(tutor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

        return tutor;
    }

    private Tutor checkTutor(String username){
        for (Tutor tutor : tutors){
            if(tutor.checkAccount(username))
                return tutor;
        }
        return null;
    }

}
