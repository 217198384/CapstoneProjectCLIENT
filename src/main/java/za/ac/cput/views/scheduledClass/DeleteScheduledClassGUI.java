package za.ac.cput.views.scheduledClass;

/**
 * Dinelle Kotze
 * 219089302
 * DeleteScheduledClassGUI.java
 * This is the Delete Scheduled Class GUI for the Scheduled Class entity.
 */

import com.google.gson.Gson;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.entity.curriculum.ScheduledClass;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class DeleteScheduledClassGUI extends JFrame implements ActionListener
{
    private static OkHttpClient client = new OkHttpClient();

    private JTable table;
    private JPanel panelCenter, panelSouth;
    private JButton btnDelete, btnBack;
    private JLabel lblDelete, blank1, blank2, blank3, blank4;
    private JTextField txtDeleteId;

    public DeleteScheduledClassGUI()
    {
        super("Delete Scheduled Class");
        table = new JTable();

        panelCenter = new JPanel();
        panelSouth = new JPanel();

        btnDelete = new JButton("Delete");
        btnBack = new JButton("Back");

        lblDelete = new JLabel("Enter Scheduled Class ID to Delete: ");
        txtDeleteId = new JTextField();

        blank1 = new JLabel("");
        blank2 = new JLabel("");
        blank3 = new JLabel("");
        blank4 = new JLabel("");
    }

    public void setGUI()
    {
        panelCenter.setLayout(new GridLayout(1,1));
        panelSouth.setLayout(new GridLayout(4,2));

        panelCenter.add(table);

        panelSouth.add(blank1);
        panelSouth.add(blank2);

        panelSouth.add(lblDelete);
        panelSouth.add(txtDeleteId);

        panelSouth.add(blank3);
        panelSouth.add(blank4);

        panelSouth.add(btnDelete);
        panelSouth.add(btnBack);

        displayTable();

        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);

        btnBack.addActionListener(this);
        btnDelete.addActionListener(this);

        table.setRowHeight(30);
        this.add(new JScrollPane(table));
        this.pack();
        this.setSize(1000, 450);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void displayTable()
    {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addColumn("Scheduled Class ID");
        model.addColumn("Subject Code");
        model.addColumn("Room Code");
        model.addColumn("Time");

        try
        {
            final String URL = "http://localhost:8080/scheduledClass/getall";
            String responseBody = run(URL);
            JSONArray scheduledClasses = new JSONArray(responseBody);

            for (int i = 0; i < scheduledClasses.length(); i++)
            {
                JSONObject scheduledClass = scheduledClasses.getJSONObject(i);

                Gson g = new Gson();
                ScheduledClass s = g.fromJson(scheduledClass.toString(), ScheduledClass.class);

                Object[] rowData = new Object[4];
                rowData[0] = s.getScheduledClassId();
                rowData[1] = s.getSubjectCode();
                rowData[2] = s.getRoomCode();
                rowData[3] = s.getClassTime();
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

    public boolean request(String id) throws IOException
    {
        final String URL = "http://localhost:8080/scheduledClass/delete/" + id;
        RequestBody body = RequestBody
                .create( "charset=utf-8", MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .post(body)
                .addHeader("Accept", "application/json")
                .url(URL)
                .build();

        Response response = client.newCall(request).execute();
        return response.isSuccessful();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnDelete)
        {
            if (!Objects.equals(txtDeleteId.getText(), ""))
            {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Delete Scheduled Class", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION)
                {
                    try
                    {
                        if(request(txtDeleteId.getText()))
                        {
                            JOptionPane.showMessageDialog(null,"Scheduled Class Deleted");
                            ScheduledClassMainGUI.main(null);
                            this.setVisible(false);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"Problem, Scheduled Class Not Deleted");
                        }
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Please enter a value");
            }
        }
        else if (e.getSource() == btnBack) {
            ScheduledClassMainGUI.main(null);
            this.setVisible(false);
        }
    }

    public static void main(String[] args)
    {
        new DeleteScheduledClassGUI().setGUI();
    }
}
