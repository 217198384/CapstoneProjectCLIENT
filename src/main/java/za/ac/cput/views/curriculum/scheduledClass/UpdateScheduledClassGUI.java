package za.ac.cput.views.curriculum.scheduledClass;

/**
 * Dinelle Kotze
 * 219089302
 * UpdateScheduledClassGUI.java
 * This is the Update Scheduled Class GUI for the Scheduled Class entity.
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

public class UpdateScheduledClassGUI extends JFrame implements ActionListener
{
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();

    private JTable table;
    private JPanel panelNorth, panelCenter, panelSouth, panelFields, panelUpdate;
    private JButton btnUpdate, btnBack, btnEnter;
    private JLabel lblUpdate, blank1, blank2, blank3, blank4,
            blank5, blank6, blank7, blank8,
            lblSubjectCode, lblRoomCode, lblTime;
    private JTextField txtUpdateId, txtSubjectCode,
            txtRoomCode, txtTime;

    public UpdateScheduledClassGUI()
    {
        super("Update Scheduled Class");
        table = new JTable();

        panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();
        panelUpdate = new JPanel();
        panelFields = new JPanel();

        btnUpdate = new JButton("Update");
        btnBack = new JButton("Back");
        btnEnter = new JButton("Enter");

        lblUpdate = new JLabel("Enter Scheduled Class ID to Update: ", SwingConstants.CENTER);
        txtUpdateId = new JTextField();

        lblSubjectCode = new JLabel("Subject Code: ", SwingConstants.CENTER);
        lblRoomCode = new JLabel("Room Code: ", SwingConstants.CENTER);
        lblTime = new JLabel("Time: ", SwingConstants.CENTER);

        txtSubjectCode = new JTextField();
        txtRoomCode = new JTextField();
        txtTime = new JTextField();

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

        panelFields.add(lblSubjectCode);
        panelFields.add(txtSubjectCode);
        panelFields.add(lblRoomCode);
        panelFields.add(txtRoomCode);
        panelFields.add(lblTime);
        panelFields.add(txtTime);

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

    private ScheduledClass getScheduledClass(String id) throws IOException
    {
        ScheduledClass scheduledClass = null;
        try
        {
            final String URL = "http://localhost:8080/scheduledClass/read/" + id;
            String responseBody = run(URL);
            Gson gson = new Gson();
            scheduledClass = gson.fromJson(responseBody, ScheduledClass.class);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        System.out.println(scheduledClass);
        return scheduledClass;
    }

    public void store(String subjectCode, String roomCode, String time)
    {
        ScheduledClass scheduledClass = null;

        try
        {
            final String URL = "http://localhost:8080/scheduledClass/update";

            scheduledClass = new ScheduledClass.Builder()
                    .setScheduledClassId(txtUpdateId.getText())
                    .setSubjectCode(Integer.parseInt(subjectCode))
                    .setRoomCode(Integer.parseInt(roomCode))
                    .setClassTime(time)
                    .build();

            Gson g = new Gson();
            String jsonString = g.toJson(scheduledClass);
            String r = post(URL, jsonString);
            System.out.println(r);
            if (r != null)
            {
                JOptionPane.showMessageDialog(null, "Scheduled Class updated successfully!");
                ScheduledClassMainGUI.main(null);
                this.setVisible(false);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Oops, Scheduled Class not updated.");
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        System.out.println(scheduledClass);
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
                        ScheduledClass scheduledClass = getScheduledClass(txtUpdateId.getText());
                        if (scheduledClass != null)
                        {
                            panelFields.setVisible(true);
                            txtSubjectCode.setText(String.valueOf(scheduledClass.getSubjectCode()));
                            txtRoomCode.setText(String.valueOf(scheduledClass.getRoomCode()));
                            txtTime.setText(scheduledClass.getClassTime());
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "No Scheduled Class with that ID");
                        }
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Please enter a valid Scheduled Class ID");
                }
                break;
            case "Update" :
                store(txtSubjectCode.getText(),
                        txtRoomCode.getText(),
                        txtTime.getText());
                break;
            case "Back" :
                ScheduledClassMainGUI.main(null);
                this.setVisible(false);
                break;
        }
    }

    public static void main(String[] args)
    {
        new UpdateScheduledClassGUI().setGUI();
    }
}
