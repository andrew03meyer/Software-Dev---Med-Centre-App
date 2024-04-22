package src.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookingSelectorUI extends JFrame implements ActionListener {

    JButton pastDetails;
    JButton futureBookings;
    JButton back;

    public BookingSelectorUI() {
        initFrame();
    }

    /**
     * Initiates the frame that the UI is based on
     */
    private void initFrame() {
        // initialise the buttons
        initPastButton();
        initFutureButton();
        initBackButton();

        // Set the layout of the window
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Set the window properties
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Creates the back button
     */
    private void initBackButton() {
        back = new JButton("Back");
        back.addActionListener(this);
        add(back);
    }

    /**
     * Creates the future bookings button
     */
    private void initFutureButton() {
        futureBookings = new JButton("View my bookings");
        futureBookings.addActionListener(this);
        add(futureBookings);
    }

    /**
     * Creates the past details button
     */
    private void initPastButton() {
        pastDetails = new JButton("View past details");
        pastDetails.addActionListener(this);
        add(pastDetails);
    }

    /**
     * Action performed method
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle the button click
        if (e.getSource() == back) {
            // System.out.println("Back");
            new MainMenuUI();
            dispose();
        }

        if (e.getSource() == futureBookings) {
            // System.out.println("Bookings");
            new BookingsUI();
            dispose();
        }

        if (e.getSource() == pastDetails) {
            // System.out.println("Past visits");
            new PastVisitsUI();
            dispose();
        }
    }
}
