package za.ac.cput.views.semester;

import za.ac.cput.views.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SemesterMainGUI extends JFrame implements ActionListener {
    private final JButton btnView;
    private final JButton btnAdd;
    private final JButton btnUpdate;
    private final JButton btnDelete;
    private final JButton btnBack;
    private final JLabel lblHeading;
    private final JPanel pN;
    private final JPanel pC;
    private final JPanel pS;
    private final Font hFt;

    public SemesterMainGUI() {
        super("Semester Main Menu");

        pN = new JPanel();
        pC = new JPanel();
        pS = new JPanel();

        btnView = new JButton("View All Semester");
        btnAdd = new JButton("Add New Semester");
        btnUpdate = new JButton("Update Semester");
        btnDelete = new JButton("Delete Semester");
        btnBack = new JButton("Back");

        lblHeading = new JLabel("Semester", SwingConstants.CENTER);

        hFt = new Font("Arial", Font.BOLD, 30);
    }

    public static void main(String[] args) {
        new SemesterMainGUI().setGUI();
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
        btnAdd.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnBack.addActionListener(this);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "View All Enrollments":
                GetSemester.main(null);
                this.setVisible(false);
                break;
            case "Add New Semester":
                AddSemester.main(null);
                this.setVisible(false);
                break;
            case "Update Semester":
                UpdateSemeter.main(null);
                this.setVisible(false);
                break;
            case "Delete Semester":
                DeleteSemester.main(null);
                this.setVisible(false);
                break;
            case "Back":
                MainGUI.main(null);
                this.setVisible(false);
                break;
        }
    }
}
