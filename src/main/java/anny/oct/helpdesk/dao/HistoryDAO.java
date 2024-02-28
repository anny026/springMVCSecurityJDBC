package anny.oct.helpdesk.dao;

import anny.oct.helpdesk.controller.TicketsController;
import anny.oct.helpdesk.model.Comment;
import anny.oct.helpdesk.model.History;
import anny.oct.helpdesk.model.User;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class HistoryDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "pg88";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Logger logger
            = Logger.getLogger(
            HistoryDAO.class.getName());
    public void saveHistory(String action, String description, int id_updatedTicket, User user) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO history (ticket_id,\n" +
                            "date_action,\n" +
                            "action,\n" +
                            "user_id,\n" +
                            "description)\n" +
                            "VALUES (?,?,?,?,?)");
            preparedStatement.setInt(1, id_updatedTicket);
            java.util.Date today = new java.util.Date();
            preparedStatement.setDate(2, new Date(today.getTime()));
            preparedStatement.setString(3, action);
            preparedStatement.setInt(4, user.getId());
            preparedStatement.setString(5, description);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<History> getHistory(int id_ticket) {
        List<History> histories = new ArrayList<>();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM history WHERE ticket_id=?");
            preparedStatement.setInt(1, id_ticket);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                History history = new History();
                history.setId(resultSet.getInt("id"));
                history.setTicket_id(id_ticket);
                history.setDate(resultSet.getDate("date_action"));
                history.setAction(resultSet.getString("action"));
                history.setUser_id(resultSet.getInt("user_id"));
                history.setDescription(resultSet.getString("description"));
                histories.add(history);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        logger.log(Level.INFO, "getHistory........"+histories);
        return histories;
    }
}
