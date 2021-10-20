package za.ac.cput.views.semester;

import com.google.gson.Gson;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.entity.tertiaryInstitution.Semester;
import za.ac.cput.views.enroll.EnrollMainGUI;
import za.ac.cput.views.student.StudentMainGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class UpdateSemeter extends JFrame implements ActionListener {
    public static final MediaType JSON =
            MediaType.get("application/json; charset=utf-8");
    private static final OkHttpClient client = new OkHttpClient();

    private final JTable table;
    private final JPanel pN;
    private final JPanel pC;
    private final JPanel pS;
    private final JPanel pFields;
    private final JPanel pUpdate;
    private final JButton btnUpdate;
    private final JButton btnBack;
    private final JButton btnEnter;
    private final JLabel lblUpdate;
    private final JLabel blank1;
    private final JLabel blank2;
    private final JLabel blank3;
    private final JLabel blank4;
    private final JLabel blank5;
    private final JLabel blank6;
    private final JLabel blank7;
    private final JLabel blank8;
    private final JLabel lblSemesterStart;
    private final JLabel lblSemesterEnd;
    private final JTextField txtUpdateId;
    private final JTextField txtSemesterStart;
    private final JTextField txtSemesterEnd;

    public UpdateSemeter() {
        super("Update Enrolled Student");
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

        lblSemesterStart = new JLabel("Semester Start: ", SwingConstants.CENTER);
        lblSemesterEnd = new JLabel("Semes terEnd: ", SwingConstants.CENTER);


        txtSemesterStart = new JTextField();
        txtSemesterEnd = new JTextField();


        blank1 = new JLabel("");
        blank2 = new JLabel("");
        blank3 = new JLabel("");
        blank4 = new JLabel("");
        blank5 = new JLabel("");
        blank6 = new JLabel("");
        blank7 = new JLabel("");
        blank8 = new JLabel("");
    }

    private static String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static void main(String[] args) {
        new UpdateSemeter().setGUI();
    }

    public void setGUI() {
        pN.setLayout(new GridLayout(0, 1));
        pC.setLayout(new GridLayout(0, 1));
        pUpdate.setLayout(new GridLayout(3, 3));
        pFields.setLayout(new GridLayout(5, 2));
        pS.setLayout(new GridLayout(2, 2));

        pN.add(new JScrollPane(table));

        pUpdate.add(blank1);
        pUpdate.add(blank2);
        pUpdate.add(blank3);
        pUpdate.add(lblUpdate);
        pUpdate.add(txtUpdateId);
        pUpdate.add(btnEnter);
        pUpdate.add(blank4);
        pUpdate.add(blank5);

        pFields.add(lblSemesterStart);
        pFields.add(txtSemesterStart);
        pFields.add(lblSemesterEnd);
        pFields.add(txtSemesterEnd);


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
        model.addColumn("Semester ID");
        model.addColumn("Semester Start");
        model.addColumn("Semester End");


        try {
            final String URL = "http://localhost:8080/enroll/getall";
            String responseBody = run(URL);
            JSONArray semesters = new JSONArray(responseBody);

            for (int i = 0; i < semesters.length(); i++) {
                JSONObject enroll = semesters.getJSONObject(i);

                Gson g = new Gson();
                Semester s = g.fromJson(enroll.toString(), Semester.class);

                Object[] rowData = new Object[3];
                rowData[0] = s.getSemesterID();
                rowData[1] = s.getSemesterStart();
                rowData[2] = s.getSemesterEnd();


                model.addRow(rowData);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Semester getStudent(String id) throws IOException {
        Semester semester = null;
        try {
            final String URL = "http://localhost:8080/semester/read/" + id;
            String responseBody = run(URL);
            Gson gson = new Gson();
            semester = gson.fromJson(responseBody, Semester.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(semester);
        return semester;
    }

    public void store(String semesterStart, String semesterEnd) {
        Semester semester = null;
        try {
            final String URL = "http://localhost:8080/student/update";
            semester = new Semester.SemesterBuilder()

                    .setSemesterStart(txtSemesterStart.getText())
                    .setSemesterEnd(txtSemesterEnd.getText())


                    .build();
            Gson g = new Gson();
            String jsonString = g.toJson(semester);
            String r = post(URL, jsonString);
            System.out.println(r);
            if (r != null) {
                JOptionPane.showMessageDialog(null, "Student updated successfully!");
                EnrollMainGUI.main(null);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Student Not Updated");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        System.out.println(semester);
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
            case "Enter":
                if (!Objects.equals(txtUpdateId.getText(), "")) {
                    try {
                        Semester ab = getStudent(txtUpdateId.getText());
                        if (ab != null) {
                            pFields.setVisible(true);
                            txtSemesterStart.setText(ab.getSemesterStart());
                            txtSemesterEnd.setText(ab.getSemesterEnd());


                        } else {
                            JOptionPane.showMessageDialog(null, "No Student matches the ID");
                        }
                    } catch (IOException ep) {
                        ep.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid Student ID");
                }
                break;
            case "Update":
                store(txtSemesterStart.getText(),

                        txtSemesterEnd.getText());
                break;
            case "Back":
                SemesterMainGUI.main(null);
                this.setVisible(false);
                break;
        }
    }
}

