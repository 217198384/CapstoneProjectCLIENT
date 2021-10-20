package za.ac.cput.views.enroll;

import com.google.gson.Gson;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.entity.tertiaryInstitution.Enroll;
import za.ac.cput.views.student.StudentMainGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class UpdateEnrolls extends JFrame implements ActionListener {
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
    private final JLabel lblCourseCode;
    private final JLabel lblDate;
    private final JLabel lblPaymentReceived;
    private final JTextField txtUpdateId;
    private final JTextField txtCourseCode;
    private final JTextField txtDate;
    private final JTextField txtPaymentReceived;

    public UpdateEnrolls() {
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

        lblCourseCode = new JLabel("Course Code: ", SwingConstants.CENTER);
        lblDate = new JLabel("Date: ", SwingConstants.CENTER);
        lblPaymentReceived = new JLabel("Payment Received: ", SwingConstants.CENTER);


        txtCourseCode = new JTextField();
        txtDate = new JTextField();
        txtPaymentReceived = new JTextField();


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
        new UpdateEnrolls().setGUI();
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

        pFields.add(lblCourseCode);
        pFields.add(txtCourseCode);
        pFields.add(lblDate);
        pFields.add(txtDate);
        pFields.add(lblPaymentReceived);
        pFields.add(txtPaymentReceived);


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
        model.addColumn("Course Code");
        model.addColumn("Date");
        model.addColumn("Payment Received");


        try {
            final String URL = "http://localhost:8080/enroll/getall";
            String responseBody = run(URL);
            JSONArray enrolls = new JSONArray(responseBody);

            for (int i = 0; i < enrolls.length(); i++) {
                JSONObject enroll = enrolls.getJSONObject(i);

                Gson g = new Gson();
                Enroll s = g.fromJson(enroll.toString(), Enroll.class);

                Object[] rowData = new Object[4];
                rowData[0] = s.getStudentID();
                rowData[1] = s.getCourseCode();
                rowData[2] = s.getDate();
                rowData[3] = s.getPaymentReceived();

                model.addRow(rowData);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Enroll getStudent(String id) throws IOException {
        Enroll enroll = null;
        try {
            final String URL = "http://localhost:8080/student/read/" + id;
            String responseBody = run(URL);
            Gson gson = new Gson();
            enroll = gson.fromJson(responseBody, Enroll.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(enroll);
        return enroll;
    }

    public void store(String courseCode, String date, String paymentReceived) {
        Enroll enroll = null;
        try {
            final String URL = "http://localhost:8080/student/update";
            enroll = new Enroll.EnrollBuilder()
                    .setStudentID(txtUpdateId.getText())
                    .setCourseCode(txtCourseCode.getText())
                    .setDate(txtDate.getText())
                    .setPaymentReceived(txtPaymentReceived.getText())

                    .build();
            Gson g = new Gson();
            String jsonString = g.toJson(enroll);
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
        System.out.println(enroll);
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
                        Enroll ab = getStudent(txtUpdateId.getText());
                        if (ab != null) {
                            pFields.setVisible(true);
                            txtCourseCode.setText(ab.getCourseCode());
                            txtDate.setText(ab.getDate());
                            txtPaymentReceived.setText(ab.getPaymentReceived());

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
                store(txtCourseCode.getText(),
                        txtDate.getText(),

                        txtPaymentReceived.getText());
                break;
            case "Back":
                StudentMainGUI.main(null);
                this.setVisible(false);
                break;
        }
    }
}
