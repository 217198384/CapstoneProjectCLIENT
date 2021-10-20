package za.ac.cput.views.tertiaryInstitution.department;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.entity.tertiaryInstitution.Department;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GetDepartment extends JFrame implements ActionListener {
    private static OkHttpClient client = new OkHttpClient();

    private JTable table;
    private JPanel pC, pS;
    private JButton btnBack;

    public GetDepartment() {
        super("All Departments");
        table = new JTable();

        pC = new JPanel();
        pS = new JPanel();

        btnBack = new JButton("Back");
    }

    public void setGUI() {
        pC.setLayout(new GridLayout(1,1));
        pS.setLayout(new GridLayout(1,1));

        pC.add(table);
        pS.add(btnBack);

        this.add(pC, BorderLayout.CENTER);
        this.add(pS, BorderLayout.SOUTH);

        btnBack.addActionListener(this);

        getAll();
        table.setRowHeight(30);
        this.add(new JScrollPane(table));
        this.pack();
        this.setSize(1000, 450);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void getAll() {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            DepartmentMainGUI.main(null);
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new GetDepartment().setGUI();
    }
}