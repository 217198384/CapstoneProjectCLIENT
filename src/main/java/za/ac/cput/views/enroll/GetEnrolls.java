package za.ac.cput.views.enroll;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.entity.tertiaryInstitution.Enroll;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GetEnrolls extends JFrame implements ActionListener {
    private static final OkHttpClient client = new OkHttpClient();

    private final JTable table;
    private final JPanel pC;
    private final JPanel pS;
    private final JButton btnBack;

    public GetEnrolls() {
        super("All Enrollments");
        table = new JTable();

        pC = new JPanel();
        pS = new JPanel();

        btnBack = new JButton("Back");
    }

    private static String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static void main(String[] args) {
        new GetEnrolls().setGUI();
    }

    public void setGUI() {
        pC.setLayout(new GridLayout(1, 1));
        pS.setLayout(new GridLayout(1, 1));

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
        model.addColumn("Course Code");
        model.addColumn("Date");
        model.addColumn("PaymentReceived");


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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            EnrollMainGUI.main(null);
            this.setVisible(false);
        }
    }
}

