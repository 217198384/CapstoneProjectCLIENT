package za.ac.cput.views.physical.building;

import com.google.gson.Gson;
import okhttp3.*;
import za.ac.cput.entity.physical.Building;
import za.ac.cput.factory.physical.BuildingFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AddBuildingGUI extends JFrame implements ActionListener {

    public static final MediaType JSON =
            MediaType.get("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();

    private JLabel lblBuildingID, lblBuildingName, lblBuildingAddress, lblRoomCount;
    private JTextField txtBuildingID, txtBuildingName, txtBuildingAddress, txtRoomCount;
    private JButton btnSave, btnCancel;
    public JPanel pN, pS;

    public AddBuildingGUI() {

        super("Add New Buildings");

        pN = new JPanel();
        pS = new JPanel();

        lblBuildingID = new JLabel("Building ID: ");
        lblBuildingName = new JLabel("Building Name: ");
        lblBuildingAddress = new JLabel("Building Address: ");
        lblRoomCount = new JLabel("Room Count: ");


        txtBuildingID = new JTextField(30);
        txtBuildingName = new JTextField(30);
        txtBuildingAddress = new JTextField(30);
        txtRoomCount = new JTextField(30);

        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");
    }

    public void setGUI() {

        pN.setLayout(new GridLayout(0,2));
        pS.setLayout(new GridLayout(1,2));

        pN.add(lblBuildingID);
        pN.add(txtBuildingID);
        pN.add(lblBuildingName);
        pN.add(txtBuildingName);
        pN.add(lblBuildingAddress);
        pN.add(txtBuildingAddress);
        pN.add(lblRoomCount);
        pN.add(txtRoomCount);

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
            store(txtBuildingID.getText(),
                    txtBuildingName.getText(),
                    txtBuildingAddress.getText(),
                    txtRoomCount.getText());
        } else if (e.getSource() == btnCancel) {
            BuildingMainGUI.main(null);
            this.setVisible(false);
        }
    }

    public void store(String buildingID, String buildingName, String buildingAddress, String roomCountString) {
        try {
            final String URL = "http://localhost:8080/building/create";
            int roomCount = Integer.parseInt(roomCountString);
            Building building = BuildingFactory.build(buildingID, roomCount, buildingName, buildingAddress);
            Gson g = new Gson();
            String jsonString = g.toJson(building);
            String r = post(URL, jsonString);
            if (r != null) {
                JOptionPane.showMessageDialog(null, "Building saved successfully!");
                BuildingMainGUI.main(null);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Oops, Building not saved.");
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

    public static void main(String[] args) {

        new AddBuildingGUI().setGUI();
    }
}
