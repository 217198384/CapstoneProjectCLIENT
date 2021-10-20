package za.ac.cput.views.tertiaryInstitution.semester;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.entity.tertiaryInstitution.Semester;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GetSemester extends JFrame implements ActionListener {
    private static final OkHttpClient client = new OkHttpClient();

    private final JTable table;
    private final JPanel pC;
    private final JPanel pS;
    private final JButton btnBack;

    public GetSemester() {
        super("Semesters");
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
        new GetSemester().setGUI();
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
        model.addColumn("Semester ID");
        model.addColumn("Semester Start");
        model.addColumn("Semester End");


        try {
            final String URL = "http://localhost:8080/semester/getall";
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            SemesterMainGUI.main(null);
            this.setVisible(false);
        }
    }
}

