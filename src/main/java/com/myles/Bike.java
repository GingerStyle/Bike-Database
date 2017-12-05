package com.myles;

public class Bike {

    private String brand;
    private String model;
    private String year;
    private String serial;
    private String color;
    private double mileage;

    public Bike(String brand, String model, String year, String serial, String color, double mileage){
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.serial = serial;
        this.color = color;
        this.mileage = mileage;
    }

    public String getSerial() { return serial; }

    public double getMileage() { return mileage; }

    @Override
    public String toString(){
        String string = year + " " + brand + " " + model + " Color: " + color + " Serial: " + serial + " Mileage: " + mileage;
        return string;
    }
}
