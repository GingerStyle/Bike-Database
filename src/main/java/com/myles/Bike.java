package com.myles;

public class Bike {

    private int id;
    private String brand;
    private String model;
    private String year;
    private String serial;
    private String color;
    private double mileage;
    private String path;

    public Bike(int id, String brand, String model, String year, String serial, String color, double mileage, String path){
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.serial = serial;
        this.color = color;
        this.mileage = mileage;
        this.path = path;
    }

    //get methods
    public double getMileage() { return mileage; }

    public int getId() { return id; }

    public String getPath() { return path; }

    //set methods
    public void setPath(String path) { this.path = path; }

    @Override
    public String toString(){
        String string = year + " " + brand + " " + model + " Color: " + color + " Serial: " + serial + " Mileage: " + mileage;
        return string;
    }
}
