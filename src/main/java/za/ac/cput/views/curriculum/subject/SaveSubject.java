package za.ac.cput.views.curriculum.subject;

import com.google.gson.Gson;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.entity.curriculum.Subject;
import za.ac.cput.factory.curriculum.SubjectFactory;

import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BorderFactory;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class SaveSubject extends JFrame implements ActionListener {
    public static  final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();

    private JPanel panelNorth, panelCenter, panelSouth;

    private JLabel lblPic, lblHeading;

    private JLabel lblSubjectName;
    private JTextField txtSubjectName;
    private JLabel lblBlank1;

    private JLabel lblLecturerId;
    private JTextField txtLecturerId;
    private JLabel lblBlank2;

    private JLabel lblCourseCode;
    private JTextField txtCourseCode;
    private JLabel lblBlank3;

    private JLabel lblSemesterId;
    private JTextField txtSemesterId;
    private JLabel lblBlank4;

    private JButton btnReset, btnExit, btnSaveInformation;

    private Font ft1, ft2;


    public SaveSubject() {
        super("Subject Section");
        panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();

        lblPic = new JLabel(new ImageIcon("film3.png"));
        lblHeading = new JLabel("Subject");

        lblSubjectName = new JLabel("Subject Name: ");
        txtSubjectName= new JTextField();
        lblBlank1 = new JLabel(" ");

        lblLecturerId = new JLabel("LecturerID: ");
        txtLecturerId = new JTextField();
        lblBlank2 = new JLabel(" ");

        lblCourseCode = new JLabel("Course Code: ");
        txtCourseCode = new JTextField();
        lblBlank3 = new JLabel(" ");


        lblSemesterId = new JLabel("SemesterID: ");
        txtSemesterId = new JTextField();
        lblBlank4 = new JLabel(" ");

        btnSaveInformation = new JButton("Save Information");
        btnReset = new JButton("Reset");
        btnExit = new JButton("Exit");

        ft1 = new Font("Arial", Font.BOLD, 32);
        ft2 = new Font("Arial", Font.BOLD, 15);
    }

    public void reset() {


        txtSubjectName.setText(" ");
        txtLecturerId.setText(" ");
        txtCourseCode.setText(" ");
        txtSemesterId.setText(" ");


    }


    public void setGUI() {
        panelNorth.setLayout(new FlowLayout());
        panelCenter.setLayout(new GridLayout(4, 3));
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


        panelCenter.add(lblSubjectName);
        panelCenter.add(txtSubjectName);
        panelCenter.add(lblBlank4);

        panelCenter.add(lblCourseCode);
        panelCenter.add(txtCourseCode);
        panelCenter.add(lblBlank1);

        panelCenter.add(lblLecturerId);
        panelCenter.add(txtLecturerId);
        panelCenter.add(lblBlank2);

        panelCenter.add(lblSemesterId);
        panelCenter.add(txtSemesterId);
        panelCenter.add(lblBlank3);


        panelSouth.add(btnSaveInformation);
        panelSouth.add(btnReset);
        panelSouth.add(btnExit);

        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnSaveInformation.addActionListener(this);
        btnReset.addActionListener(this);
        btnExit.addActionListener(this);

        this.setSize(600, 600);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource()==btnSaveInformation){
            store(txtSubjectName.getText(),Integer.parseInt(txtLecturerId.getText()),txtCourseCode.getText(),Integer.parseInt(txtSemesterId.getText()));
        }else if(e.getSource()==btnReset){
            reset();

        }
        else if(e.getSource()==btnExit){
            this.dispose();
            new SubjectMenuGUI().setGUI();

        }

    }

    public String post(final String url,String json)throws IOException {

        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .delete()
                .url(url)
                .post(body)
                .build();

        try(Response response = client.newCall(request).execute()){
            return response.body().string();
        }

    }

    public void store(String subjectName,int LecturerId,String courseCode,int semesterid){

        try{

            final String URL ="http://localhost:8080/subject/create";
            Subject sub = SubjectFactory.build(subjectName,LecturerId ,courseCode,semesterid);
            Gson g = new Gson();
            String jsonString = g.toJson(sub);
            String r =post(URL,jsonString);

            if(r!=null)
                JOptionPane.showMessageDialog(null,"Your information is successfully saved.");
            else
                JOptionPane.showMessageDialog(null,"Error");

        }catch (Exception p){
            JOptionPane.showMessageDialog(null,p.getMessage());

        }


    }

    public static void main(String[] args) {
        // TODO code application logic here

        new SaveSubject().setGUI();
    }


}





