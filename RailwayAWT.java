import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RailwayAWT extends Frame implements ActionListener {

    TextField tfPNR, tfName, tfAge, tfTrain;
    Choice genderChoice;
    TextArea ta;
    Button book, view, cancel;

    RailwayAWT() {
        setTitle("Railway Reservation System");

        setLayout(new GridLayout(9,2,10,10));

        add(new Label("PNR:"));
        tfPNR = new TextField();
        add(tfPNR);

        add(new Label("Name:"));
        tfName = new TextField();
        add(tfName);

        add(new Label("Age:"));
        tfAge = new TextField();
        add(tfAge);

        add(new Label("Gender:"));
        genderChoice = new Choice();
        genderChoice.add("Male");
        genderChoice.add("Female");
        genderChoice.add("Other");
        add(genderChoice);

        add(new Label("Train Name:"));
        tfTrain = new TextField();
        add(tfTrain);

        book = new Button("Book Ticket");
        view = new Button("View Tickets");
        cancel = new Button("Cancel Ticket");

        add(book);
        add(view);
        add(cancel);

        add(new Label("Output:"));
        ta = new TextArea();
        add(ta);

        book.addActionListener(this);
        view.addActionListener(this);
        cancel.addActionListener(this);

        setSize(500,400);
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

            // BOOK TICKET
            if (e.getSource() == book) {

                if (tfPNR.getText().isEmpty() || tfName.getText().isEmpty() || tfAge.getText().isEmpty()) {
                    ta.setText("⚠️ Fill all fields!");
                    return;
                }

                String q = "INSERT INTO reservations VALUES(?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(q);

                ps.setInt(1, Integer.parseInt(tfPNR.getText()));
                ps.setString(2, tfName.getText());
                ps.setInt(3, Integer.parseInt(tfAge.getText()));
                ps.setString(4, genderChoice.getSelectedItem());
                ps.setString(5, tfTrain.getText());

                ps.executeUpdate();
                ta.setText("✅ Ticket Booked Successfully!");
            }

            // VIEW TICKETS
            else if (e.getSource() == view) {

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM reservations");

                ta.setText("");

                while (rs.next()) {
                    ta.append(
                        "PNR: " + rs.getInt("pnr") +
                        ", Name: " + rs.getString("name") +
                        ", Age: " + rs.getInt("age") +
                        ", Gender: " + rs.getString("gender") +
                        ", Train: " + rs.getString("train_name") + "\n"
                    );
                }
            }

            // CANCEL TICKET
            else if (e.getSource() == cancel) {

                if (tfPNR.getText().isEmpty()) {
                    ta.setText("⚠️ Enter PNR!");
                    return;
                }

                String q = "DELETE FROM reservations WHERE pnr=?";
                PreparedStatement ps = con.prepareStatement(q);

                ps.setInt(1, Integer.parseInt(tfPNR.getText()));

                int rows = ps.executeUpdate();

                if (rows > 0)
                    ta.setText("❌ Ticket Cancelled!");
                else
                    ta.setText("⚠️ PNR not found!");
            }

            con.close();

        } catch (NumberFormatException nfe) {
            ta.setText("⚠️ Enter valid numbers!");
        } catch (Exception ex) {
            ta.setText("Error: " + ex.getMessage());
        }
    }
}