package anny.oct.helpdesk.dao.impl;

import anny.oct.helpdesk.controller.TicketsController;
import anny.oct.helpdesk.model.Role;
import anny.oct.helpdesk.model.User;
import anny.oct.helpdesk.dao.UserDao;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class UserDaoImpl implements UserDao {



    private static int PEOPLE_COUNT;
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

    @Override
    public User findUserByName(String email){
        User user= null;

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM userhd WHERE email=?");
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){  //без if  видно, если польз не сущ
            user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setRoleId(resultSet.getInt("role_id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));

                logger.log(Level.INFO, "**********  findUserByName");
                logger.log(Level.INFO, " ******** password  "+resultSet.getString("password"));
                logger.log(Level.INFO, "role id -   "+ resultSet.getInt("role_id"));
                logger.log(Level.INFO, " ******** Role.values  "+Role.values()[resultSet.getInt("role_id")-1]);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }


//    @Override
//    public Optional<User> findUserByName(String name){
//        if (name.equals("John")) {
//            return of(new User("John", "$2a$04$LacRHQK6n9yLacUUauHvYO/YnzEwnXFo1XyGPBTAcTeIXRDJeZyE6", ROLE_USER));
//        } else if (name.equals("Kate")) {
//            return of(new User("Kate", "$2a$04$LacRHQK6n9yLacUUauHvYO/YnzEwnXFo1XyGPBTAcTeIXRDJeZyE6", ROLE_ADMIN));
//        }else{
//            return empty();
//        }
//    }
}
