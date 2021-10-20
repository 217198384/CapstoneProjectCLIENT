package za.ac.cput.views.tertiaryInstitution.course;

import com.google.gson.Gson;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.entity.tertiaryInstitution.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class UpdateCourse extends JFrame implements ActionListener {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();

    private JTable table;
    private JPanel pN, pC, pS, pFields, pUpdate;
    private JButton btnUpdate, btnBack, btnEnter;
    private JLabel blank1, blank2, blank3, blank4,
            blank5, blank6, blank7, blank8,
            lblCourseCode, lblTitle, lblDepartmentId, lblCredit, lblDuration, lblFullTime;
    private JTextField txtCourseCode, txtTitle, txtDepartmentId, txtCredit, txtDuration, txtFullTime;

    public UpdateCourse(){
        super("Update Course");
        table = new JTable();

        pN = new JPanel();
        pC = new JPanel();
        pS = new JPanel();
        pUpdate = new JPanel();
        pFields = new JPanel();

        btnUpdate = new JButton("Update");
        btnBack = new JButton("Back");
        btnEnter = new JButton("Enter");

        lblCourseCode = new JLabel("Enter Department ID to update: ", SwingConstants.CENTER);
        txtCourseCode = new JTextField();

        lblTitle = new JLabel("Title: ", SwingConstants.CENTER);
        lblDepartmentId = new JLabel("Department ID: ", SwingConstants.CENTER);
        lblCredit = new JLabel("Credit: ", SwingConstants.CENTER);
        lblDuration = new JLabel("Duration: ", SwingConstants.CENTER);
        lblFullTime = new JLabel("Full time: ", SwingConstants.CENTER);

        txtTitle = new JTextField();
        txtDepartmentId = new JTextField();
        txtCredit = new JTextField();
        txtDuration = new JTextField();
        txtFullTime = new JTextField();

        blank1 = new JLabel("");
        blank2 = new JLabel("");
        blank3 = new JLabel("");
        blank4 = new JLabel("");
        blank5 = new JLabel("");
        blank6 = new JLabel("");
        blank7 = new JLabel("");
        blank8 = new JLabel("");
    }

    public void setGUI(){
        pN.setLayout(new GridLayout(0,1));
        pC.setLayout(new GridLayout(0,1));
        pUpdate.setLayout(new GridLayout(3,3));
        pFields.setLayout(new GridLayout(5,2));
        pS.setLayout(new GridLayout(2,2));

        pN.add(new JScrollPane(table));

        pUpdate.add(blank1);
        pUpdate.add(blank2);
        pUpdate.add(blank3);
        pUpdate.add(lblCourseCode);
        pUpdate.add(txtCourseCode);
        pUpdate.add(btnEnter);
        pUpdate.add(blank4);
        pUpdate.add(blank5);

        pFields.add(lblTitle);
        pFields.add(txtTitle);
        pFields.add(lblDepartmentId);
        pFields.add(txtDepartmentId);
        pFields.add(lblCredit);
        pFields.add(txtCredit);
        pFields.add(lblDuration);
        pFields.add(txtDuration);
        pFields.add(lblFullTime);
        pFields.add(txtFullTime);

        pC.add(pUpdate);
        pC.add(pFields);

        pS.add(blank7);
        pS.add(blank8);
        pS.add(btnUpdate);
        pS.add(btnBack);

        displayTable();
        pFields.setVisible(false);

        this.add(pN, BorderLayout.NORTH);
        this.add(pC, BorderLayout.CENTER);
        this.add(pS, BorderLayout.SOUTH);

        btnEnter.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnBack.addActionListener(this);

        table.setRowHeight(30);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void displayTable(){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addColumn("Course Code");
        model.addColumn("Title");
        model.addColumn("Department ID");
        model.addColumn("Credit");
        model.addColumn("Duration");
        model.addColumn("Full Time");

        try{
            final String URL = "http://localhost:8080/course/getAll";
            String responseBody = run(URL);
            JSONArray courses = new JSONArray(responseBody);

            for(int i = 0; i < courses.length(); i++){
                JSONObject course = courses.getJSONObject(i);

                Gson g = new Gson();
                Course c = g.fromJson(course.toString(), Course.class);

                Object[] rowData = new Object[6];
                rowData[0] = c.getcourseCode();
                rowData[1] = c.getcourseTitle();
                rowData[2] = c.getdepartmentId();
                rowData[3] = c.getCredit();
                rowData[4] = c.getDuration();
                rowData[5] = c.getFullTime();
                model.addRow(rowData);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private Course getCourse(String id) throws IOException{
        Course course = null;
        try{
            final String URL = "http://localhost:8080/course/read/" + id;
            String responseBody = run(URL);
            Gson gson = new Gson();
            course = gson.fromJson(responseBody, Course.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println(course);
        return course;
    }

    public void store (String courseCode, String title, String departmentId, String credit, String duration, String fullTime){
        try {
            final String URL = "http://localhost:8080/course/update";

            Course course = new Course.CourseBuilder()
                    .setCourseCode(txtCourseCode.getText())
                    .setTitle(txtTitle.getText())
                    .setDepartmentId(txtDepartmentId.getText())
                    .setCredit(Integer.parseInt(txtCredit.getText()))
                    .setDuration(Integer.parseInt(txtDuration.getText()))
                    .setFullTime(Boolean.parseBoolean(txtFullTime.getText()))
                    .build();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Enter":
                if (!Objects.equals(txtCourseCode.getText(), "")) {
                    try{
                        Course c = getCourse((txtCourseCode.getText()));
                        if(c != null){
                            pFields.setVisible(true);
                            txtTitle.setText(c.getcourseTitle());
                            txtDepartmentId.setText(c.getdepartmentId());
                            txtCredit.setText(String.valueOf(c.getCredit()));
                            txtDuration.setText(String.valueOf(c.getDuration()));
                            txtFullTime.setText(String.valueOf(c.getFullTime()));
                        } else {
                            JOptionPane.showMessageDialog(null, "No Course with entered Code"); }
                    } catch (IOException ex) {
                        ex.printStackTrace(); }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid Course Code"); }
                break;

            case "Update":
                store(txtCourseCode.getText(),
                        txtTitle.getText(),
                        txtDepartmentId.getText(),
                        txtCredit.getText(),
                        txtDuration.getText(),
                        txtFullTime.getText());
                break;

            case "Back":
                CourseMainGUI.main(null);
                this.setVisible(false);
                break;
        }
    }

        public static void main(String[] args) {
            new UpdateCourse().setGUI();
        }
    }
