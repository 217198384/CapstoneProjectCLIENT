package za.ac.cput.views.student;

import com.google.gson.Gson;
import okhttp3.*;
import za.ac.cput.entity.person.Student;
import za.ac.cput.factory.person.StudentFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AddStudent extends JFrame implements ActionListener {
    public static final MediaType JSON =
            MediaType.get("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();

    private JLabel lblName, lblSurname, lblAge, lblEmail, lblPhone;
    private JTextField txtName, txtSurname, txtAge, txtEmail, txtPhone;
    private JButton btnSave, btnCancel;
    public JPanel pN, pS;

    public AddStudent() {
        super("Add new Student");

        pN = new JPanel();
        pS = new JPanel();

        lblName = new JLabel("First Name: ");
        lblSurname = new JLabel("Last Name: ");
        lblAge = new JLabel("Age: ");
        lblEmail = new JLabel("Email Address: ");
        lblPhone = new JLabel("Phone Number: ");

        txtName = new JTextField(30);
        txtSurname = new JTextField(30);
        txtAge = new JTextField(30);
        txtEmail = new JTextField(30);
        txtPhone = new JTextField(30);

        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");
    }

    public void setGUI() {
        pN.setLayout(new GridLayout(0,2));
        pS.setLayout(new GridLayout(1,2));

        pN.add(lblName);
        pN.add(txtName);
        pN.add(lblSurname);
        pN.add(txtSurname);
        pN.add(lblAge);
        pN.add(txtAge);
        pN.add(lblEmail);
        pN.add(txtEmail);
        pN.add(lblPhone);
        pN.add(txtPhone);

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
        if (e.getSource() == btnSave) {
            store(txtName.getText(),
                    txtSurname.getText(),
                    txtAge.getText(),
                    txtEmail.getText(),
                    txtPhone.getText());
        } else if (e.getSource() == btnCancel) {
            StudentMainGUI.main(null);
            this.setVisible(false);
        }
    }

    public void store(String fName, String lName, String ageString, String email, String phone) {
        try {
            final String URL = "http://localhost:8080/student/create";
            int age = Integer.parseInt(ageString);
            Student student = StudentFactory.build(fName, lName, email, phone, age);
            Gson g = new Gson();
            String jsonString = g.toJson(student);
            String r = post(URL, jsonString);
            if (r != null) {
                JOptionPane.showMessageDialog(null, "Student saved successfully!");
                StudentMainGUI.main(null);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Oops, Student not saved.");
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
        new AddStudent().setGUI();
    }
}
