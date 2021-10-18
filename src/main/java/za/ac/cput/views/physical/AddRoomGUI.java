package za.ac.cput.views.physical;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        switch (e.getActionCommand()) {
            case "Save":
                break;
            case "Cancel":
                RoomMainGUI.main(null);
                this.setVisible(false);
                break;
            case "Add ":
                break;
        }
    }

    public static void main(String[] args) {

        new AddRoomGUI().setGUI();
    }
}
