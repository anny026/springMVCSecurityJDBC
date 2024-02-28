package anny.oct.helpdesk.dao;

import anny.oct.helpdesk.controller.TicketsController;
import anny.oct.helpdesk.model.Category;
import org.springframework.stereotype.Component;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class CategoryDAO {
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

    public Category getCategory(Integer id) {
        Category category = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM category WHERE id =?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {  //if или без if
                category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name_category"));}
            } catch(SQLException throwables){
                throwables.printStackTrace();
            }
        logger.log(Level.INFO, " ******** getCategory  "+category);
            return category;
        }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM category";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name_category"));
                categories.add(category);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        logger.log(Level.INFO, " ******** getCategories  "+categories);
        return categories;
    }





}



