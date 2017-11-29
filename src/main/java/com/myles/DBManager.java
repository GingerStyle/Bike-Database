package com.myles;

import java.sql.*;

public class DBManager {

    static String db_url = "jdbc:mysql://localhost:3306/bikes";
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";//toDo get the folder of where to put the driver
    static String user = System.getenv("MYSQL_USER");
    static String password = System.getenv("MYSQL_PASSWORD");

    public void createTable(){
        try (Connection connection = DriverManager.getConnection(db_url, user, password);
            Statement statement = connection.createStatement()){

            String createTable = "CREATE TABLE IF NOT EXISTS bike (id int NOT NULL AUTO_INCREMENT, Brand varchar(20), Model varchar(20), Year varchar(4),Serial varchar(30), Color varchar(20), Mileage double, PRIMARY KEY(id) )";
            statement.executeUpdate(createTable);

        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    public void addBike(String brand, String model, String year, String serial, String color, double mileage){

        try (Connection connection = DriverManager.getConnection(db_url, user, password)){

            String insert = "INSERT INTO bikes (Brand, Model, Year, Serial, Color, Mileage) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement prepStatement = connection.prepareStatement(insert);
            prepStatement.setString(1, brand);
            prepStatement.setString(2, model);
            prepStatement.setString(3, year);
            prepStatement.setString(4, serial);
            prepStatement.setString(5, color);
            prepStatement.setDouble(6, mileage);
            prepStatement.executeUpdate();

            connection.close();
            prepStatement.close();

        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
    }

    public void deleteBike(){ //toDo

    }

    public void addBikeMileage(double addMileage){ //toDo

        try (Connection connection = DriverManager.getConnection(db_url, user, password)){

            String query = "SELECT * FROM bike WHERE ";//toDo finish this statement
            PreparedStatement prepStatement = connection.prepareStatement(query);
            ResultSet results = prepStatement.executeQuery();

            connection.close();
            prepStatement.close();
            results.close();

        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
    }

    public void getBikes(){ //toDo
        //gets information from the database, then creates bike objects which are then displayed in the Jlist by jListDisplay() in GUIManager
    }
}
