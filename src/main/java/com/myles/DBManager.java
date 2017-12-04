package com.myles;

import java.sql.*;
import java.util.LinkedList;

public class DBManager {

    static String db_url = "jdbc:sqlite:bikes.db";
    static final String JDBC_DRIVER = "org.sqlite.JDBC";

    //CREATE TABLE bike (id INTEGER PRIMARY KEY AUTOINCREMENT, Brand varchar(20), Model varchar(20), Year varchar(4), Serial varchar(30), Color varchar(20), Mileage double, Photo varchar(100))";

    public void addBike(String brand, String model, String year, String serial, String color, double mileage){

        try (Connection connection = DriverManager.getConnection(db_url)){

            String insert = "INSERT INTO bike (Brand, Model, Year, Serial, Color, Mileage) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement prepStatement = connection.prepareStatement(insert);
            prepStatement.setString(1, brand);
            prepStatement.setString(2, model);
            prepStatement.setString(3, year);
            prepStatement.setString(4, serial);
            prepStatement.setString(5, color);
            prepStatement.setDouble(6, mileage);
            prepStatement.executeUpdate();

        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
    }

    public void deleteBike(String bike){ //toDo

        try (Connection connection = DriverManager.getConnection(db_url)){

            String delete = "DELETE FROM bike WHERE = ?";//todo finish this statement
            PreparedStatement prepStatement = connection.prepareStatement(delete);
            prepStatement.setString(1, bike);
            prepStatement.executeUpdate();

        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
    }

    public void addBikeMileage(double addMileage){ //toDo

        try (Connection connection = DriverManager.getConnection(db_url)){

            String query = "SELECT * FROM bike WHERE ";//toDo finish this statement
            PreparedStatement prepStatement = connection.prepareStatement(query);


            ResultSet results = prepStatement.executeQuery();


        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
    }

    //gets information from the database, then creates bike objects which are then displayed in the Jlist by jListDisplay() in GUIManager
    public LinkedList<Bike> getBikes(){
        LinkedList<Bike> bikes = new LinkedList<>();
        try(Connection connection = DriverManager.getConnection(db_url);
            Statement statement = connection.createStatement()){

            String getAll = "SELECT * FROM bike;";
            ResultSet results = statement.executeQuery(getAll);

            while(results.next()){
                String brand = results.getString("Brand");
                String model = results.getString("Model");
                String year = results.getString("Year");
                String serial = results.getString("Serial");
                String color = results.getString("Color");
                double mileage = results.getDouble("Mileage");
                Bike bike = new Bike(brand, model, year, serial, color, mileage);
                bikes.add(bike);
            }

        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
        return bikes;
    }
}
