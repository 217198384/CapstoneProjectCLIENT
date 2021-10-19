package za.ac.cput.views.tertiaryInstitution.Course;

import com.google.gson.Gson;
import okhttp3.*;
import za.ac.cput.entity.tertiaryInstitution.Course;
import za.ac.cput.factory.tertiaryInstitution.CourseFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AddCourse extends JFrame implements ActionListener {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();

    private JLabel lblCourseCode, lblTitle, lblDepartmentId, lblCredit, lblDuration, lblFullTime;
    private JTextField txtCourseCode, txtTitle, txtDepartmentId, txtCredit, txtDuration, txtFullTime;
    private JButton btnSave, btnCancel;
    public JPanel pN, pS;

    public AddCourse(){
        super("Add new Course");

        pN = new JPanel();
        pS = new JPanel();

        lblCourseCode = new JLabel("Course code: ");
        lblTitle = new JLabel("Title: ");
        lblDepartmentId = new JLabel("Department: ");
        lblCredit = new JLabel("Credit: ");
        lblDuration = new JLabel("Duration: ");
        lblFullTime = new JLabel("Full Time: ");

        txtCourseCode = new JTextField(30);
        txtTitle = new JTextField(30);
        txtDepartmentId = new JTextField(30);
        txtCredit = new JTextField(30);
        txtDuration = new JTextField(30);
        txtFullTime = new JTextField(2);

        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");
    }

    public void setGUI() {
        pN.setLayout(new GridLayout(0,2));
        pS.setLayout(new GridLayout(1,2));

        pN.add(lblCourseCode);
        pN.add(txtCourseCode);

        pN.add(lblTitle);
        pN.add(txtTitle);

        pN.add(lblDepartmentId);
        pN.add(txtDepartmentId);

        pN.add(lblCredit);
        pN.add(txtCredit);

        pN.add(lblDuration);
        pN.add(txtDuration);

        pN.add(lblFullTime);
        pN.add(txtFullTime);

        pS.add(btnSave);
        pS.add(btnCancel);

        this.add(pN, BorderLayout.NORTH);
        this.add(pS, BorderLayout.SOUTH);

        btnSave.addActionListener(this);
        btnCancel.addActionListener(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnSave){
            store(txtCourseCode.getText(),
                    txtTitle.getText(),
                    txtDepartmentId.getText(),
                    txtCredit.getText(),
                    txtDuration.getText(),
                    txtFullTime.getText());
        }
        else if (e.getSource() == btnCancel){
            CourseMainGUI.main(null);
            this.setVisible(false);
        }
    }

    public void store (String courseCode, String title, String departmentId, String creditString, String durationString, String fullTime){
        try {
            final String URL = "http://localhost:8080/course/create";
            int credit = Integer.parseInt(creditString);
            int duration = Integer.parseInt(durationString);
            boolean fulltime = Boolean.parseBoolean(fullTime);
            Course course = CourseFactory.build(courseCode, title, departmentId, credit, duration, fulltime);
            Gson g = new Gson();
            String jsonString = g.toJson(course);
            String r = post(URL, jsonString);
            if (r != null) {
                JOptionPane.showMessageDialog(null, "Course saved successfully!");
                CourseMainGUI.main(null);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Oops, Course did not saved.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public String post(final String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder().url(url).post(body).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static void main(String[] args) {
        new AddCourse().setGUI();
    }
}
