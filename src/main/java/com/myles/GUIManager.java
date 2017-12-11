package com.myles;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
    //maintenance panel elements
    protected JList maintenanceRecordList;
    protected JButton addRecordButton;
    protected JButton deleteRecordButton;
    protected JPanel maintenancePanel;

    private DefaultListModel listModel;

    private DBManager dbManager = new DBManager();

    public GUIManager(){

        setContentPane(mainPanel);
        setPreferredSize(new Dimension(900, 500));
        this.setTitle("Bike Maintenance Database");
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


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
                }else if (year.length() != 4){
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
                    displayErrorMessage("You must enter a color");
                    return;
                }

                //data validation for the mileageTextField
                String mileage = mileageTextField.getText();
                int decimal = 0;
                if(mileage.equals("")) {
                displayErrorMessage("You must enter the mileage of the bike");
                return;
                }else {
                    for (char x : mileage.toCharArray()){
                        if (!Character.isDigit(x) && x != '.'){
                            displayErrorMessage("The mileage can only contain numbers");
                            return;
                        }else if(x == '.'){
                            decimal++;
                        }
                    }
                }
                if (decimal > 1){
                    displayErrorMessage("You have more than one decimal point in Mileage");
                    return;
                }

                //adding bike to the database
                dbManager.addBike(brand,model,year,serial,color,Double.valueOf(mileage));
                //updating the displayed list with what is in the database
                LinkedList<Bike> bikes = dbManager.getBikes();
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
                    //making sure you intended to delete a bike
                    int sure = yesNoDialog("Are you sure you want to delete this bike?\n" + selectedBike);
                    if(sure == JOptionPane.YES_OPTION){
                        int id = selectedBike.getId();
                        dbManager.deleteBike(id);
                        LinkedList<Bike> bikes = dbManager.getBikes();
                        jListDisplay(bikes);
                        displayPicture("Title Image.png");
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
                int decimal = 0;
                if(index != -1){
                    Bike selectedBike = bikeList.getSelectedValue();
                    //making sure something was entered
                    String addMileage = mileageTextField.getText();
                    if(addMileage.equals("")){
                        displayErrorMessage("Enter the mileage in the Mileage text box");
                        return;
                    }
                    //making sure data entered is numeric or a decimal
                    for (char x : addMileage.toCharArray()){
                        if (!Character.isDigit(x) && x != '.'){
                            displayErrorMessage("The mileage can only contain numbers");
                            return;
                        }else if (x == '.'){
                            decimal++;
                        }
                    }
                    //making sure no more than one decimal is entered
                    if (decimal > 1){
                        displayErrorMessage("You have more than one decimal point in Mileage");
                        return;
                    }
                    int sure = yesNoDialog("Are you sure you entered the correct mileage?\nYou entered: " + mileageTextField.getText());
                    if(sure == JOptionPane.YES_OPTION){
                        int id = selectedBike.getId();
                        double mileage = selectedBike.getMileage();
                        mileage += Double.valueOf(addMileage);
                        dbManager.addBikeMileage(id, mileage);
                        LinkedList<Bike> bikes = dbManager.getBikes();
                        jListDisplay(bikes);
                        mileageTextField.setText("");
                        displayPicture("Title Image.png");
                        }
                }else{
                    displayErrorMessage("Please select a bike from the list to update");
                }
            }
        });

        //controls what happens when you press the add/change picture button
        addPhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //make sure something is selected
                int index = bikeList.getSelectedIndex();
                if(index != -1){
                    //allow the user to browse their files and select a picture
                    JFileChooser chooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, GIF, and PNG Images", "jpg", "gif", "png");
                    chooser.setFileFilter(filter);
                    int chosen = chooser.showOpenDialog(GUIManager.this);
                    //if they select something then save it to the database and display it
                    if(chosen == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = chooser.getSelectedFile();
                        String path = selectedFile.toString();
                        Bike selectedBike = bikeList.getSelectedValue();
                        selectedBike.setPath(path);//update object already in the jList then you don't have to update the whole list
                        int id = selectedBike.getId();
                        dbManager.setPicture(path, id);
                        displayPicture(path);
                    }
                }else{
                    displayErrorMessage("Please select a bike from the list to add a picture to it");
                }
            }
        });

        //displays the stored picture when you click on a bike in the list
        bikeList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Bike selectedBike = bikeList.getSelectedValue();
                if(selectedBike == null) {return;}
                String path = selectedBike.getPath();

                //checking that there is a file path in the database and that the file exists
                if(path == null){
                    pictureLabel.setIcon(null);
                    pictureLabel.setText("No photo on file");
                }else if(!new File(path).exists()){
                    pictureLabel.setIcon(null);
                    pictureLabel.setText("Missing file");
                }else{
                    displayPicture(path);
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

    //used to handle scaling and displays pictures
    protected void displayPicture(String filePath){
        //clear picture
        pictureLabel.setText("");
        pictureLabel.setIcon(null);
        //scale the picture
        ImageIcon bikeIcon = new ImageIcon(new ImageIcon(filePath).getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH));
        //display the picture
        pictureLabel.setIcon(bikeIcon);
    }

    //method used to display error messages
    private void displayErrorMessage(String message){
        JOptionPane.showMessageDialog(this, message);
    }

    //method used to display confirmation messages
    private int yesNoDialog(String message) {
        return JOptionPane.showConfirmDialog(this, message, null, JOptionPane.YES_NO_OPTION);
    }
}
