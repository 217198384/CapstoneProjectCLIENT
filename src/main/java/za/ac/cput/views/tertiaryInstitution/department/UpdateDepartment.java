package za.ac.cput.views.tertiaryInstitution.department;

import com.google.gson.Gson;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.entity.tertiaryInstitution.Department;
import za.ac.cput.factory.tertiaryInstitution.DepartmentFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class UpdateDepartment extends JFrame implements ActionListener {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();

    private JTable table;
    private JPanel pN, pC, pS, pFields, pUpdate;
    private JButton btnUpdate, btnBack, btnEnter;
    private JLabel lblUpdate, blank1, blank2, blank3, blank4,
            blank5, blank6, blank7, blank8,
            lblDepId, lblDepName, lblDepDesc;
    private JTextField txtUpdateId, txtDepId, txtDepName, txtDepDesc;

    public UpdateDepartment(){
        super("Update Department");
        table = new JTable();

        pN = new JPanel();
        pC = new JPanel();
        pS = new JPanel();
        pUpdate = new JPanel();
        pFields = new JPanel();

        btnUpdate = new JButton("Update");
        btnBack = new JButton("Back");
        btnEnter = new JButton("Enter");

        lblUpdate = new JLabel("Enter Department ID to Update: ", SwingConstants.CENTER);
        txtUpdateId = new JTextField();

        txtDepId = new JTextField();
        txtDepName = new JTextField();
        txtDepDesc = new JTextField();

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
        pFields.setLayout(new GridLayout(3,2));
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

//        pFields.add(lblDepId);
//        pFields.add(txtDepId);
//        pFields.add(lblDepName);
//        pFields.add(txtDepName);
//        pFields.add(lblDepDesc);
//        pFields.add(txtDepDesc);

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
        model.addColumn("Department ID");
        model.addColumn("Department Name");
        model.addColumn("Department Desc");

        try {
            final String URL = "http://localhost:8080/department/getAll";
            String responseBody = run(URL);
            JSONArray departments = new JSONArray(responseBody);

            for (int i = 0; i < departments.length(); i++) {
                JSONObject department = departments.getJSONObject(i);

                Gson g = new Gson();
                Department d = g.fromJson(department.toString(), Department.class);

                Object[] rowData = new Object[3];
                rowData[0] = d.getDepartmentId();
                rowData[1] = d.getDepartmentName();
                rowData[2] = d.getDepartmentDesc();
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

    private Department getDepartment(String id) throws IOException{
        Department department = null;
        try {
            final String URL = "http://localhost:8080/department/read/{id}" + id;
            String responseBody = run(URL);
            Gson gson = new Gson();
            department = gson.fromJson(responseBody, Department.class);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(department);
        return department;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Enter":
                if (!Objects.equals(txtDepId.getText(), "")){
                    try {
                        Department d = getDepartment((txtUpdateId.getText()));
                        if(d != null){
                            pFields.setVisible(true);
                            txtDepId.setText(d.getDepartmentId());
                            txtDepName.setText(d.getDepartmentName());
                            txtDepDesc.setText(d.getDepartmentDesc());
                        } else {
                            JOptionPane.showMessageDialog(null, "No Department with entered ID"); }
                    } catch (IOException ex) {
                        ex.printStackTrace(); }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid Department ID"); }
                break;

            case "Update":
                store(txtDepId.getText(),
                        txtDepName.getText(),
                        txtDepDesc.getText());
                break;

            case "Back":
                DepartmentMainGUI.main(null);
                this.setVisible(false);
                break;
        }
    }

    public static void main(String[] args) {
        new UpdateDepartment().setGUI();
    }
}
