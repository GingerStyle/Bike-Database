package com.myles;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class GUIManager extends JFrame{

    //main panel elements
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
    //picture panel elements
    protected JPanel picturePanel;
    protected JLabel pictureLabel;
    protected JButton addPhotoButton;

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
                //data validation for brandTextField
                String brand = brandTextField.getText();
                if (brand.equals("")) {
                    displayErrorMessage("You must enter a brand name");
                    return;
                }

                //data validation for the modelTextField
                String model = modelTextField.getText();
                if (model.equals("")) {
                    displayErrorMessage("You must enter a model name");
                    return;
                }

                //data validation for the yearTextField
                String year = yearTextField.getText();
                if (year.equals("")) {
                    displayErrorMessage("You must enter a year");
                    return;
                }
                if (year.length() != 4){
                    displayErrorMessage("You must use a four digit year");
                    return;
                }else {
                    for (char x : year.toCharArray()){
                        if (!Character.isDigit(x)){
                            displayErrorMessage("The year must contain only numbers");
                            return;
                        }
                    }
                }

                //data validation for the serialTextField
                String serial = serialTextField.getText();
                if (serial.equals("")) {
                    displayErrorMessage("You must enter a serial number");
                    return;
                }

                //data validation for the colorTextField
                String color = colorTextField.getText();
                if (color.equals("")) {
                    displayErrorMessage("you must enter a color");
                    return;
                }

                //data validation for the mileageTextField
                String mileage = mileageTextField.getText();
                if(mileage.equals("")) {
                displayErrorMessage("You must enter the mileage of the bike");
                return;
                }else{
                    for (char x : mileage.toCharArray()){
                        if (!Character.isDigit(x) && x != '.'){
                            displayErrorMessage("The mileage can only contain numbers");
                            return;
                        }
                    }
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
                        int id = selectedBike.getId();
                        dbManager.deleteBike(id);
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
                    String addMileage = mileageTextField.getText();
                    for (char x : addMileage.toCharArray()){
                        if (!Character.isDigit(x) && x != '.'){
                            displayErrorMessage("The mileage can only contain numbers");
                            return;
                        }
                    }
                    int sure = yesNoDialog("Are you sure you entered the correct mileage?");
                    if(sure == JOptionPane.YES_OPTION){
                        int id = selectedBike.getId();
                        double mileage = selectedBike.getMileage();
                        mileage += Double.valueOf(addMileage);
                        dbManager.addBikeMileage(id, mileage);
                        LinkedList<Bike> bikes = dbManager.getBikes();
                        jListDisplay(bikes);
                        mileageTextField.setText("");
                        }
                }else{
                    displayErrorMessage("Please select a bike from the list to update");
                }
            }
        });

        //controls what happens when you press the add/change picture button
        addPhotoButton.addActionListener(new ActionListener() {//todo finish this
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = bikeList.getSelectedIndex();
                if(index != -1){

                }else{
                    displayErrorMessage("Please select a bike from the list to add a picture to it");
                }
            }
        });

        //displays the stored picture when you click on a bike in the list
        bikeList.addListSelectionListener(new ListSelectionListener() {//todo add check if file is not found
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Bike selectedBike = bikeList.getSelectedValue();
                int id = selectedBike.getId();
                String path = dbManager.getPicture(id);
                if(path == null){
                    pictureLabel.setText("No photo on file");
                }else{
                    ImageIcon bikeIcon = new ImageIcon(path);
                    pictureLabel.setIcon(bikeIcon);
                }
            }
        });

    }

    //gets list of bike objects from DBManager.getBikes() and displays them
    protected void jListDisplay(LinkedList<Bike> bikes){
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
