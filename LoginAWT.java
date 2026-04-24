import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginAWT extends Frame implements ActionListener {

    TextField tfUser, tfPass;
    Button btnLogin;
    Label msg;

    LoginAWT() {
        setTitle("Railway Login");

        setLayout(new GridLayout(4,2,10,10));

        add(new Label("Username:"));
        tfUser = new TextField();
        add(tfUser);

        add(new Label("Password:"));
        tfPass = new TextField();
        tfPass.setEchoChar('*');
        add(tfPass);

        btnLogin = new Button("Login");
        add(btnLogin);

        msg = new Label("");
        add(msg);

        btnLogin.addActionListener(this);

        setSize(300,200);
        setVisible(true);

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {

        try {
            Connection con = DBConnection.getConnection();

            String q = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(q);

            ps.setString(1, tfUser.getText());
            ps.setString(2, tfPass.getText());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                msg.setText("Login Successful");
                new RailwayAWT();
                dispose();
            } else {
                msg.setText("Invalid Username or Password");
            }

            con.close();

        } catch (Exception ex) {
            msg.setText("Error: " + ex.getMessage());
        }
    }
}