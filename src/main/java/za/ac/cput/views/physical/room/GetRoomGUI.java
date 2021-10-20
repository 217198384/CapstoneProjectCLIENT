package za.ac.cput.views.physical.room;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.entity.physical.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GetRoomGUI extends JFrame implements ActionListener {

    private static OkHttpClient client = new OkHttpClient();

    private JTable table;
    private JPanel pC, pS;
    private JButton btnBack;

    public GetRoomGUI() {
        super("All Rooms");
        table = new JTable();

        pC = new JPanel();
        pS = new JPanel();

        btnBack = new JButton("Back");
    }

    public void setGUI() {
        pC.setLayout(new GridLayout(1,1));
        pS.setLayout(new GridLayout(1,1));

        pC.add(table);
        pS.add(btnBack);

        this.add(pC, BorderLayout.CENTER);
        this.add(pS, BorderLayout.SOUTH);

        btnBack.addActionListener(this);

        getAll();
        table.setRowHeight(30);
        this.add(new JScrollPane(table));
        this.pack();
        this.setSize(1000, 450);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void getAll() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addColumn("Room Code");
        model.addColumn("Room Type");
        model.addColumn("Room Capacity");
        model.addColumn("Room Floor");
        model.addColumn("Building ID");

        try {
            final String URL = "http://localhost:8080/room/getalllect";
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            RoomMainGUI.main(null);
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {

        new GetRoomGUI().setGUI();
    }
}
