package za.ac.cput.views.scheduledClass;

import com.google.gson.Gson;
import okhttp3.*;
import za.ac.cput.entity.curriculum.ScheduledClass;
import za.ac.cput.factory.curriculum.ScheduledClassFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AddScheduledClassGUI extends JFrame implements ActionListener
{
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();

    private JLabel lblSubjectCode, lblRoomCode, lblTime;
    private JTextField txtSubjectCode, txtRoomCode, txtTime;
    private JButton btnSave, btnCancel;
    public JPanel panelNorth, panelSouth;

    public AddScheduledClassGUI()
    {
        super("Add new Scheduled Class");

        panelNorth = new JPanel();
        panelSouth = new JPanel();

        lblRoomCode = new JLabel("Room Code: ");
        lblSubjectCode = new JLabel("Subject Code: ");
        lblTime = new JLabel("Time: ");

        txtRoomCode = new JTextField(30);
        txtSubjectCode = new JTextField(30);
        txtTime = new JTextField(30);

        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");
    }

    public void setGUI()
    {
        panelNorth.setLayout(new GridLayout(0,2));
        panelSouth.setLayout(new GridLayout(1,2));

        panelNorth.add(lblSubjectCode);
        panelNorth.add(txtSubjectCode);
        panelNorth.add(lblRoomCode);
        panelNorth.add(txtRoomCode);
        panelNorth.add(lblTime);
        panelNorth.add(txtTime);

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
            store(txtSubjectCode.getText(),
                    txtRoomCode.getText(),
                    txtTime.getText());
        }
        else if (e.getSource() == btnCancel)
        {
            ScheduledClassMainGUI.main(null);
            this.setVisible(false);
        }
    }

    public void store(String subjectCode, String roomCode, String time)
    {
        try
        {
            final String URL = "http://localhost:8080/scheduledClass/create";
            ScheduledClass scheduledClass = ScheduledClassFactory.build(Integer.parseInt(subjectCode), Integer.parseInt(roomCode), time);
            Gson g = new Gson();
            String jsonString = g.toJson(scheduledClass);
            String r = post(URL, jsonString);

            if (r != null)
            {
                JOptionPane.showMessageDialog(null, "Scheduled Class saved successfully!");
                ScheduledClassMainGUI.main(null);
                this.setVisible(false);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Oops, Scheduled Class not saved.");
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
        new AddScheduledClassGUI().setGUI();
    }
}