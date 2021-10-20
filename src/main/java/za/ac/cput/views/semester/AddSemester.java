package za.ac.cput.views.semester;

import com.google.gson.Gson;
import okhttp3.*;
import za.ac.cput.entity.tertiaryInstitution.Semester;
import za.ac.cput.factory.tertiaryInstitution.SemesterFactory;
import za.ac.cput.views.enroll.EnrollMainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AddSemester extends JFrame implements ActionListener {
    public static final MediaType JSON =
            MediaType.get("application/json; charset=utf-8");
    private static final OkHttpClient client = new OkHttpClient();
    public JPanel pN, pS;
    private final JLabel lblSemesterID;
    private final JLabel lblSemesterStart;
    private final JLabel lblSemesterEnd;
    private final JTextField txtSemesterID;
    private final JTextField txtSemesterStart;
    private final JTextField txtSemesterEnd;
    private final JButton btnSave;
    private final JButton btnCancel;

    public AddSemester() {
        super("Semester");
        pN = new JPanel();
        pS = new JPanel();

        lblSemesterID = new JLabel("Semester ID: ");
        lblSemesterStart = new JLabel("Semester Start: ");
        lblSemesterEnd = new JLabel("Semester End: ");


        txtSemesterID = new JTextField(30);
        txtSemesterStart = new JTextField(30);
        txtSemesterEnd = new JTextField(30);


        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");


    }

    public static void main(String[] args) {
        new AddSemester().setGUI();
    }

    public void setGUI() {
        pN.setLayout(new GridLayout(0, 2));
        pS.setLayout(new GridLayout(1, 2));

        pN.add(lblSemesterID);
        pN.add(lblSemesterStart);
        pN.add(lblSemesterEnd);

        pN.add(txtSemesterID);
        pN.add(txtSemesterStart);
        pN.add(txtSemesterEnd);


        pS.add(btnSave);
        pS.add(btnCancel);

        this.add(pN, BorderLayout.NORTH);
        this.add(pS, BorderLayout.SOUTH);

        btnSave.addActionListener(this);
        btnCancel.addActionListener(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSave) {
            store(txtSemesterID.getText(),
                    txtSemesterStart.getText(),

                    txtSemesterEnd.getText());

        } else if (e.getSource() == btnCancel) {
            SemesterMainGUI.main(null);
            this.setVisible(false);
        }
    }

    public void store(String semesterID, String semesterStart, String semesterEnd) {
        try {
            final String URL = "http://localhost:8080/semester/create";

            Semester semester = SemesterFactory.build(semesterID, semesterStart, semesterEnd);
            Gson g = new Gson();
            String jsonString = g.toJson(semester);
            String r = post(URL, jsonString);
            if (r != null) {
                JOptionPane.showMessageDialog(null, "Semester Added");
                SemesterMainGUI.main(null);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Semester Could Not Be Added");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public String post(final String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder().url(url).post(body).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}

