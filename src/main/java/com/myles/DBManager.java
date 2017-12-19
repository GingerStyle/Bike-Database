package com.myles;

import java.sql.*;
import java.util.LinkedList;

public class DBManager {

    static String db_url = "jdbc:sqlite:bikes.db";
    static final String JDBC_DRIVER = "org.sqlite.JDBC";

    //Schema for the bike table: bike (id INTEGER PRIMARY KEY AUTOINCREMENT, Brand varchar(20), Model varchar(20), Year varchar(4), Serial varchar(30), Color varchar(20), Mileage double, Photo varchar(100));
    //CREATE TABLE IF NOT EXISTS ? (Date varchar(10), Type varchar(7), Serviced_By varchar(50), Parts varchar(), Description varchar(200))
    //Type can be service or repair hence 7 character length

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
            //delete bike from database
            String delete = "DELETE FROM bike WHERE id = ?";
            PreparedStatement prepStatement = connection.prepareStatement(delete);
            prepStatement.setInt(1, id);
            prepStatement.executeUpdate();

            //delete maintenance record table associated with the bike
            String dropTable = "DROP TABLE IF EXISTS ?";
            prepStatement = connection.prepareStatement(dropTable);
            prepStatement.setString(1, String.valueOf(id));
            prepStatement.execute();

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
                String path = results.getString("Photo");
                Bike bike = new Bike(id, brand, model, year, serial, color, mileage, path);
                bikes.add(bike);
            }

        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
        return bikes;
    }

    //method to set the photo file path in the database
    public void setPicture(String path, int id){

        try (Connection connection = DriverManager.getConnection(db_url)){

            String query = "UPDATE bike SET Photo = ? WHERE id = ?";
            PreparedStatement prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, path);
            prepStatement.setInt(2, id);
            prepStatement.execute();

        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
    }

    //get maintenance records from the database based on which bike was selected. Then creates Records objects and sends them to
    //GUIManager.maintenanceListDisplay() to display them
    public LinkedList<Records> getRecords(int id){
        LinkedList<Records> records = new LinkedList<>();
        try(Connection connection = DriverManager.getConnection(db_url)){

            String getAll = "SELECT * FROM ? ORDER BY Date ASC";
            PreparedStatement prepStatement = connection.prepareStatement(getAll);
            prepStatement.setString(1, String.valueOf(id));
            ResultSet results = prepStatement.executeQuery(getAll);

            while(results.next()){
                String date = results.getString("Date");
                String type = results.getString("Type");
                String servicedBy = results.getString("Serviced_By");
                String parts = results.getString("Parts");
                String description = results.getString("Description");
                Records record = new Records(date, type, servicedBy, parts, description);
                records.add(record);
            }

        }catch(SQLException sqle){
            sqle.printStackTrace();
        }

        return records;
    }

    //method to add maintenance records to database
    public void addRecord(int id, String date, String type, String servicedBy, String parts, String description){
        try(Connection connection = DriverManager.getConnection(db_url)){
            //create table for record if it doesn't already exist
            String createTable = "CREATE TABLE IF NOT EXISTS ? (Date varchar(10), Type varchar(7), Serviced_By varchar(50), Parts varchar(), Description varchar(200))";
            PreparedStatement prepStatement = connection.prepareStatement(createTable);
            prepStatement.setString(1, String.valueOf(id));
            prepStatement.execute();

            //insert record into the table
            String insert = "INSERT INTO ? (Date, Type, Serviced_By, Parts, Description) VALUES (?, ?, ?, ?, ?)";
            prepStatement = connection.prepareStatement(insert);
            prepStatement.setString(1, String.valueOf(id));
            prepStatement.setString(2, date);
            prepStatement.setString(3, type);
            prepStatement.setString(4, servicedBy);
            prepStatement.setString(5, parts);
            prepStatement.setString(6, description);
            prepStatement.execute();

        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
    }
}
