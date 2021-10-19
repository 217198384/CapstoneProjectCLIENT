package za.ac.cput.views.examination;

/**
 * Dinelle Kotze
 * 219089302
 * ExaminationMainGUI.java
 * This is the Main Examination GUI for the Examination entity.
 */

import za.ac.cput.views.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExaminationMainGUI extends JFrame implements ActionListener
{
    private JButton btnView, btnAdd, btnUpdate, btnDelete, btnBack;
    private JLabel lblHeading;
    private JPanel panelNorth, panelCenter, panelSouth;
    private Font headingFont;

    public ExaminationMainGUI()
    {
        super("Examination Main Menu");

        panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();

        btnView = new JButton("View All Examinations");
        btnAdd = new JButton("Add New Examination");
        btnUpdate = new JButton("Update Examination");
        btnDelete = new JButton("Delete Examination");
        btnBack = new JButton("Back");

        lblHeading = new JLabel("Examination", SwingConstants.CENTER);

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

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch (e.getActionCommand())
        {
            case "View All Examinations":
                ViewExaminationGUI.main(null);
                this.setVisible(false);
                break;
            case "Add New Examination":
                AddExaminationGUI.main(null);
                this.setVisible(false);
                break;
            case "Update Examination":
                UpdateExaminationGUI.main(null);
                this.setVisible(false);
                break;
            case "Delete Examination":
                DeleteExaminationGUI.main(null);
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
        new ExaminationMainGUI().setGUI();
    }
}
