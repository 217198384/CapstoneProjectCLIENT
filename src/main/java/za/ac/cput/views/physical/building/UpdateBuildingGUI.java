package za.ac.cput.views.physical.building;

import com.google.gson.Gson;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.entity.physical.Building;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class UpdateBuildingGUI extends JFrame implements ActionListener {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();

    private JTable table;
    private JPanel pN, pC, pS, pFields, pUpdate;
    private JButton btnUpdate, btnBack, btnEnter;
    private JLabel lblUpdate, blank1, blank2, blank3, blank4,
            blank5, blank6, blank7, blank8,
            lblBuildingID, lblBuildingName, lblBuildingAddress, lblRoomCount;
    private JTextField txtUpdateId, txtBuildingID, txtBuildingName,
            txtBuildingAddress, txtRoomCount;

    public UpdateBuildingGUI() {
        super("Update Buildings");
        table = new JTable();

        pN = new JPanel();
        pC = new JPanel();
        pS = new JPanel();
        pUpdate = new JPanel();
        pFields = new JPanel();

        btnUpdate = new JButton("Update");
        btnBack = new JButton("Back");
        btnEnter = new JButton("Enter");

        lblUpdate = new JLabel("Enter Building ID to Update: ", SwingConstants.CENTER);
        txtUpdateId = new JTextField();

        lblBuildingID = new JLabel("Building ID: ", SwingConstants.CENTER);
        lblBuildingName = new JLabel("Building Name: ", SwingConstants.CENTER);
        lblBuildingAddress = new JLabel("Building Address: ", SwingConstants.CENTER);
        lblRoomCount = new JLabel("Room Count: ", SwingConstants.CENTER);

        txtBuildingID = new JTextField();
        txtBuildingName = new JTextField();
        txtBuildingAddress = new JTextField();
        txtRoomCount = new JTextField();

        blank1 = new JLabel("");
        blank2 = new JLabel("");
        blank3 = new JLabel("");
        blank4 = new JLabel("");
        blank5 = new JLabel("");
        blank6 = new JLabel("");
        blank7 = new JLabel("");
        blank8 = new JLabel("");
    }

    public void setGUI() {
        pN.setLayout(new GridLayout(0,1));
        pC.setLayout(new GridLayout(0,1));
        pUpdate.setLayout(new GridLayout(3,3));
        pFields.setLayout(new GridLayout(5,2));
        pS.setLayout(new GridLayout(2,2));

        pN.add(new JScrollPane(table));

        pUpdate.add(blank1);
        pUpdate.add(blank2);
        pUpdate.add(blank3);
        pUpdate.add(lblUpdate);
        pUpdate.add(txtUpdateId);
        pUpdate.add(btnEnter);
        pUpdate.add(blank4);
        pUpdate.add(blank5);

        pFields.add(lblBuildingID);
        pFields.add(txtBuildingID);
        pFields.add(lblBuildingName);
        pFields.add(txtBuildingName);
        pFields.add(lblBuildingAddress);
        pFields.add(txtBuildingAddress);
        pFields.add(lblRoomCount);
        pFields.add(txtRoomCount);

        pC.add(pUpdate);
        pC.add(pFields);

        pS.add(blank7);
        pS.add(blank8);
        pS.add(btnUpdate);
        pS.add(btnBack);

        displayTable();
        pFields.setVisible(false);

        this.add(pN, BorderLayout.NORTH);
        this.add(pC, BorderLayout.CENTER);
        this.add(pS, BorderLayout.SOUTH);

        btnEnter.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnBack.addActionListener(this);

        table.setRowHeight(30);
        this.pack();
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void displayTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addColumn("Building ID");
        model.addColumn("Building Name");
        model.addColumn("Building Address");
        model.addColumn("Room Count");

        try {
            final String URL = "http://localhost:8080/building/getalllect";
            String responseBody = run(URL);
            JSONArray buildings = new JSONArray(responseBody);

            for (int i = 0; i < buildings.length(); i++) {
                JSONObject building = buildings.getJSONObject(i);

                Gson g = new Gson();
                Building b = g.fromJson(building.toString(), Building.class);

                Object[] rowData = new Object[4];
                rowData[0] = b.getBuildingID();
                rowData[1] = b.getBuildingName();
                rowData[2] = b.getBuildingAddress();
                rowData[3] = b.getRoomCount();
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

    private Building getBuilding(String id) throws IOException {
        Building building = null;
        try {
            final String URL = "http://localhost:8080/buiding/readlect/" + id;
            String responseBody = run(URL);
            Gson gson = new Gson();
            building = gson.fromJson(responseBody, Building.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(building);
        return building;
    }

    public void store(String buildingID, String buildingName, String buildingAddress, String stringRoomCount) {
        Building building = null;
        try {
            final String URL = "http://localhost:8080/building/updatelect";
            int roomCount = Integer.parseInt(stringRoomCount);
            building = new Building.BuildingBuilder()
                    .setBuildingID(txtUpdateId.getText())
                    .setBuildingName(txtBuildingName.getText())
                    .setBuildingAddress(txtBuildingAddress.getText())
                    .setRoomCount(Integer.parseInt(txtRoomCount.getText()))
                    .build();
            Gson g = new Gson();
            String jsonString = g.toJson(building);
            String r = post(URL, jsonString);
            System.out.println(r);
            if (r != null) {
                JOptionPane.showMessageDialog(null, "Building updated successfully!");
                BuildingMainGUI.main(null);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Oops, Student not updated.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        System.out.println(building);
    }

    public String post(final String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder().url(url).post(body).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Enter" :
                if (!Objects.equals(txtUpdateId.getText(), "")) {
                    try {
                        Building b = getBuilding(null);
                        if(b != null) {
                            pFields.setVisible(true);
                            txtBuildingID.setText(b.getBuildingID());
                            txtBuildingName.setText(b.getBuildingName());
                            txtBuildingAddress.setText(b.getBuildingAddress());
                            txtRoomCount.setText(String.valueOf(b.getRoomCount()));
                        } else {
                            JOptionPane.showMessageDialog(null, "No Student with that ID");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid Student ID");
                }
                break;
            case "Update" :
                store(txtBuildingID.getText(),
                        txtBuildingName.getText(),
                        txtBuildingAddress.getText(),
                        txtRoomCount.getText());
                break;
            case "Back" :
                BuildingMainGUI.main(null);
                this.setVisible(false);
                break;
        }
    }

    public static void main(String[] args) {

        new UpdateBuildingGUI().setGUI();
    }
}
