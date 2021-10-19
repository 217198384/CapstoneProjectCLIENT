package za.ac.cput.views.curriculum.examination;

/**
 * Dinelle Kotze
 * 219089302
 * ViewExaminationGUI.java
 * This is the View Examination GUI for the Examination entity.
 */

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.entity.curriculum.Examination;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ViewExaminationGUI extends JFrame implements ActionListener
{
    private static OkHttpClient client = new OkHttpClient();

    private JTable table;
    private JPanel panelCenter, panelSouth;
    private JButton btnBack;

    public ViewExaminationGUI()
    {
        super("All Examinations");
        table = new JTable();

        panelCenter = new JPanel();
        panelSouth = new JPanel();

        btnBack = new JButton("Back");
    }

    public void setGUI()
    {
        panelCenter.setLayout(new GridLayout(1,1));
        panelSouth.setLayout(new GridLayout(1,1));

        panelCenter.add(table);
        panelSouth.add(btnBack);

        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);

        btnBack.addActionListener(this);

        getAll();
        table.setRowHeight(30);
        this.add(new JScrollPane(table));
        this.pack();
        this.setSize(1000, 450);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void getAll()
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
        try (Response response = client.newCall(request).execute())
        {
            return response.body().string();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnBack)
        {
            ExaminationMainGUI.main(null);
            this.setVisible(false);
        }
    }

    public static void main(String[] args)
    {
        new ViewExaminationGUI().setGUI();
    }
}