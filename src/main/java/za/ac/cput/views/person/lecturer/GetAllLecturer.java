package za.ac.cput.views.person.lecturer;

/**
 * GetAllLecturer.java
 * Author: Shane Knoll (218279124)
 * Date: 20 October 2021
 */


import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.entity.person.Lecturer;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GetAllLecturer extends JFrame implements ActionListener {
    private static OkHttpClient client = new OkHttpClient();

    private JTable lecturerTable;
    private JLabel lblHeading;
    private JPanel panelCenter;
    private JPanel panelSouth;
    private JPanel panelNorth;
    private JButton btnExit;

    public GetAllLecturer() {
        super("Display all lecturers");
        lecturerTable = new JTable();
        lblHeading= new JLabel("GET ALL LECTURER TABLE");
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
        panelCenter.add(lecturerTable);
        panelSouth.add(btnExit);
        this.add(panelNorth,BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);
        lblHeading.setForeground(Color.RED);
        panelNorth.setBackground(Color.green);
        btnExit.addActionListener(this);

        getAll();
        lecturerTable.setRowHeight(40);
        this.add(new JScrollPane(lecturerTable));
        this.pack();
        this.setSize(1200, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void getAll() {
        DefaultTableModel model = (DefaultTableModel) lecturerTable.getModel();
        model.addColumn("Lecturer ID");
        model.addColumn("Lecturer's First Name");
        model.addColumn("Lecturer's Last Name");
        model.addColumn("Lecturer's Age");
        model.addColumn("Lecturer's Email Address");
        model.addColumn("Lecturer's Phone Number");

        try {
            final String URL = "http://localhost:8080/lecturer/getall";
            String responseBody = run(URL);
            JSONArray lecturer= new JSONArray(responseBody);

            for (int i = 0; i < lecturer.length(); i++) {
                JSONObject lect = lecturer.getJSONObject(i);

                Gson g = new Gson();
                Lecturer le = g.fromJson(lect.toString(), Lecturer.class);

                Object[] rowData = new Object[6];
                rowData[0] = le.getLecturerID();
                rowData[1] = le.getFirstName();
                rowData[2] = le.getLastName();
                rowData[3] = le.getAge();
                rowData[4] = le.getEmailAddress();
                rowData[5] = le.getContactNo();
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
        if(e.getSource()==btnExit){
            this.dispose();
            new LecturerMenuGUI().setGUI();
        }
    }

    public static void main(String[] args) {
        new GetAllLecturer().setGUI();
    }
}

