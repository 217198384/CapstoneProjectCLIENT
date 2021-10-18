package za.ac.cput.views.physical;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        lblRoomCount = new JLabel("Building Address: ");


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

        switch(e.getActionCommand()){
            case "Save":
                break;
            case "Cancel":
                BuildingMainGUI.main(null);
                this.setVisible(false);
                break;
        }
    }

    public static void main(String[] args) {

        new AddBuildingGUI().setGUI();
    }
}
