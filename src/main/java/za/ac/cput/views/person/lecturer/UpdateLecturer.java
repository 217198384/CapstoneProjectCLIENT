package za.ac.cput.views.person.lecturer;

import com.google.gson.Gson;
import okhttp3.*;
import za.ac.cput.entity.person.Lecturer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UpdateLecturer extends JFrame implements ActionListener {
    public static  final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();

    private JPanel panelNorth, panelCenter, panelSouth;

    private JLabel lblPic, lblHeading;

    private JLabel lblUpdate;
    private JTextField txtUpdate;
    private  JButton btnSearch;


    private JLabel lblFirstName;
    private JTextField txtFirstName;
    private JLabel lblBlank;

    private JLabel lblLastName;
    private JTextField txtLastName;
    private JLabel lblBlank1;

    private JLabel lblAge;
    private JTextField txtAge;
    private JLabel lblBlank2;

    private JLabel lblEmailAddress;
    private JTextField txtEmailAddress;
    private JLabel lblBlank3;

    private JLabel lblContactNumber;
    private JTextField txtContactNumber;
    private JLabel lblBlank4;

    private JButton btnReset, btnExit, btnSaveInformation;

    private Font ft1, ft2;


    public UpdateLecturer() {
        super("Student Enrollment");
        panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();

        lblPic = new JLabel(new ImageIcon("film3.png"));
        lblHeading = new JLabel("Lecturer Details");

        lblUpdate= new JLabel("Please insert your Lecturer ID:");
        txtUpdate= new JTextField();
        btnSearch= new JButton("Search");


        lblFirstName = new JLabel("Enter your first name: ");
        txtFirstName= new JTextField();
        lblBlank = new JLabel(" ");

        lblLastName = new JLabel("Enter your last name: ");
        txtLastName= new JTextField();
        lblBlank1 = new JLabel(" ");

        lblAge = new JLabel("Enter your age: ");
        txtAge = new JTextField();
        lblBlank2 = new JLabel(" ");

        lblEmailAddress = new JLabel("Enter your email address: ");
        txtEmailAddress = new JTextField();
        lblBlank3 = new JLabel(" ");


        lblContactNumber = new JLabel("Enter your contact number: ");
        txtContactNumber = new JTextField();
        lblBlank4 = new JLabel(" ");

        btnSaveInformation = new JButton("Update");
        btnReset = new JButton("Reset");
        btnExit = new JButton("Exit");

        ft1 = new Font("Arial", Font.BOLD, 32);
        ft2 = new Font("Arial", Font.BOLD, 15);
    }




    public void setGUI() {
        panelNorth.setLayout(new FlowLayout());
        panelCenter.setLayout(new GridLayout(6, 3));
        panelSouth.setLayout(new GridLayout(1, 3));
        panelCenter.setBorder(BorderFactory.createEmptyBorder(100, 0, 20, 0));

        lblHeading.setFont(ft1);
        lblHeading.setForeground(Color.RED);

        panelNorth.setBackground(Color.YELLOW);
        panelCenter.setBackground(Color.GREEN);

        btnSaveInformation.setBackground(Color.BLUE);
        btnSaveInformation.setForeground(Color.WHITE);
        btnReset.setBackground(Color.BLACK);
        btnReset.setForeground(Color.WHITE);


        panelNorth.add(lblPic);
        panelNorth.add(lblHeading);

        panelCenter.add(lblUpdate);
        panelCenter.add(txtUpdate);
        panelCenter.add(btnSearch);

        panelCenter.add(lblFirstName);
        panelCenter.add(txtFirstName);
        panelCenter.add(lblBlank);

        panelCenter.add(lblLastName);
        panelCenter.add(txtLastName);
        panelCenter.add(lblBlank4);

        panelCenter.add(lblAge);
        panelCenter.add(txtAge);
        panelCenter.add(lblBlank2);

        panelCenter.add(lblEmailAddress);
        panelCenter.add(txtEmailAddress);
        panelCenter.add(lblBlank1);


        panelCenter.add(lblContactNumber);
        panelCenter.add(txtContactNumber);
        panelCenter.add(lblBlank3);





        panelSouth.add(btnSaveInformation);
        panelSouth.add(btnReset);
        panelSouth.add(btnExit);

        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
btnSearch.addActionListener(this);
        btnSaveInformation.addActionListener(this);
        btnReset.addActionListener(this);
        btnExit.addActionListener(this);

        this.setSize(600, 600);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }



    private static String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
    private Lecturer getLecturer(String id) throws IOException {
        Lecturer lect = null;
        try {
            final String URL = "http://localhost:8080/lecturer/read/" + id;
            String responseBody = run(URL);
            Gson gson = new Gson();
            lect = gson.fromJson(responseBody,Lecturer.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(lect);
        return lect;
    }

    public String post(final String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder().url(url).post(body).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public void store(String firtsName,String lastName,String Age,String emailAddress,String contactNumber){
        Lecturer lect = null;
        try {
            final String URL = "http://localhost:8080/lecturer/update";
            int age = Integer.parseInt(String.valueOf(Age));
            lect= new Lecturer.LecturerBuilder()
                    .setlecturerID(txtUpdate.getText())
                    .setfirstName(txtFirstName.getText())
                    .setlastName(txtLastName.getText())
                    .setage(Integer.parseInt(txtAge.getText()))
                    .setemailAddress(txtEmailAddress.getText())
                    .setcontactNo(txtContactNumber.getText())
                    .build();
            Gson g = new Gson();
            String jsonString = g.toJson(lect);
            String r = post(URL, jsonString);
            System.out.println(r);
            if (r != null) {
                JOptionPane.showMessageDialog(null, "Lecturer has been updated successfully!");
                LecturerMenuGUI.main(null);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Error: cannot update lecturer's information.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        System.out.println(lect);
       

    }



    public void reset() {
        txtUpdate.setText("");
        txtFirstName.setText(" ");
        txtLastName.setText(" ");
        txtAge.setText(" ");
        txtEmailAddress.setText(" ");
        txtContactNumber.setText(" ");


    }
    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource()==btnSearch) {
            try {
                Lecturer le = getLecturer(txtUpdate.getText());
                if (le != null) {
                    txtFirstName.setText(le.getFirstName());
                    txtLastName.setText(le.getLastName());
                    txtAge.setText(String.valueOf(le.getAge()));
                    txtEmailAddress.setText(le.getEmailAddress());
                    txtContactNumber.setText(le.getContactNo());
                } else {
                    JOptionPane.showMessageDialog(null, "Error, cannot search for your ID");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }


        }else  if(e.getSource()==btnSaveInformation){

            store(txtFirstName.getText(),
                    txtLastName.getText(),
                    txtAge.getText(),
                    txtEmailAddress.getText(),
                    txtContactNumber.getText());
    }else if(e.getSource()==btnReset){
            reset();
        }

        else if(e.getSource()==btnExit){
            this.dispose();
            new LecturerMenuGUI().setGUI();
        }



    }

    public static void main(String[] args) {
        // TODO code application logic here

        new UpdateLecturer().setGUI();
    }


}







