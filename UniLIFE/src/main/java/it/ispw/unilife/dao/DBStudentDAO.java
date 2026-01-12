package it.ispw.unilife.dao;

import it.ispw.unilife.dao.factory.ConnectionFactory;
import it.ispw.unilife.dao.factory.DAOFactory;
import it.ispw.unilife.exception.DAOException;
import it.ispw.unilife.exception.UserNotFoundException;
import it.ispw.unilife.model.Role;
import it.ispw.unilife.model.Student;
import it.ispw.unilife.model.Tutor;
import it.ispw.unilife.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DBStudentDAO implements StudentDAO{

    private static List<Student> students = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(DBStudentDAO.class.getName());


    @Override
    public List getAll() {
        return List.of();
    }

    @Override
    public void insert(Student student) {
        String query = "INSERT INTO student (username) VALUES (?)";

        Connection conn = it.ispw.unilife.dao.ConnectionFactory.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, student.getUserName());

            stmt.executeUpdate(); // Esegue il salvataggio nel DB
            logger.info("Studente salvato correttamente nel database!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Student student) throws DAOException {

    }

    @Override
    public Student findStudentByUsername(String userName) throws UserNotFoundException {
        // 1. Controllo cache
        Student cachedStudent = checkStudent(userName);
        if (cachedStudent != null) {
            return cachedStudent;
        }

        // 2. Query unica (JOIN)
        // Seleziona i dati dello user SOLO SE esiste una corrispondenza nella tabella student
        String query = "SELECT u.username, u.name, u.surname, u.password " +
                "FROM users u " +
                "JOIN student s ON u.username = s.username " +
                "WHERE u.username = ?";

        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, userName);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Student student = new Student(
                            resultSet.getString("name"),
                            resultSet.getString("surname"),
                            resultSet.getString("username"),
                            resultSet.getString("password")
                    );

                    students.add(student);

                    return student;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserNotFoundException();
        }

        throw new UserNotFoundException();
    }

    private Student checkStudent(String username){
        for (Student student : students){
            if(student.checkAccount(username))
                return student;
        }
        return null;
    }
}
