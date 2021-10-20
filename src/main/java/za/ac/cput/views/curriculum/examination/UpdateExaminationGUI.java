package za.ac.cput.views.curriculum.examination;

/**
 * Dinelle Kotze
 * 219089302
 * UpdateExaminationGUI.java
 * This is the Update Examination GUI for the Examination entity.
 */

import com.google.gson.Gson;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.entity.curriculum.Examination;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class UpdateExaminationGUI extends JFrame implements ActionListener
{
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();

    private JTable table;
    private JPanel panelNorth, panelCenter, panelSouth, panelFields, panelUpdate;
    private JButton btnUpdate, btnBack, btnEnter;
    private JLabel lblUpdate, blank1, blank2, blank3, blank4,
            blank5, blank6, blank7, blank8,
            lblDesc, lblSubjectCode, lblDate;
    private JTextField txtUpdateId, txtDesc,
            txtSubjectCode, txtDate;

    public UpdateExaminationGUI()
    {
        super("Update Examination");
        table = new JTable();

        panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();
        panelUpdate = new JPanel();
        panelFields = new JPanel();

        btnUpdate = new JButton("Update");
        btnBack = new JButton("Back");
        btnEnter = new JButton("Enter");

        lblUpdate = new JLabel("Enter Examination ID to Update: ", SwingConstants.CENTER);
        txtUpdateId = new JTextField();

        lblDesc = new JLabel("Description: ", SwingConstants.CENTER);
        lblSubjectCode = new JLabel("Subject Code: ", SwingConstants.CENTER);
        lblDate = new JLabel("Date: ", SwingConstants.CENTER);

        txtDesc = new JTextField();
        txtSubjectCode = new JTextField();
        txtDate = new JTextField();

        blank1 = new JLabel("");
        blank2 = new JLabel("");
        blank3 = new JLabel("");
        blank4 = new JLabel("");
        blank5 = new JLabel("");
        blank6 = new JLabel("");
        blank7 = new JLabel("");
        blank8 = new JLabel("");
    }

    public void setGUI()
    {
        panelNorth.setLayout(new GridLayout(0,1));
        panelCenter.setLayout(new GridLayout(0,1));
        panelUpdate.setLayout(new GridLayout(3,3));
        panelFields.setLayout(new GridLayout(5,2));
        panelSouth.setLayout(new GridLayout(2,2));

        panelNorth.add(new JScrollPane(table));

        panelUpdate.add(blank1);
        panelUpdate.add(blank2);
        panelUpdate.add(blank3);
        panelUpdate.add(lblUpdate);
        panelUpdate.add(txtUpdateId);
        panelUpdate.add(btnEnter);
        panelUpdate.add(blank4);
        panelUpdate.add(blank5);

        panelFields.add(lblDesc);
        panelFields.add(txtDesc);
        panelFields.add(lblSubjectCode);
        panelFields.add(txtSubjectCode);
        panelFields.add(lblDate);
        panelFields.add(txtDate);

        panelCenter.add(panelUpdate);
        panelCenter.add(panelFields);

        panelSouth.add(blank7);
        panelSouth.add(blank8);
        panelSouth.add(btnUpdate);
        panelSouth.add(btnBack);

        displayTable();
        panelFields.setVisible(false);

        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);

        btnEnter.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnBack.addActionListener(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        table.setRowHeight(30);
        this.pack();
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void displayTable()
    {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addColumn("Examination ID");
        model.addColumn("Description");
        model.addColumn("Subject Code");
        model.addColumn("Date");

        try
        {
            final String URL = "http://localhost:8080/examination/getall";
            String responseBody = run(URL);
            JSONArray examinations = new JSONArray(responseBody);

            for (int i = 0; i < examinations.length(); i++)
            {
                JSONObject examination = examinations.getJSONObject(i);

                Gson g = new Gson();
                Examination e = g.fromJson(examination.toString(), Examination.class);

                Object[] rowData = new Object[4];
                rowData[0] = e.getExamId();
                rowData[1] = e.getExamDesc();
                rowData[2] = e.getSubjectCode();
                rowData[3] = e.getExamDate();
                model.addRow(rowData);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static String run(String url) throws IOException
    {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private Examination getExamination(String id) throws IOException
    {
        Examination examination = null;
        try
        {
            final String URL = "http://localhost:8080/examination/read/" + id;
            String responseBody = run(URL);
            Gson gson = new Gson();
            examination = gson.fromJson(responseBody, Examination.class);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        System.out.println(examination);
        return examination;
    }

    public void store(String desc, String subjectCode, String date)
    {
        Examination examination = null;

        try
        {
            final String URL = "http://localhost:8080/examination/update";

            examination = new Examination.Builder()
                    .setExamId(txtUpdateId.getText())
                    .setExamDesc(desc)
                    .setSubjectCode(Integer.parseInt(subjectCode))
                    .setExamDate(date)
                    .build();

            Gson g = new Gson();
            String jsonString = g.toJson(examination);
            String r = post(URL, jsonString);
            System.out.println(r);
            if (r != null)
            {
                JOptionPane.showMessageDialog(null, "Examination updated successfully!");
                ExaminationMainGUI.main(null);
                this.setVisible(false);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Oops, Examination not updated.");
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        System.out.println(examination);
    }

    public String post(final String url, String json) throws IOException
    {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder().url(url).post(body).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch (e.getActionCommand())
        {
            case "Enter" :
                if (!Objects.equals(txtUpdateId.getText(), "")) {
                    try {
                        Examination examination = getExamination(txtUpdateId.getText());
                        if (examination != null)
                        {
                            panelFields.setVisible(true);
                            txtDesc.setText(examination.getExamDesc());
                            txtSubjectCode.setText(String.valueOf(examination.getSubjectCode()));
                            txtDate.setText(String.valueOf(examination.getExamDate()));
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "No Examination with that ID");
                        }
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Please enter a valid Examination ID");
                }
                break;
            case "Update" :
                store(txtDesc.getText(),
                        txtSubjectCode.getText(),
                        txtDate.getText());
                break;
            case "Back" :
                ExaminationMainGUI.main(null);
                this.setVisible(false);
                break;
        }
    }

    public static void main(String[] args)
    {
        new UpdateExaminationGUI().setGUI();
    }
}
