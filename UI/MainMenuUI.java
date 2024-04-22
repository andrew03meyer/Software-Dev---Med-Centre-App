package UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuUI extends JFrame implements ActionListener{
    private final JButton logOutButton;
    private final JButton bookings;
    private final JButton patients;
    private final JButton contact;

    MainMenuUI(){
        // Initialize the UI components
        logOutButton = new JButton("Log Out");
        logOutButton.addActionListener(this);
        bookings = new JButton("Bookings");
        bookings.addActionListener(this);
        patients = new JButton("Patients");
        contact = new JButton("Contact Reception");


        // Set the layout of the window
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add the components to the window
        add(logOutButton);
        add(bookings);
        add(patients);
        add(contact);


        // Set the window properties
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle the button click
        if (e.getSource() == logOutButton) {
            System.out.println("Log out successful");
            new LogInUI();
            dispose();
        }
        else if (e.getSource() == bookings) {
            System.out.println("Open bookings successful");
            new BookingsUI();
            dispose();
        }
    }
}
