package za.ac.cput.views.tertiaryInstitution.Course;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.entity.tertiaryInstitution.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GetCourse extends JFrame implements ActionListener {
    private static OkHttpClient client = new OkHttpClient();

    private JTable table;
    private JPanel pC, pS;
    private JButton btnBack;

    public GetCourse(){
        super("All Courses");
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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(1000, 450);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void getAll(){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addColumn("Course Code");
        model.addColumn("Title");
        model.addColumn("Department ID");
        model.addColumn("Title");
        model.addColumn("Credit");
        model.addColumn("Duration");
        model.addColumn("Full Time");

        try{
            final String URL = "http://localhost:8080/course/getAll";
            String responseBody = run(URL);
            JSONArray courses = new JSONArray(responseBody);

                for(int i=0; i < courses.length(); i++){
                    JSONObject course = courses.getJSONObject(i);

                    Gson g = new Gson();
                    Course c = g.fromJson(course.toString(), Course.class);

                    Object[] rowData = new Object[6];
                    rowData[0] = c.getcourseCode();
                    rowData[1] = c.getcourseTitle();
                    rowData[2] = c.getdepartmentId();
                    rowData[3] = c.getCredit();
                    rowData[4] = c.getDuration();
                    rowData[5] = c.getFullTime();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            CourseMainGUI.main(null);
            this.setVisible(false);
        }
    }

        public static void main(String[] args) {
            new GetCourse().setGUI();
        }
    }