package za.ac.cput.views.tertiaryInstitution.department;

import com.google.gson.Gson;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.entity.tertiaryInstitution.Department;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class DeleteDepartment extends JFrame implements ActionListener {
    private static OkHttpClient client = new OkHttpClient();

    private JTable table;
    private JPanel pC, pS;
    private JButton btnDelete, btnBack;
    private JLabel lblDelete, blank1, blank2, blank3, blank4;
    private JTextField txtDeleteDept;

    public DeleteDepartment(){
        super("Delete Course");
        table = new JTable();

        pC = new JPanel();
        pS = new JPanel();

        lblDelete = new JLabel(" Enter Department ID to Delete: ");
        txtDeleteDept = new JTextField();

        blank1 = new JLabel("");
        blank2 = new JLabel("");
        blank3 = new JLabel("");
        blank4 = new JLabel("");

        btnDelete = new JButton("Delete");
        btnBack = new JButton("Back");
    }

    public void setGUI(){
        pC.setLayout(new GridLayout(1,1));
        pS.setLayout(new GridLayout(4,2));

        pC.add(table);

        pS.add(blank1);
        pS.add(blank2);

        pS.add(lblDelete);
        pS.add(txtDeleteDept);

        pS.add(blank3);
        pS.add(blank4);

        pS.add(btnDelete);
        pS.add(btnBack);

        displayTable();

        this.add(pC, BorderLayout.CENTER);
        this.add(pS, BorderLayout.SOUTH);

        btnBack.addActionListener(this);
        btnDelete.addActionListener(this);

        table.setRowHeight(30);
        this.add(new JScrollPane(table));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(1000, 450);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void displayTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addColumn("Department ID");
        model.addColumn("Department Name");
        model.addColumn("Department Desc");

        try{
            final String URL = "http://localhost:8080/department/getAll";
            String responseBody = run(URL);
            JSONArray department = new JSONArray(responseBody);

            for(int i = 0; i < department.length(); i++){
                JSONObject course = department.getJSONObject(i);

                Gson g = new Gson();
                Department d = g.fromJson(course.toString(), Department.class);

                Object[] rowData = new Object[3];
                rowData[0] = d.getDepartmentId();
                rowData[1] = d.getDepartmentName();
                rowData[2] = d.getDepartmentDesc();
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

    public boolean request(String id) throws IOException{
        final String URL = "http://localhost:8080/department/delete" + id;
        RequestBody body = RequestBody.create( "charset=utf-8", MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .post(body)
                .addHeader("Accept", "application/json")
                .url(URL)
                .build();

        Response response =client.newCall(request).execute();
        return response.isSuccessful();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDelete) {
            if (!Objects.equals(txtDeleteDept.getText(), "")) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Delete Course", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        if(request(txtDeleteDept.getText())) {
                            JOptionPane.showMessageDialog(null,"Course Deleted");
                            DepartmentMainGUI.main(null);
                            this.setVisible(false);
                        } else {
                            JOptionPane.showMessageDialog(null,"Issue! Course Could Not Delete");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a value");
            }
        } else if (e.getSource() == btnBack) {
            DepartmentMainGUI.main(null);
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new DeleteDepartment().setGUI();
    }
}
