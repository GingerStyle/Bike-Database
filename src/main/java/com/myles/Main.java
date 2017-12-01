package com.myles;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        GUIManager gui = new GUIManager();
        DBManager dbManager = new DBManager();

        try {
            Class.forName(dbManager.JDBC_DRIVER);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Can't instantiate driver class; check you have drivers and classpath configured correctly?");
            cnfe.printStackTrace();
            System.exit(-1);  //No driver? Need to fix before anything else will work. So quit the program
        }

        dbManager.openDB();
        LinkedList bikes = dbManager.getBikes();
        gui.jListDisplay(bikes);
    }
}
