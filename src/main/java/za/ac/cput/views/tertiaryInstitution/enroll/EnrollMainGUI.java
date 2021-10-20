package za.ac.cput.views.tertiaryInstitution.enroll;

import za.ac.cput.views.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnrollMainGUI extends JFrame implements ActionListener {
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

    public EnrollMainGUI() {
        super("Enroll Main Menu");

        pN = new JPanel();
        pC = new JPanel();
        pS = new JPanel();

        btnView = new JButton("View All Enrollments");
        btnAdd = new JButton("Enroll New Student");
        btnUpdate = new JButton("Update Student Enrollment");
        btnDelete = new JButton("Delete Student Enrollment");
        btnBack = new JButton("Back");

        lblHeading = new JLabel("Enrollment", SwingConstants.CENTER);

        hFt = new Font("Arial", Font.BOLD, 30);
    }

    public static void main(String[] args) {
        new EnrollMainGUI().setGUI();
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
                GetEnrolls.main(null);
                this.setVisible(false);
                break;
            case "Enroll New Student":
                EnrollStudent.main(null);
                this.setVisible(false);
                break;
            case "Update Student Enrollment":
                UpdateEnrolls.main(null);
                this.setVisible(false);
                break;
            case "Delete Student Enrollment":
                DeleteEnrolls.main(null);
                this.setVisible(false);
                break;
            case "Back":
                MainGUI.main(null);
                this.setVisible(false);
                break;
        }
    }
}
