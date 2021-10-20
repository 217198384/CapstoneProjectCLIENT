/**
 * author: Llewelyn Klaase
 * student no: 216267072
 */
package za.ac.cput.views.physical.building;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.entity.physical.Building;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GetBuildingGUI extends JFrame implements ActionListener {

    private static OkHttpClient client = new OkHttpClient();

    private JTable table;
    private JPanel pC, pS;
    private JButton btnBack;

    public GetBuildingGUI() {
        super("All Buildings");
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
        model.addColumn("Building ID");
        model.addColumn("Building Name");
        model.addColumn("Building Address");
        model.addColumn("Room Count");

        try {
            final String URL = "http://localhost:8080/building/getall";
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            BuildingMainGUI.main(null);
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {

        new GetBuildingGUI().setGUI();
    }
}
