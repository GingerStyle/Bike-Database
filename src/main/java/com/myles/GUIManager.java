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
    protected JButton searchBikesButton;
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

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String brand = brandTextField.getText();
                if(brand.equals("")){
                    displayErrorMessage("You must enter a brand name");
                    return;
                }
                String model = modelTextField.getText();
                if(model.equals("")){
                    displayErrorMessage("You must enter a model name");
                    return;
                }
                String year = yearTextField.getText();
                if(year.equals("")){
                    displayErrorMessage("You must enter a year");
                    return;
                }
                String serial = serialTextField.getText();
                if(serial.equals("")){
                    int yesNo = yesNoDialog("Are you sure you don't want to enter a serial number?");
                    if(yesNo == 1){//todo make sure this works correctly
                        return;
                    }
                }
                String color = colorTextField.getText();
                if(color.equals("")){
                    int yesNo = yesNoDialog("Are you sure you don't want to enter a color?");
                    if(yesNo == 1){//todo make sure this works correctly
                        return;
                    }
                }
                String mileage = mileageTextField.getText();
                if(mileage.equals("")){
                    displayErrorMessage("You must enter the mileage of the bike");
                    return;
                }
                dbManager.addBike(brand, model, year, serial, color, Double.valueOf(mileage));
                LinkedList bikes = dbManager.getBikes();
                jListDisplay(bikes);
            }
        });

        deleteBikeButton.addActionListener(new ActionListener() {//toDo finish this
            public void actionPerformed(ActionEvent e) {
                int index = bikeList.getSelectedIndex();
                if(index != -1){
                    int sure = yesNoDialog("Are you sure you want to delete this bike?");
                    if(sure == JOptionPane.YES_OPTION){
                        //todo get bike to delete and send to DBManager.deleteBike
                        listModel.remove(index);
                    }
                }else {
                    displayErrorMessage("Please select a bike from the list to delete it");
                }
            }
        });

        addMileageButton.addActionListener(new ActionListener() {//toDo finish this
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

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
