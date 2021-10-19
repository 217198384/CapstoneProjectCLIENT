package za.ac.cput.views.curriculum.subject;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.entity.curriculum.Subject;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GetAllSubject extends JFrame implements ActionListener {
    private static OkHttpClient client = new OkHttpClient();

    private JTable subjectTable;
    private JLabel lblHeading;
    private JPanel panelNorth;
    private JPanel panelCenter;
    private JPanel panelSouth;
    private JButton btnExit;

    public GetAllSubject() {
        super("Display all subjects");
        subjectTable = new JTable();
        lblHeading= new JLabel("GET ALL SUBJECT TABLE");
        panelNorth = new JPanel();
        panelSouth = new JPanel();
        panelCenter = new JPanel();
        btnExit= new JButton("Exit");
    }

    public void setGUI() {
        panelNorth.setLayout(new FlowLayout());
        panelCenter.setLayout(new GridLayout(1,1));
        panelSouth.setLayout(new GridLayout(1,1));
        panelNorth.add(lblHeading);
        panelCenter.add(subjectTable);
        panelSouth.add(btnExit);
        this.add(panelNorth,BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);

        lblHeading.setForeground(Color.RED);
        panelNorth.setBackground(Color.green);
        btnExit.addActionListener(this);
        getAll();
        subjectTable.setRowHeight(40);
        this.add(new JScrollPane(subjectTable));
        this.pack();
        this.setSize(1200, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void getAll() {
        DefaultTableModel model = (DefaultTableModel) subjectTable.getModel();
        model.addColumn("Subject code");
        model.addColumn("Subject Name");
        model.addColumn(" Course Code");
        model.addColumn("LecturerId");
        model.addColumn("SemesterId");


        try {
            final String URL = "http://localhost:8080/subject/getall";
            String responseBody = run(URL);
            JSONArray lecturer= new JSONArray(responseBody);

            for (int i = 0; i < lecturer.length(); i++) {
                JSONObject lect = lecturer.getJSONObject(i);

                Gson g = new Gson();
                Subject sub = g.fromJson(lect.toString(), Subject.class);

                Object[] rowData = new Object[5];
                rowData[0] = sub.getSubjectCode();
                rowData[1] = sub.getSubjectName();
                rowData[2] = sub.getCourseCode();
                rowData[3] = sub.getLecturerID();
                rowData[4] = sub.getSemesterID();
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
        if (e.getSource() == btnExit) {
            SubjectMenuGUI.main(null);
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new GetAllSubject().setGUI();
    }
}


