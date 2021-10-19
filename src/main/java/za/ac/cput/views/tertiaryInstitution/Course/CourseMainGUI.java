package za.ac.cput.views.tertiaryInstitution.Course;

import za.ac.cput.views.MainGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourseMainGUI extends JFrame implements ActionListener {

    private JButton btnView, btnAdd, btnUpdate, btnDelete, btnBack;
    private JLabel lblHeading;
    private JPanel pN, pC, pS;
    private Font hFt;

    public CourseMainGUI(){
        super("Course Main Menu");

        pN = new JPanel();
        pC = new JPanel();
        pS = new JPanel();

        btnView = new JButton("View All Courses");
        btnAdd = new JButton("Add New Course");
        btnUpdate = new JButton("Update Course");
        btnDelete = new JButton("Delete Course");
        btnBack = new JButton("Back");

        lblHeading = new JLabel("Course", SwingConstants.CENTER);

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

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "View All Courses":
                GetCourse.main(null);
                this.setVisible(false);
                break;

            case "Add new Course":
                AddCourse.main(null);
                this.setVisible(false);
                break;

            case "Update Course":
                UpdateCourse.main(null);
                this.setVisible(false);
                break;

            case "Delete Course":
                DeleteCourse.main(null);
                this.setVisible(false);
                break;

            case "Back":
                MainGUI.main(null);
                this.setVisible(false);
                break;
        }
    }

    public static void main(String[] args) {
        new CourseMainGUI().setGUI();
    }
}

