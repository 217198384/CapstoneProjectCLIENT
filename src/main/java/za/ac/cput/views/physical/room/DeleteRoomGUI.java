/**
 * author: Llewelyn Klaase
 * student no: 216267072
 */
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

public class DeleteRoomGUI extends JFrame implements ActionListener {

    private static OkHttpClient client = new OkHttpClient();

    private JTable table;
    private JPanel pC, pS;
    private JButton btnDelete, btnBack;
    private JLabel lblDelete, blank1, blank2, blank3, blank4;
    private JTextField txtDeleteId;


    public DeleteRoomGUI() {

        super("Delete Rooms");

        table = new JTable();

        pC = new JPanel();
        pS = new JPanel();

        btnDelete = new JButton("Delete");
        btnBack = new JButton("Back");

        lblDelete = new JLabel(" Enter Room Code to Delete: ");
        txtDeleteId = new JTextField();

        blank1 = new JLabel("");
        blank2 = new JLabel("");
        blank3 = new JLabel("");
        blank4 = new JLabel("");
    }

    public void setGUI() {

        pC.setLayout(new GridLayout(1,1));
        pS.setLayout(new GridLayout(4,2));

        pC.add(table);

        pS.add(blank1);
        pS.add(blank2);

        pS.add(lblDelete);
        pS.add(txtDeleteId);

        pS.add(blank3);
        pS.add(blank4);

        pS.add(btnDelete);
        pS.add(btnBack);

        displayTable();

        this.add(pC, BorderLayout.CENTER);
        this.add(pS, BorderLayout.SOUTH);

        btnBack.addActionListener(this);
        btnDelete.addActionListener(this);

        table.setRowHeight(30);
        this.add(new JScrollPane(table));
        this.pack();
        this.setSize(1000, 450);
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

    public boolean request(String id) throws IOException {
        final String URL = "http://localhost:8080/room/delete/" + id;
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
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnDelete) {
            if (!Objects.equals(txtDeleteId.getText(), "")) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Delete Building", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        if(request(txtDeleteId.getText())) {
                            JOptionPane.showMessageDialog(null,"Room Deleted");
                            RoomMainGUI.main(null);
                            this.setVisible(false);
                        } else {
                            JOptionPane.showMessageDialog(null,"Problem, Room Not Deleted");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a value");
            }
        } else if (e.getSource() == btnBack) {
            RoomMainGUI.main(null);
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {

        new DeleteRoomGUI().setGUI();
    }
}
