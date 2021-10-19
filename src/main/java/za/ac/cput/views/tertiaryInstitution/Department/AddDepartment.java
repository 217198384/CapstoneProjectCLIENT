package za.ac.cput.views.tertiaryInstitution.Department;

import com.google.gson.Gson;
import okhttp3.*;
import za.ac.cput.entity.tertiaryInstitution.Department;
import za.ac.cput.factory.tertiaryInstitution.DepartmentFactory;
import za.ac.cput.views.student.StudentMainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AddDepartment extends JFrame implements ActionListener {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();

    private JLabel lblDepartmentID, lblDepartmentName, lblDepartmentDesc;
    private JTextField txtDepId, txtDepName, txtDepDesc;
    private JButton btnSave, btnCancel;
    public JPanel pN, pS;

    public AddDepartment(){
        super("Add new Department");

        pN = new JPanel();
        pS = new JPanel();

        lblDepartmentName = new JLabel("Department Name: ");
        lblDepartmentID = new JLabel("Department ID: ");
        lblDepartmentDesc = new JLabel("Department Desc: ");

        txtDepId = new JTextField(30);
        txtDepName = new JTextField(30);
        txtDepDesc = new JTextField(30);

        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");
    }

    public void setGUI() {
        pN.setLayout(new GridLayout(0,2));
        pS.setLayout(new GridLayout(1,2));

        pN.add(lblDepartmentName);
        pN.add(txtDepName);
        pN.add(lblDepartmentID);
        pN.add(txtDepId);
        pN.add(lblDepartmentDesc);
        pN.add(txtDepDesc);

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
            store(txtDepId.getText(),
                    txtDepName.getText(),
                    txtDepDesc.getText());
        } else if (e.getSource() == btnCancel) {
            DepartmentMainGUI.main(null);
            this.setVisible(false);
        }
    }

    public void store(String DepId, String DepName, String DepDesc){
        try{
            final String URL = "http://localhost:8080/department/create";
            Department department = DepartmentFactory.build(DepId, DepName, DepDesc);
            Gson g = new Gson();
            String jsonString = g.toJson(department);
            String r = post(URL, jsonString);
            if (r != null) {
                JOptionPane.showMessageDialog(null, "Department saved successfully!");
                DepartmentMainGUI.main(null);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Oops, Department could not save.");
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

    public static void main(String[] args){
        new AddDepartment().setGUI();
    }
}
