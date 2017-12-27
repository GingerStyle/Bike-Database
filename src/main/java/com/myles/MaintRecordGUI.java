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
    protected JTextField ServicedByTextField;
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

        addButton.addActionListener(new ActionListener() {//todo finish this
            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = (Date)dateSpinner.getModel().getValue();

            }
        });

        cancelButton.addActionListener(new ActionListener() {//todo finish this
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

}
