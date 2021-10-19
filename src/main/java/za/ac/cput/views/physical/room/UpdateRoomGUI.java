package za.ac.cput.views.physical.room;

import com.google.gson.Gson;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.entity.physical.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class UpdateRoomGUI extends JFrame implements ActionListener {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();

    private JTable table;
    private JPanel pN, pC, pS, pFields, pUpdate;
    private JButton btnUpdate, btnBack, btnEnter;
    private JLabel lblUpdate, blank1, blank2, blank3, blank4,
            blank5, blank6, blank7, blank8,
            lblRoomCode, lblRoomType, lblRoomCapacity, lblRoomFloor, lblBuildingID;
    private JTextField txtUpdateId, txtRoomCode,
            txtRoomType, txtRoomCapacity, txtRoomFloor, txtBuildingID;

    public UpdateRoomGUI() {
        super("Update Rooms");
        table = new JTable();

        pN = new JPanel();
        pC = new JPanel();
        pS = new JPanel();
        pUpdate = new JPanel();
        pFields = new JPanel();

        btnUpdate = new JButton("Update");
        btnBack = new JButton("Back");
        btnEnter = new JButton("Enter");

        lblUpdate = new JLabel("Enter Room Code to Update: ", SwingConstants.CENTER);
        txtUpdateId = new JTextField();

        lblRoomCode = new JLabel("Room Code: ", SwingConstants.CENTER);
        lblRoomType = new JLabel("Room Type: ", SwingConstants.CENTER);
        lblRoomCapacity = new JLabel("Room Capacity: ", SwingConstants.CENTER);
        lblRoomFloor = new JLabel("Room Floor: ", SwingConstants.CENTER);
        lblBuildingID = new JLabel("Building ID: ", SwingConstants.CENTER);

        txtRoomCode = new JTextField();
        txtRoomType = new JTextField();
        txtRoomCapacity = new JTextField();
        txtRoomFloor = new JTextField();
        txtBuildingID = new JTextField();

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

        pFields.add(lblRoomCode);
        pFields.add(txtRoomCode);
        pFields.add(lblRoomType);
        pFields.add(txtRoomType);
        pFields.add(lblRoomCapacity);
        pFields.add(txtRoomCapacity);
        pFields.add(lblRoomFloor);
        pFields.add(txtRoomFloor);
        pFields.add(lblBuildingID);
        pFields.add(txtBuildingID);

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
        model.addColumn("Room Code");
        model.addColumn("Room Type");
        model.addColumn("Room Capacity");
        model.addColumn("Room Floor");
        model.addColumn("Building ID");

        try {
            final String URL = "http://localhost:8080/room/getall";
            String responseBody = run(URL);
            JSONArray rooms = new JSONArray(responseBody);

            for (int i = 0; i < rooms.length(); i++) {
                JSONObject room = rooms.getJSONObject(i);

                Gson g = new Gson();
                Room r = g.fromJson(room.toString(), Room.class);

                Object[] rowData = new Object[5];
                rowData[0] = r.getRoomCode();
                rowData[1] = r.getRoomType();
                rowData[2] = r.getRoomCapacity();
                rowData[3] = r.getRoomFloor();
                rowData[4] = r.getBuildingID();
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

    private Room getRoom(String id) throws IOException {
        Room room = null;
        try {
            final String URL = "http://localhost:8080/room/read/" + id;
            String responseBody = run(URL);
            Gson gson = new Gson();
            room = gson.fromJson(responseBody, Room.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(room);
        return room;
    }

    public void store(String roomCode, String roomType, String stringRoomCapacity, String stringRoomFloor, String stringBuildingID) {
        Room room = null;
        try {
            final String URL = "http://localhost:8080/room/update";
            int roomCapacity = Integer.parseInt(stringRoomCapacity);
            int buildingID = Integer.parseInt(stringBuildingID);
            int roomFloor = Integer.parseInt(stringRoomFloor);
            room = new Room.RoomBuilder()
                    .setRoomCode(txtUpdateId.getText())
                    .setRoomType(txtRoomType.getText())
                    .setRoomCapacity(Integer.parseInt(txtRoomCapacity.getText()))
                    .setRoomFloor(Integer.parseInt(txtRoomFloor.getText()))
                    .setBuildingID(Integer.parseInt(txtBuildingID.getText()))
                    .build();
            Gson g = new Gson();
            String jsonString = g.toJson(room);
            String r = post(URL, jsonString);
            System.out.println(r);
            if (r != null) {
                JOptionPane.showMessageDialog(null, "Room updated successfully!");
                RoomMainGUI.main(null);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Oops, Room not updated.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        System.out.println(room);
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
                        Room r = getRoom(txtUpdateId.getText());
                        if(r != null) {
                            pFields.setVisible(true);
                            txtRoomCode.setText(r.getRoomCode());
                            txtRoomType.setText(r.getRoomType());
                            txtRoomCapacity.setText(String.valueOf(r.getRoomCapacity()));
                            txtRoomFloor.setText(String.valueOf(r.getRoomFloor()));
                            txtBuildingID.setText(String.valueOf(r.getBuildingID()));
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
                store(txtRoomCode.getText(),
                        txtRoomType.getText(),
                        txtRoomCapacity.getText(),
                        txtRoomFloor.getText(),
                        txtBuildingID.getText());
                break;
            case "Back" :
                RoomMainGUI.main(null);
                this.setVisible(false);
                break;
        }
    }

    public static void main(String[] args) {

        new UpdateRoomGUI().setGUI();
    }
}
