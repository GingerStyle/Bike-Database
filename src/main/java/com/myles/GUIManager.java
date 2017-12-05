package com.myles;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class GUIManager extends JFrame{

    protected JTextField brandTextField;
    protected JTextField modelTextField;
    protected JTextField yearTextField;
    protected JTextField serialTextField;
    protected JTextField colorTextField;
    protected JTextField mileageTextField;
    protected JButton addButton;
    protected JButton deleteBikeButton;
    protected JButton addMileageButton;
    protected JList<Bike> bikeList;
    protected JPanel mainPanel;

    DefaultListModel listModel;

    DBManager dbManager = new DBManager();

    public GUIManager(){

        setContentPane(mainPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //setPreferredSize(new Dimension(500, 500));

        listModel = new DefaultListModel<Bike>();
        bikeList.setModel(listModel);
        bikeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //controls what happens when you press the add bike button
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //making sure all fields are filled
                String brand = brandTextField.getText();
                if (brand.equals("")) {
                    displayErrorMessage("You must enter a brand name");
                    return;
                }
                String model = modelTextField.getText();
                if (model.equals("")) {
                    displayErrorMessage("You must enter a model name");
                    return;
                }
                String year = yearTextField.getText();
                if (year.equals("")) {
                    displayErrorMessage("You must enter a year");
                    return;
                }
                String serial = serialTextField.getText();
                if (serial.equals("")) {
                    displayErrorMessage("You must enter a serial number");
                    return;
                }
                String color = colorTextField.getText();
                if (color.equals("")) {
                    displayErrorMessage("you must enter a color");
                    return;
                }
                String mileage = mileageTextField.getText();
                if(mileage.equals("")) {//todo make sure numeric data was entered
                displayErrorMessage("You must enter the mileage of the bike");
                return;
                }
                //adding bike to the database
                dbManager.addBike(brand,model,year,serial,color,Double.valueOf(mileage));
                //updating the displayed list with what is in the database
                LinkedList bikes = dbManager.getBikes();
                jListDisplay(bikes);
                //clearing the jTextFields
                brandTextField.setText("");
                modelTextField.setText("");
                yearTextField.setText("");
                serialTextField.setText("");
                colorTextField.setText("");
                mileageTextField.setText("");
            }
        });

        //controls what happens when you press the delete button
        deleteBikeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //making sure that something was selected
                int index = bikeList.getSelectedIndex();
                if(index != -1){
                    Bike selectedBike = bikeList.getSelectedValue();
                    int sure = yesNoDialog("Are you sure you want to delete this bike?");
                    if(sure == JOptionPane.YES_OPTION){
                        String serial = selectedBike.getSerial();
                        dbManager.deleteBike(serial);
                        LinkedList<Bike> bikes = dbManager.getBikes();
                        jListDisplay(bikes);
                    }
                }else {
                    displayErrorMessage("Please select a bike from the list to delete it");
                }
            }
        });

        //controls what happens when you press the add mileage button
        addMileageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //making sure something was selected
                int index = bikeList.getSelectedIndex();
                if(index != -1){
                    Bike selectedBike = bikeList.getSelectedValue();
                    //making sure data entered is numeric
                    String addMileage = mileageTextField.getText();//todo make sure numeric data was entered

                    int sure = yesNoDialog("Are you sure you entered the correct mileage?");
                    if(sure == JOptionPane.YES_OPTION){
                        String serial = selectedBike.getSerial();
                        double mileage = selectedBike.getMileage();
                        mileage += Double.valueOf(addMileage);
                        dbManager.addBikeMileage(mileage, serial);
                        LinkedList<Bike> bikes = dbManager.getBikes();
                        jListDisplay(bikes);
                        mileageTextField.setText("");
                    }
                }else{
                    displayErrorMessage("Please select a bike from the list to update");
                }
            }
        });

    }

    //gets list of bike objects from DBManager.getbikes() and displays them
    protected void jListDisplay(LinkedList<Bike> bikes){//todo make it display bikes in order by year
        listModel.clear();
        for(Bike x : bikes){
            listModel.addElement(x);
        }
    }

    protected void displayErrorMessage(String message){
        JOptionPane.showMessageDialog(this, message);
    }

    protected int yesNoDialog(String message) {
        return JOptionPane.showConfirmDialog(this, message, null, JOptionPane.YES_NO_OPTION);
    }
}
