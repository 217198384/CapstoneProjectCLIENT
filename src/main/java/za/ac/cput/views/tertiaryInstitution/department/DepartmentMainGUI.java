package za.ac.cput.views.tertiaryInstitution.department;

import za.ac.cput.views.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DepartmentMainGUI extends JFrame implements ActionListener {

    private JButton btnView, btnAdd, btnUpdate, btnDelete, btnBack;
    private JLabel lblHeading;
    private JPanel pN, pC, pS;
    private Font hFt;

    public DepartmentMainGUI(){
        super("Department Main Menu");

        pN = new JPanel();
        pC = new JPanel();
        pS = new JPanel();

        btnView = new JButton("View All Departments");
        btnAdd = new JButton("Add New Department");
        btnUpdate = new JButton("Update Department");
        btnDelete = new JButton("Delete Department");
        btnBack = new JButton("Back");

        lblHeading = new JLabel("Department", SwingConstants.CENTER);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "View All Departments":
                GetDepartment.main(null);
                this.setVisible(false);
                break;

           case "Add New Department":
                AddDepartment.main(null);
                this.setVisible(false);
                break;

            case "Update Department":
                UpdateDepartment.main(null);
                this.setVisible(false);
                break;

            case "Delete Department":
                DeleteDepartment.main(null);
                this.setVisible(false);
                break;

            case "Back":
                MainGUI.main(null);
                this.setVisible(false);
                break;
        }
    }

    public static void main(String[] args) {
        new DepartmentMainGUI().setGUI();
    }
}
