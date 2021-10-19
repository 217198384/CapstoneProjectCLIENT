package za.ac.cput.views;

/**
 * MainGUI.java
 * Front end GUI for Main Menu
 * @author Dylan Koevort 218088159
 * 17 October 2021
 */

import za.ac.cput.views.physical.BuildingMainGUI;
import za.ac.cput.views.physical.RoomMainGUI;
import za.ac.cput.views.student.StudentMainGUI;
import za.ac.cput.views.tertiaryInstitution.Course.CourseMainGUI;
import za.ac.cput.views.tertiaryInstitution.Department.DepartmentMainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame implements ActionListener {

    private JButton btnStudent, btnExamination, btnScheduledClass,
            btnCourse, btnDepartment, btnBuilding, btnRoom,
            btnLecturer, btnSubject, btnSemester, btnEnroll;
    private JLabel lblHeading;
    private JPanel pN, pC;
    public Font hFt;

    public MainGUI() {

        super("College Management Application");

        pN = new JPanel();
        pC = new JPanel();

        btnStudent = new JButton("Student");
        btnExamination = new JButton("Examination");
        btnScheduledClass = new JButton("Scheduled Class");
        btnCourse = new JButton("Course");
        btnDepartment = new JButton("Department");
        btnBuilding = new JButton("Building");
        btnRoom = new JButton("Room");
        btnLecturer = new JButton("Lecturer");
        btnSubject = new JButton("Subject");
        btnSemester = new JButton("Semester");
        btnEnroll = new JButton("Enroll");

        lblHeading = new JLabel("Main Menu", SwingConstants.CENTER);

        hFt = new Font("Arial", Font.BOLD, 30);
    }

    public void setGUI() {

        pN.setLayout(new FlowLayout(FlowLayout.CENTER));
        pC.setLayout(new GridLayout(11, 1));

        this.setPreferredSize(new Dimension(600, 600));

        lblHeading.setFont(hFt);

        pN.add(lblHeading);

        pC.add(btnBuilding);
        pC.add(btnCourse);
        pC.add(btnDepartment);
        pC.add(btnEnroll);
        pC.add(btnExamination);
        pC.add(btnLecturer);
        pC.add(btnRoom);
        pC.add(btnScheduledClass);
        pC.add(btnSemester);
        pC.add(btnStudent);
        pC.add(btnSubject);

        this.add(pN, BorderLayout.NORTH);
        this.add(pC, BorderLayout.CENTER);

        btnBuilding.addActionListener(this);
        btnCourse .addActionListener(this);
        btnDepartment.addActionListener(this);
        btnEnroll.addActionListener(this);
        btnExamination.addActionListener(this);
        btnLecturer.addActionListener(this);
        btnRoom.addActionListener(this);
        btnScheduledClass.addActionListener(this);
        btnSemester.addActionListener(this);
        btnStudent.addActionListener(this);
        btnSubject.addActionListener(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch(e.getActionCommand()) {
            case "Building":
                BuildingMainGUI.main(null);
                this.setVisible(false);
                break;
            case "Course":
                CourseMainGUI.main(null);
                this.setVisible(false);
                break;
            case "Department":
                DepartmentMainGUI.main(null);
                this.setVisible(false);
                break;
            case "Enroll":
                break;
            case "Examination":
                break;
            case "Lecturer":
                break;
            case "Room":
                RoomMainGUI.main(null);
                this.setVisible(false);
                break;
            case "Scheduled Class":
                break;
            case "Semester":
                break;
            case "Student":
                StudentMainGUI.main(null);
                this.setVisible(false);
                break;
            case "Subject":
                break;
        }
    }

    public static void main(String[] args) {
        new MainGUI().setGUI();
    }
}
