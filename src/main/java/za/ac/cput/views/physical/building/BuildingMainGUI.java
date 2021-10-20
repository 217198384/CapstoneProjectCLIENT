/**
 * author: Llewelyn Klaase
 * student no: 216267072
 */
package za.ac.cput.views.physical.building;

import za.ac.cput.views.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuildingMainGUI extends JFrame implements ActionListener {

    private JButton btnView, btnAdd, btnUpdate, btnDelete, btnBack;
    private JLabel lblHeading;
    private JPanel pN, pC, pS;
    private Font hFt;

    public BuildingMainGUI() {

        super("Buildings Main Menu");

        pN = new JPanel();
        pC = new JPanel();
        pS = new JPanel();

        btnView = new JButton("View All Buildings");
        btnAdd = new JButton("Add New Buildings");
        btnUpdate = new JButton("Update Buildings");
        btnDelete = new JButton("Delete Buildings");
        btnBack = new JButton("Back");

        lblHeading = new JLabel("Buildings", SwingConstants.CENTER);

        hFt = new Font("Arial", Font.BOLD, 30);
    }

    public void setGUI() {

        pN.setLayout(new FlowLayout(FlowLayout.CENTER));
        pC.setLayout(new GridLayout(4, 1));
        pS.setLayout(new GridLayout(1, 1));

        this.setPreferredSize(new Dimension(600, 600));

        lblHeading.setFont(hFt);

        pN.add(lblHeading);

        pC.add(btnView);
        pC.add(btnAdd);
        pC.add(btnUpdate);
        pC.add(btnDelete);

        pS.add(btnBack);


        this.add(pN, BorderLayout.NORTH);
        this.add(pC, BorderLayout.CENTER);
        this.add(pS, BorderLayout.SOUTH);

        btnView.addActionListener(this);
        btnAdd .addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnBack.addActionListener(this);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "View All Buildings":
                GetBuildingGUI.main(null);
                this.setVisible(false);
                break;
            case "Add New Buildings":
                AddBuildingGUI.main(null);
                this.setVisible(false);
                break;
            case "Update Buildings":
                UpdateBuildingGUI.main(null);
                this.setVisible(false);
                break;
            case "Delete Buildings":
                DeleteBuildingGUI.main(null);
                this.setVisible(false);
                break;
            case "Back":
                MainGUI.main(null);
                this.setVisible(false);
                break;
        }
    }

    public static void main(String[] args) {

        new BuildingMainGUI().setGUI();
    }
}
