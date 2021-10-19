package za.ac.cput.views.scheduledClass;

import za.ac.cput.views.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScheduledClassMainGUI extends JFrame implements ActionListener
{
    private JButton btnView, btnAdd, btnUpdate, btnDelete, btnBack;
    private JLabel lblHeading;
    private JPanel panelNorth, panelCenter, panelSouth;
    private Font headingFont;

    public ScheduledClassMainGUI()
    {
        super("Scheduled Class Main Menu");

        panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();

        btnView = new JButton("View All Scheduled Classes");
        btnAdd = new JButton("Add New Scheduled Class");
        btnUpdate = new JButton("Update Scheduled Class");
        btnDelete = new JButton("Delete Scheduled Class");
        btnBack = new JButton("Back");

        lblHeading = new JLabel("Scheduled Class", SwingConstants.CENTER);

        headingFont = new Font("Arial", Font.BOLD, 30);
    }

    public void setGUI()
    {
        panelNorth.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelCenter.setLayout(new GridLayout(4, 1));
        panelSouth.setLayout(new GridLayout(1, 1));

        this.setPreferredSize(new Dimension(600, 600));

        lblHeading.setFont(headingFont);

        panelNorth.add(lblHeading);

        panelCenter.add(btnView);
        panelCenter.add(btnAdd);
        panelCenter.add(btnUpdate);
        panelCenter.add(btnDelete);

        panelSouth.add(btnBack);

        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);

        btnView.addActionListener(this);
        btnAdd .addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnBack.addActionListener(this);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch (e.getActionCommand())
        {
            case "View All Scheduled Classes":
                ViewScheduledClassGUI.main(null);
                this.setVisible(false);
                break;
            case "Add New Scheduled Class":
                AddScheduledClassGUI.main(null);
                this.setVisible(false);
                break;
            case "Update Scheduled Class":
                UpdateScheduledClassGUI.main(null);
                this.setVisible(false);
                break;
            case "Delete Scheduled Class":
                DeleteScheduledClassGUI.main(null);
                this.setVisible(false);
                break;
            case "Back":
                MainGUI.main(null);
                this.setVisible(false);
                break;
        }
    }

    public static void main(String[] args)
    {
        new ScheduledClassMainGUI().setGUI();
    }
}