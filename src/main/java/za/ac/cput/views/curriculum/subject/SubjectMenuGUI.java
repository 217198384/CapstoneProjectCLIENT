package za.ac.cput.views.curriculum.subject;

import za.ac.cput.views.MainGUI;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import javax.swing.*;

public class SubjectMenuGUI extends JFrame implements ActionListener{
    private JButton btnDisplay;
    private JButton btnAdd, btnUpdate;
    private JButton btnDelete, btnExit;
    private JLabel lblHeading;
    private JPanel panelNorth, panelCenter, panelSouth;
    private Font font;

    public SubjectMenuGUI() {
        super("Subject Main Menu");

        panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();

        btnDisplay = new JButton("Display All Subjects");
        btnAdd = new JButton("Add Subject");
        btnUpdate = new JButton("Update Subject");
        btnDelete = new JButton("Delete Subject");
        btnExit = new JButton("Exit");

        lblHeading = new JLabel("Select a Button for Subject", SwingConstants.CENTER);

        font = new Font("Arial", Font.BOLD, 35);
    }

    public void setGUI() {
        panelNorth.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelCenter.setLayout(new GridLayout(4, 1));
        panelSouth.setLayout(new GridLayout(1, 1));

        this.setPreferredSize(new Dimension(500, 600));
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
            new SaveSubject().setGUI();
        }else if(e.getSource()==btnDisplay){
            dispose();
            new GetAllSubject().setGUI();
        }else if(e.getSource()==btnUpdate){
            dispose();
            new UpdateSubject().setGUI();
        }else if(e.getSource()==btnDelete){
            dispose();
            new DeleteSubject().setGUI();
        }else if(e.getSource()==btnExit){
            this.dispose();
            new MainGUI().setGUI();
        }





    }

    public static void main(String[] args) {
        new SubjectMenuGUI().setGUI();
    }
}
