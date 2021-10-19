package za.ac.cput.views.examination;

/**
 * Dinelle Kotze
 * 219089302
 * AddExaminationGUI.java
 * This is the Add Examination GUI for the Examination entity.
 */

import com.google.gson.Gson;
import okhttp3.*;
import za.ac.cput.entity.curriculum.Examination;
import za.ac.cput.factory.curriculum.ExaminationFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class AddExaminationGUI extends JFrame implements ActionListener
{
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();

    private JLabel lblDesc, lblSubjectCode, lblDate;
    private JTextField txtDesc, txtSubjectCode, txtDate;
    private JButton btnSave, btnCancel;
    public JPanel panelNorth, panelSouth;

    public AddExaminationGUI()
    {
        super("Add new Examination");

        panelNorth = new JPanel();
        panelSouth = new JPanel();

        lblDesc = new JLabel("Description: ");
        lblSubjectCode = new JLabel("Subject Code: ");
        lblDate = new JLabel("Date: ");

        txtDesc = new JTextField(30);
        txtSubjectCode = new JTextField(30);
        txtDate = new JTextField(30);
        txtDate.setText("YYYY-MM-DD");

        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");
    }

    public void setGUI()
    {
        panelNorth.setLayout(new GridLayout(0,2));
        panelSouth.setLayout(new GridLayout(1,2));

        panelNorth.add(lblDesc);
        panelNorth.add(txtDesc);
        panelNorth.add(lblSubjectCode);
        panelNorth.add(txtSubjectCode);
        panelNorth.add(lblDate);
        panelNorth.add(txtDate);

        panelSouth.add(btnSave);
        panelSouth.add(btnCancel);

        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelSouth, BorderLayout.SOUTH);

        btnSave.addActionListener(this);
        btnCancel.addActionListener(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnSave)
        {
            store(txtDesc.getText(),
                    txtSubjectCode.getText(),
                    txtDate.getText());
        }
        else if (e.getSource() == btnCancel)
        {
            ExaminationMainGUI.main(null);
            this.setVisible(false);
        }
    }

    public void store(String desc, String subjectCode, String date)
    {
        try
        {
            final String URL = "http://localhost:8080/examination/create";
            Examination examination = ExaminationFactory.build(Integer.parseInt(subjectCode), desc, date);
            Gson g = new Gson();
            String jsonString = g.toJson(examination);
            String r = post(URL, jsonString);

            if (r != null)
            {
                JOptionPane.showMessageDialog(null, "Examination saved successfully!");
                ExaminationMainGUI.main(null);
                this.setVisible(false);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Oops, Examination not saved.");
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public String post(final String url, String json) throws IOException
    {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder().url(url).post(body).build();
        try (Response response = client.newCall(request).execute())
        {
            return response.body().string();
        }
    }

    public static void main(String[] args)
    {
        new AddExaminationGUI().setGUI();
    }
}