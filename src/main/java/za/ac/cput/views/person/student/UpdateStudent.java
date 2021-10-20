package za.ac.cput.views.person.student;

/**
 * UpdateStudent.java
 * Front end GUI for updating a student
 * @author Dylan Koevort 218088159
 * 18 October 2021
 */

import com.google.gson.Gson;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.entity.person.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class UpdateStudent extends JFrame implements ActionListener {
    public static final MediaType JSON =
            MediaType.get("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();

    private JTable table;
    private JPanel pN, pC, pS, pFields, pUpdate;
    private JButton btnUpdate, btnBack, btnEnter;
    private JLabel lblUpdate, blank1, blank2, blank3, blank4,
            blank5, blank6, blank7, blank8,
            lblName, lblSurname, lblAge, lblEmail, lblPhone;
    private JTextField txtUpdateId, txtName,
            txtSurname, txtAge, txtEmail, txtPhone;

    public UpdateStudent() {
        super("Update Student");
        table = new JTable();

        pN = new JPanel();
        pC = new JPanel();
        pS = new JPanel();
        pUpdate = new JPanel();
        pFields = new JPanel();

        btnUpdate = new JButton("Update");
        btnBack = new JButton("Back");
        btnEnter = new JButton("Enter");

        lblUpdate = new JLabel("Enter Student ID to Update: ", SwingConstants.CENTER);
        txtUpdateId = new JTextField();

        lblName = new JLabel("First Name: ", SwingConstants.CENTER);
        lblSurname = new JLabel("Last Name: ", SwingConstants.CENTER);
        lblAge = new JLabel("Age: ", SwingConstants.CENTER);
        lblEmail = new JLabel("Email Address: ", SwingConstants.CENTER);
        lblPhone = new JLabel("Phone Number: ", SwingConstants.CENTER);

        txtName = new JTextField();
        txtSurname = new JTextField();
        txtAge = new JTextField();
        txtEmail = new JTextField();
        txtPhone = new JTextField();

        blank1 = new JLabel("");
        blank2 = new JLabel("");
        blank3 = new JLabel("");
        blank4 = new JLabel("");
        blank5 = new JLabel("");
        blank6 = new JLabel("");
        blank7 = new JLabel("");
        blank8 = new JLabel("");
    }

    public void setGUI() {
        pN.setLayout(new GridLayout(0,1));
        pC.setLayout(new GridLayout(0,1));
        pUpdate.setLayout(new GridLayout(3,3));
        pFields.setLayout(new GridLayout(5,2));
        pS.setLayout(new GridLayout(2,2));

        pN.add(new JScrollPane(table));

        pUpdate.add(blank1);
        pUpdate.add(blank2);
        pUpdate.add(blank3);
        pUpdate.add(lblUpdate);
        pUpdate.add(txtUpdateId);
        pUpdate.add(btnEnter);
        pUpdate.add(blank4);
        pUpdate.add(blank5);

        pFields.add(lblName);
        pFields.add(txtName);
        pFields.add(lblSurname);
        pFields.add(txtSurname);
        pFields.add(lblAge);
        pFields.add(txtAge);
        pFields.add(lblEmail);
        pFields.add(txtEmail);
        pFields.add(lblPhone);
        pFields.add(txtPhone);

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
        this.pack();
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void displayTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addColumn("Student ID");
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("Age");
        model.addColumn("Email Address");
        model.addColumn("Phone Number");

        try {
            final String URL = "http://localhost:8080/student/getall";
            String responseBody = run(URL);
            JSONArray students = new JSONArray(responseBody);

            for (int i = 0; i < students.length(); i++) {
                JSONObject student = students.getJSONObject(i);

                Gson g = new Gson();
                Student s = g.fromJson(student.toString(), Student.class);

                Object[] rowData = new Object[6];
                rowData[0] = s.getStudentId();
                rowData[1] = s.getFirstName();
                rowData[2] = s.getLastName();
                rowData[3] = s.getAge();
                rowData[4] = s.getEmailAddress();
                rowData[5] = s.getContactNo();
                model.addRow(rowData);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private Student getStudent(String id) throws IOException {
        Student student = null;
        try {
            final String URL = "http://localhost:8080/student/read/" + id;
            String responseBody = run(URL);
            Gson gson = new Gson();
            student = gson.fromJson(responseBody, Student.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(student);
        return student;
    }

    public void store(String fName, String lName, String ageString, String email, String phone) {
        Student student = null;
        try {
            final String URL = "http://localhost:8080/student/update";
            int age = Integer.parseInt(ageString);
            student = new Student.StudentBuilder()
                    .setStudentId(txtUpdateId.getText())
                    .setFirstName(txtName.getText())
                    .setLastName(txtSurname.getText())
                    .setAge(Integer.parseInt(txtAge.getText()))
                    .setEmailAddress(txtEmail.getText())
                    .setContactNo(txtPhone.getText())
                    .build();
            Gson g = new Gson();
            String jsonString = g.toJson(student);
            String r = post(URL, jsonString);
            System.out.println(r);
            if (r != null) {
                JOptionPane.showMessageDialog(null, "Student updated successfully!");
                StudentMainGUI.main(null);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Oops, Student not updated.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        System.out.println(student);
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

        switch (e.getActionCommand()) {
            case "Enter" :
                if (!Objects.equals(txtUpdateId.getText(), "")) {
                    try {
                        Student s = getStudent(txtUpdateId.getText());
                        if(s != null) {
                            pFields.setVisible(true);
                            txtName.setText(s.getFirstName());
                            txtSurname.setText(s.getLastName());
                            txtAge.setText(String.valueOf(s.getAge()));
                            txtEmail.setText(s.getEmailAddress());
                            txtPhone.setText(s.getContactNo());
                        } else {
                            JOptionPane.showMessageDialog(null, "No Student with that ID");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid Student ID");
                }
                break;
            case "Update" :
                store(txtName.getText(),
                        txtSurname.getText(),
                        txtAge.getText(),
                        txtEmail.getText(),
                        txtPhone.getText());
                break;
            case "Back" :
                StudentMainGUI.main(null);
                this.setVisible(false);
                break;
        }
    }

    public static void main(String[] args) {
        new UpdateStudent().setGUI();
    }
}
