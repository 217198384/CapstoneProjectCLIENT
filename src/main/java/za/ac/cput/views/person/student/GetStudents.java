package za.ac.cput.views.person.student;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.entity.person.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GetStudents extends JFrame implements ActionListener {
    private static OkHttpClient client = new OkHttpClient();

    private JTable table;
    private JPanel pC, pS;
    private JButton btnBack;

    public GetStudents() {
        super("All Students");
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            StudentMainGUI.main(null);
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new GetStudents().setGUI();
    }
}
