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

public class DeleteEnrolls extends JFrame implements ActionListener {
    private static final OkHttpClient client = new OkHttpClient();

    private final JTable table;
    private final JPanel pC;
    private final JPanel pS;
    private final JButton btnDelete;
    private final JButton btnBack;
    private final JLabel lblDelete;
    private final JLabel blank1;
    private final JLabel blank2;
    private final JLabel blank3;
    private final JLabel blank4;
    private final JTextField txtDeleteId;

    public DeleteEnrolls() {
        super("Delete Enrolled Student");
        table = new JTable();

        pC = new JPanel();
        pS = new JPanel();

        btnDelete = new JButton("Delete");
        btnBack = new JButton("Back");

        lblDelete = new JLabel(" Enter Student ID to Delete: ");
        txtDeleteId = new JTextField();

        blank1 = new JLabel("");
        blank2 = new JLabel("");
        blank3 = new JLabel("");
        blank4 = new JLabel("");
    }

    private static String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static void main(String[] args) {
        new DeleteEnrolls().setGUI();
    }

    public void setGUI() {
        pC.setLayout(new GridLayout(1, 1));
        pS.setLayout(new GridLayout(4, 2));

        pC.add(table);

        pS.add(blank1);
        pS.add(blank2);

        pS.add(lblDelete);
        pS.add(txtDeleteId);

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
        this.pack();
        this.setSize(1000, 450);
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

    public boolean request(String id) throws IOException {
        final String URL = "http://localhost:8080/student/delete/" + id;
        RequestBody body = RequestBody
                .create("charset=utf-8", MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .post(body)
                .addHeader("Accept", "application/json")
                .url(URL)
                .build();

        Response response = client.newCall(request).execute();
        return response.isSuccessful();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDelete) {
            if (!Objects.equals(txtDeleteId.getText(), "")) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Delete Student", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        if (request(txtDeleteId.getText())) {
                            JOptionPane.showMessageDialog(null, "Student Deleted");
                            StudentMainGUI.main(null);
                            this.setVisible(false);
                        } else {
                            JOptionPane.showMessageDialog(null, "No Student Deleted");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a value");
            }
        } else if (e.getSource() == btnBack) {
            StudentMainGUI.main(null);
            this.setVisible(false);
        }
    }

}

