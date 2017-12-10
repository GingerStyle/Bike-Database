package com.myles;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        //instantiating DBManager and GUIManager
        GUIManager gui = new GUIManager();
        DBManager dbManager = new DBManager();

        //creating driver class
        try {
            Class.forName(dbManager.JDBC_DRIVER);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Can't instantiate driver class; check you have drivers and classpath configured correctly?");
            cnfe.printStackTrace();
            System.exit(-1);  //No driver? Need to fix before anything else will work. So quit the program
        }

        //getting and displaying the title image and the bikes from the database to be ready to interact with
        LinkedList<Bike> bikes = dbManager.getBikes();
        gui.jListDisplay(bikes);
        gui.displayPicture("Title Image.png");
    }
}
