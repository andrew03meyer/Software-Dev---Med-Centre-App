package src.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuUI extends JFrame implements ActionListener {
    private JButton logOutButton;
    private JButton bookings;
    private JButton contact;
    private JButton patients;

    MainMenuUI() {
        initFrame();
    }

    private void initFrame() {
        // Initialize the UI components
        initLogout();
        initBookings();
        initPatients();
        initContact();

        // Set the layout of the window
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Set the window properties
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initContact() {
        contact = new JButton("Contact Reception");
        contact.addActionListener(this);
        add(contact);
    }

    private void initPatients() {
        patients = new JButton("Patients");
        patients.addActionListener(this);
        add(patients);
    }

    private void initBookings() {
        bookings = new JButton("Bookings");
        bookings.addActionListener(this);
        add(bookings);
    }

    private void initLogout() {
        logOutButton = new JButton("Log Out");
        logOutButton.addActionListener(this);
        add(logOutButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle the button click
        if (e.getSource() == logOutButton) {
            // System.out.println("Log out successful");
            new LoginUI();
            dispose();
        } else if (e.getSource() == bookings) {
            // System.out.println("Open bookings successful");
            new BookingSelectorUI();
            dispose();
        } else if (e.getSource() == contact) {
            // System.out.println("Open contact reception successful");
            new ContactReceptionUI();
            dispose();
        } else if (e.getSource() == patients) {
            // System.out.println("Open patients successful");
            new PatientsSelectorUI();
            dispose();
        }
    }
}
