package anny.oct.helpdesk.dao;

import anny.oct.helpdesk.controller.TicketsController;
import anny.oct.helpdesk.model.Ticket;
import anny.oct.helpdesk.model.User;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class TicketDAO {

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

    public Ticket getTicket(Integer id) {
        Ticket ticket = null;

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM ticket WHERE id =?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){  //if или без if
            ticket = new Ticket();
            ticket.setId(resultSet.getInt("id"));
            ticket.setName_ticket(resultSet.getString("name_ticket"));
            ticket.setDescription(resultSet.getString("description"));
            ticket.setCreated_on(resultSet.getString("created_on"));
            ticket.setDesired_resolution_date(resultSet.getDate("desired_resolution_date"));
            ticket.setAssignee_id(resultSet.getInt("assignee_id"));
            ticket.setOwner_id(resultSet.getInt("owner_id"));
            ticket.setState_id(resultSet.getInt("state_id"));
            ticket.setCategory_id(resultSet.getInt("category_id"));
            ticket.setUrgency_id(resultSet.getInt("urgency_id"));
            ticket.setApprover_id(resultSet.getInt("approver_id"));}
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ticket;
    }
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Ticket";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(resultSet.getInt("id"));
                ticket.setName_ticket(resultSet.getString("name_ticket"));
                ticket.setDescription(resultSet.getString("description"));
                ticket.setCreated_on(resultSet.getString("created_on"));
                ticket.setDesired_resolution_date(resultSet.getDate("desired_resolution_date"));
                ticket.setAssignee_id(resultSet.getInt("assignee_id"));
                ticket.setOwner_id(resultSet.getInt("owner_id"));
                ticket.setState_id(resultSet.getInt("state_id"));
                ticket.setCategory_id(resultSet.getInt("category_id"));
                ticket.setUrgency_id(resultSet.getInt("urgency_id"));
                ticket.setApprover_id(resultSet.getInt("approver_id"));
                tickets.add(ticket);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tickets;
    }


    public List<Ticket> getAllTicketsEmployee(User user) {
        List<Ticket> tickets = new ArrayList<>();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM ticket WHERE owner_id =?");
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(resultSet.getInt("id"));
                ticket.setName_ticket(resultSet.getString("name_ticket"));
                ticket.setDescription(resultSet.getString("description"));
                ticket.setCreated_on(resultSet.getString("created_on"));
                ticket.setDesired_resolution_date(resultSet.getDate("desired_resolution_date"));
                ticket.setAssignee_id(resultSet.getInt("assignee_id"));
                ticket.setOwner_id(resultSet.getInt("owner_id"));
                ticket.setState_id(resultSet.getInt("state_id"));
                ticket.setCategory_id(resultSet.getInt("category_id"));
                ticket.setUrgency_id(resultSet.getInt("urgency_id"));
                ticket.setApprover_id(resultSet.getInt("approver_id"));
                tickets.add(ticket);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tickets;
    }


    public List<Ticket> getAllTicketsManager(User user) {
        List<Ticket> tickets = new ArrayList<>();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from ticket \n" +
                            "join userhd \n" +
                            "on ticket.owner_id=userhd.id \n" +
                            "where userhd.role_id=1 and ticket.state_id=2 \n" +
                            "or ticket.owner_id=?\n" +
                            "or ticket.approver_id=? and ticket.state_id>2");
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(resultSet.getInt("id"));
                ticket.setName_ticket(resultSet.getString("name_ticket"));
                ticket.setDescription(resultSet.getString("description"));
                ticket.setCreated_on(resultSet.getString("created_on"));
                ticket.setDesired_resolution_date(resultSet.getDate("desired_resolution_date"));
                ticket.setAssignee_id(resultSet.getInt("assignee_id"));
                ticket.setOwner_id(resultSet.getInt("owner_id"));
                ticket.setState_id(resultSet.getInt("state_id"));
                ticket.setCategory_id(resultSet.getInt("category_id"));
                ticket.setUrgency_id(resultSet.getInt("urgency_id"));
                ticket.setApprover_id(resultSet.getInt("approver_id"));
                tickets.add(ticket);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tickets;
    }

    public List<Ticket> getAllTicketsEngineer(User user) {
        List<Ticket> tickets = new ArrayList<>();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from ticket\n" +
                            "left join userhd\n" +
                            "on ticket.assignee_id=userhd.id \n" +
                            "where state_id=3 or \n" +
                            "ticket.assignee_id=? and ticket.state_id=5 \n" +
                            "or ticket.assignee_id=? and ticket.state_id=6");

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(resultSet.getInt("id"));
                ticket.setName_ticket(resultSet.getString("name_ticket"));
                ticket.setDescription(resultSet.getString("description"));
                ticket.setCreated_on(resultSet.getString("created_on"));
                ticket.setDesired_resolution_date(resultSet.getDate("desired_resolution_date"));
                ticket.setAssignee_id(resultSet.getInt("assignee_id"));
                ticket.setOwner_id(resultSet.getInt("owner_id"));
                ticket.setState_id(resultSet.getInt("state_id"));
                ticket.setCategory_id(resultSet.getInt("category_id"));
                ticket.setUrgency_id(resultSet.getInt("urgency_id"));
                ticket.setApprover_id(resultSet.getInt("approver_id"));
                tickets.add(ticket);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tickets;
    }
    public int save(Ticket newTicket, int id_state, User user) {
        int ticketId = -1;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO ticket (name_ticket, description,\n" +
                            "created_on ,\n" +
                            "desired_resolution_date ,\n" +
                            "assignee_id ,\n" +
                            "owner_id ,\n" +
                            "state_id , \n" +
                            "category_id ,\n" +
                            "urgency_id ,\n" +
                            "approver_id) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, newTicket.getName_ticket());
            preparedStatement.setString(2, newTicket.getDescription());
            java.util.Date today = new java.util.Date();
            preparedStatement.setDate(3, new Date(today.getTime()) );
            preparedStatement.setDate(4,
                     newTicket.getDesired_resolution_date());
            preparedStatement.setInt(5, 0);
            preparedStatement.setInt(6, user.getId());   //owner создатель
            preparedStatement.setInt(7, id_state);  //state
            preparedStatement.setInt(8, newTicket.getCategory_id());  //category
            preparedStatement.setInt(9, newTicket.getUrgency_id());   //urgency
            preparedStatement.setInt(10, 0);   //approver
             preparedStatement.executeUpdate();
            ResultSet gk = preparedStatement.getGeneratedKeys();
            if(gk.next()) {
                // Получаем поле contact_id
                ticketId = gk.getInt("id");
                logger.log(Level.INFO, "ticketId  in save........"+ticketId);
            }
            gk.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ticketId;
    }

    public void update(int id, Ticket updatedTicket, int id_status) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE ticket SET name_ticket=?, Description=?, " +
                            "desired_resolution_date=?," +
                            "state_id=?, category_id=?, urgency_id=?  WHERE id=?");
            preparedStatement.setString(1, updatedTicket.getName_ticket());
            preparedStatement.setString(2, updatedTicket.getDescription());
            preparedStatement.setDate(3,
                    updatedTicket.getDesired_resolution_date());
            preparedStatement.setInt(4, id_status);      //state
            preparedStatement.setInt(5, updatedTicket.getCategory_id());
            preparedStatement.setInt(6, updatedTicket.getUrgency_id());
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateStatusOwnTicket(int id, int id_state) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE ticket SET state_id=? WHERE id=?");
            preparedStatement.setInt(1, id_state);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

        public void updateStatusByManager(int id, int id_state, int id_manager) {
            try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE ticket SET state_id=?, approver_id=? WHERE id=?");
                preparedStatement.setInt(1, id_state);
                preparedStatement.setInt(2, id_manager);
                preparedStatement.setInt(3, id);
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
    }

    public void updateStatusByEngineer(int id, int id_state, int id_engineer) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE ticket SET state_id=?, assignee_id=? WHERE id=?");
            preparedStatement.setInt(1, id_state);
            preparedStatement.setInt(2, id_engineer);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
