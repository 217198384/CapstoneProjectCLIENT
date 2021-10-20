/**
 * author: Llewelyn Klaase
 * student no: 216267072
 */
package za.ac.cput.views.physical.room;

import com.google.gson.Gson;
import okhttp3.*;
import za.ac.cput.entity.physical.Room;
import za.ac.cput.factory.physical.RoomFactory;
import za.ac.cput.views.physical.building.BuildingMainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AddRoomGUI extends JFrame implements ActionListener {

    public static final MediaType JSON =
            MediaType.get("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();

    private JLabel lblRoomCode, lblRoomType, lblRoomCapacity, lblRoomFloor, lblBuildingID;
    private JTextField txtRoomCode, txtRoomType, txtRoomCapacity, txtRoomFloor, txtBuildingID;
    private JButton btnSave, btnCancel;
    public JPanel pN, pS;

    public AddRoomGUI() {

        super("Add New Rooms");

        pN = new JPanel();
        pS = new JPanel();

        lblRoomCode = new JLabel("Room Code: ");
        lblRoomType = new JLabel("Room Type: ");
        lblRoomCapacity = new JLabel("Room Capacity: ");
        lblRoomFloor = new JLabel("Room Floor: ");
        lblBuildingID = new JLabel("Building ID: ");

        txtRoomCode = new JTextField(30);
        txtRoomType = new JTextField(30);
        txtRoomCapacity = new JTextField(30);
        txtRoomFloor = new JTextField(30);
        txtBuildingID = new JTextField(30);

        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");
    }

    public void setGUI() {

        pN.setLayout(new GridLayout(0,2));
        pS.setLayout(new GridLayout(1,2));

        pN.add(lblRoomCode);
        pN.add(txtRoomCode);
        pN.add(lblRoomType);
        pN.add(txtRoomType);
        pN.add(lblRoomCapacity);
        pN.add(txtRoomCapacity);
        pN.add(lblRoomFloor);
        pN.add(txtRoomFloor);
        pN.add(lblBuildingID);
        pN.add(txtBuildingID);

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
            store(txtRoomCode.getText(),
                    txtRoomType.getText(),
                    txtRoomCapacity.getText(),
                    txtRoomFloor.getText(),
                    txtBuildingID.getText());
        } else if (e.getSource() == btnCancel) {
            RoomMainGUI.main(null);
            this.setVisible(false);
        }
    }

    public void store(String roomCode, String roomType, String stringRoomCapacity, String stringRoomFloor, String stringBuildingID) {
        try {
            final String URL = "http://localhost:8080/room/create";
            int roomCapacity = Integer.parseInt(stringRoomCapacity);
            int roomFloor = Integer.parseInt(stringRoomFloor);
            int buildingID = Integer.parseInt(stringBuildingID);
            Room room = RoomFactory.build(roomCode, roomType, roomCapacity, roomFloor, buildingID);
            Gson g = new Gson();
            String jsonString = g.toJson(room);
            String r = post(URL, jsonString);
            if (r != null) {
                JOptionPane.showMessageDialog(null, "Building saved successfully!");
                RoomMainGUI.main(null);
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

        new AddRoomGUI().setGUI();
    }
}
