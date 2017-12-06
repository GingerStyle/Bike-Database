package com.myles;

import java.sql.*;
import java.util.LinkedList;

public class DBManager {

    static String db_url = "jdbc:sqlite:bikes.db";
    static final String JDBC_DRIVER = "org.sqlite.JDBC";

    //CREATE TABLE bike (id INTEGER PRIMARY KEY AUTOINCREMENT, Brand varchar(20), Model varchar(20), Year varchar(4), Serial varchar(30), Color varchar(20), Mileage double, Photo varchar(100))";

    //method that adds bike to the database
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

    //method that deletes bikes from the database
    public void deleteBike(int id){

        try (Connection connection = DriverManager.getConnection(db_url)){

            String delete = "DELETE FROM bike WHERE id = ?";
            PreparedStatement prepStatement = connection.prepareStatement(delete);
            prepStatement.setInt(1, id);
            prepStatement.executeUpdate();

        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
    }

    //method that updates the mileage to a bike in the database
    public void addBikeMileage(int id, double addMileage){

        try (Connection connection = DriverManager.getConnection(db_url)){

            String query = "UPDATE bike SET Mileage = ? WHERE id = ?";
            PreparedStatement prepStatement = connection.prepareStatement(query);
            prepStatement.setDouble(1, addMileage);
            prepStatement.setInt(2, id);
            prepStatement.execute();

        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
    }

    //gets information from the database, then creates bike objects which are then displayed in the jList by jListDisplay() in GUIManager
    public LinkedList<Bike> getBikes(){
        LinkedList<Bike> bikes = new LinkedList<>();
        try(Connection connection = DriverManager.getConnection(db_url);
            Statement statement = connection.createStatement()){

            String getAll = "SELECT * FROM bike ORDER BY Brand ASC, Model ASC, Year DESC";
            ResultSet results = statement.executeQuery(getAll);

            while(results.next()){
                int id = results.getInt("id");
                String brand = results.getString("Brand");
                String model = results.getString("Model");
                String year = results.getString("Year");
                String serial = results.getString("Serial");
                String color = results.getString("Color");
                double mileage = results.getDouble("Mileage");
                Bike bike = new Bike(id, brand, model, year, serial, color, mileage);
                bikes.add(bike);
            }

        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
        return bikes;
    }

    //method to get the picture to display
    public String getPicture(int id){
        String filePath = "";
        try(Connection connection = DriverManager.getConnection(db_url)){

            String getFilePath = "SELECT * FROM bike Where id = ?";
            PreparedStatement prepStatement = connection.prepareStatement(getFilePath);
            prepStatement.setInt(1, id);
            ResultSet results = prepStatement.executeQuery();

            while (results.next()){
                filePath = results.getString("Photo");
            }

        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
        return filePath;
    }

    //method to set the picture file path in the database
    public void setPicture(){//todo finish this

    }
}
