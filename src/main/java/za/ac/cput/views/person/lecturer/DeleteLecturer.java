package za.ac.cput.views.person.lecturer;


import okhttp3.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class DeleteLecturer extends JFrame implements ActionListener {

    private static OkHttpClient client = new OkHttpClient();

    private JPanel panelNorth, panelCenter, panelSouth;

    private JLabel lblPic, lblHeading;


    private JLabel lblLecturerID;
    private JTextField txtLecturerId;
    private JLabel lblBlank;


    private JButton btnReset, btnExit, btnDelete;

    private Font ft1, ft2;


    public DeleteLecturer() {
        super("Student Enrollment");
        panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();

        lblPic = new JLabel(new ImageIcon("film3.png"));
        lblHeading = new JLabel("Lecturer Details");

        lblLecturerID = new JLabel("Enter your ID to delete: ");
        txtLecturerId= new JTextField();
        lblBlank = new JLabel(" ");

        btnDelete = new JButton("Delete ");
        btnReset = new JButton("Reset");
        btnExit = new JButton("Exit");

        ft1 = new Font("Arial", Font.BOLD, 32);
        ft2 = new Font("Arial", Font.BOLD, 15);
    }

    public void reset() {

        txtLecturerId.setText(" ");

    }


    public void setGUI() {
        panelNorth.setLayout(new FlowLayout());
        panelCenter.setLayout(new GridLayout(1, 3));
        panelSouth.setLayout(new GridLayout(1, 3));
        panelCenter.setBorder(BorderFactory.createEmptyBorder(100, 0, 20, 0));

        lblHeading.setFont(ft1);
        lblHeading.setForeground(Color.RED);

        panelNorth.setBackground(Color.YELLOW);
        panelCenter.setBackground(Color.GREEN);

        btnDelete.setBackground(Color.BLUE);
        btnDelete.setForeground(Color.WHITE);
        btnReset.setBackground(Color.BLACK);
        btnReset.setForeground(Color.WHITE);


        panelNorth.add(lblPic);
        panelNorth.add(lblHeading);

        panelCenter.add(lblLecturerID);
        panelCenter.add(txtLecturerId);
        panelCenter.add(lblBlank);



        panelSouth.add(btnDelete);
        panelSouth.add(btnReset);
        panelSouth.add(btnExit);

        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnDelete.addActionListener(this);
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

    public boolean request(String id) throws IOException {
        final String URL = "http://localhost:8080/lecturer/delete/" + id;
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

                int result = JOptionPane.showConfirmDialog(null, "Confirm your deletion?", "Deleting Lecturer", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        if(request(txtLecturerId.getText())) {
                            JOptionPane.showMessageDialog(null,"Your Lecturer info was deleted successfully");
                            LecturerMenuGUI.main(null);
                            this.setVisible(false);
                        } else {
                            JOptionPane.showMessageDialog(null,"Error: cannot delete lecturer");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
             else {
                JOptionPane.showMessageDialog(null, "Please enter a value");
            }
        } else if (e.getSource() == btnExit) {
          LecturerMenuGUI.main(null);
            this.setVisible(false);
        }

    }

    public static void main(String[] args) {
        // TODO code application logic here

        new DeleteLecturer().setGUI();
    }


}







