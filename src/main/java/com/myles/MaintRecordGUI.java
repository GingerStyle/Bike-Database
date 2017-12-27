package com.myles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class MaintRecordGUI extends JFrame{

    //maintenance record panel components
    protected JPanel mainPanel;
    protected JSpinner dateSpinner;
    protected JComboBox<String> typeComboBox;
    protected JTextField servicedByTextField;
    protected JTextField partsTextField;
    protected JTextField descriptionTextField;
    protected JTextField mileageTextField;
    protected JButton addButton;
    protected JButton cancelButton;

    private String service = "Service";
    private String repair = "Repair";
    private String upgrade = "Upgrade";

    public MaintRecordGUI(){

        setContentPane(mainPanel);
        //setPreferredSize(new Dimension(, ));
        this.setTitle("Add Maintenance Record");
        pack();
        setVisible(true);

        typeComboBox.addItem(service);
        typeComboBox.addItem(repair);
        typeComboBox.addItem(upgrade);

        dateSpinner.setModel(new SpinnerDateModel());

        //when user clicks the add button it gets the input from all the fields and adds it to the database
        addButton.addActionListener(new ActionListener() {//todo finish this, and add input validation
            @Override
            public void actionPerformed(ActionEvent e) {
                //getting user input
                Date date = (Date)dateSpinner.getModel().getValue();
                Object selected = typeComboBox.getSelectedItem();
                String servicedBy = servicedByTextField.getText();
                String parts = partsTextField.getText();
                String description = descriptionTextField.getText();
                String mileage = mileageTextField.getText();
                //adding user input to the database

                //clearing text fields

            }
        });

        //when the user clicks the cancel button it clears the text fields and hides the window
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //hiding window
                setVisible(false);
            }
        });

    }

}
