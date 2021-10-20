package za.ac.cput.views.person.lecturer;

/**
 * LecturerMainGUI.java
 * Author: Shane Knoll (218279124)
 * Date: 20 October 2021
 */


import za.ac.cput.views.MainGUI;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.*;

public class LecturerMenuGUI extends JFrame implements ActionListener{
    private JButton btnDisplay;
    private JButton btnAdd, btnUpdate;
    private JButton btnDelete, btnExit;
    private JLabel lblHeading;
    private JPanel panelNorth, panelCenter, panelSouth;
    private Font font;

    public LecturerMenuGUI() {
        super("Lecturer Main Menu");

        panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();

        btnDisplay = new JButton("Display All Lecturers");
        btnAdd = new JButton("Add Lecturer");
        btnUpdate = new JButton("Update Lecturer");
        btnDelete = new JButton("Delete Lecturer");
        btnExit = new JButton("Exit");

        lblHeading = new JLabel("Select a Button for Lecturer", SwingConstants.CENTER);

        font= new Font("Arial", Font.BOLD, 30);
    }

    public void setGUI() {
        panelNorth.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelCenter.setLayout(new GridLayout(4, 1));
        panelSouth.setLayout(new GridLayout(1, 1));
        this.setPreferredSize(new Dimension(600, 600));

        lblHeading.setFont(font);

        panelNorth.add(lblHeading);
        panelCenter.add(btnAdd);
        panelCenter.add(btnDisplay);
        panelCenter.add(btnUpdate);
        panelCenter.add(btnDelete);

        panelSouth.add(btnExit);


        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);

        btnDisplay.addActionListener(this);
        btnAdd .addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnExit.addActionListener(this);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource()==btnAdd){
           dispose();
           new SaveLecturer().setGUI();
       }else if(e.getSource()==btnDisplay){
            dispose();
            new GetAllLecturer().setGUI();
        }else if(e.getSource()==btnUpdate){
           dispose();
           new UpdateLecturer().setGUI();
       }else if(e.getSource()==btnDelete){
           dispose();
           new DeleteLecturer().setGUI();
       }else if(e.getSource()==btnExit){
           this.dispose();
           new MainGUI().setGUI();
       }





    }

    public static void main(String[] args) {
        new LecturerMenuGUI().setGUI();
    }
}
