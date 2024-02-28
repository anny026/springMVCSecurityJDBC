package anny.oct.helpdesk.dao;

import anny.oct.helpdesk.controller.TicketsController;
import anny.oct.helpdesk.model.Category;
import anny.oct.helpdesk.model.Comment;
import anny.oct.helpdesk.model.Ticket;
import anny.oct.helpdesk.model.User;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class CommentDAO {
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
            TicketsController.class.getName());
    public void saveComment(String newComment, int id_updatedTicket, User user) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into COMMENT (user_id ,\n" +
                            "text, date_comment,\n" +
                            "ticket_id)\n" +
                            "values (?,?,?,?)");
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, newComment);
            java.util.Date today = new java.util.Date();
            preparedStatement.setDate(3, new Date(today.getTime()) );
            preparedStatement.setInt(4,
                    id_updatedTicket);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Comment> getComments(int id_ticket) {
        List<Comment> comments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM comment WHERE ticket_id=?");
            preparedStatement.setInt(1, id_ticket);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getInt("id"));
                comment.setUser_id(resultSet.getInt("user_id"));
                comment.setText(resultSet.getString("text"));
                comment.setDate(resultSet.getDate("date_comment"));
                comment.setTicket_id(id_ticket);
                comments.add(comment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        logger.log(Level.INFO, "getComments........"+comments);
        return comments;
    }
}
