package src.ui;

import src.engine.DBManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EnterDetailsUI extends JFrame implements ActionListener {
    private final String docName;
    private final String patName;
    private final String bookingDate;
    private JButton backButton;
    private JButton saveButton;
    private JTextArea commentsField;
    private JTextArea prescriptionsField;

    public EnterDetailsUI(List<String> row) {
        docName = (row.get(0) + " " + row.get(1));
        patName = (row.get(2) + " " + row.get(3));
        bookingDate = row.get(4);

        initFrame();
    }

    /**
     * Creates the UI
     */
    private void initFrame() {
        // Set the layout of the window
        setLayout(new BorderLayout());

        // Create buttons
        backButton = new JButton("Back");
        backButton.addActionListener(this);

        saveButton = new JButton("Save changes");
        saveButton.addActionListener(this);

        // Create labels
        JLabel docNameLabel = new JLabel("Doctor: " + docName);
        JLabel patNameLabel = new JLabel("Patient: " + patName);
        JLabel date = new JLabel("Booking date: " + bookingDate);

        // Create text fields
        JLabel commentsLabel = new JLabel("Comments: ");
        commentsField = new JTextArea();
        commentsField.setPreferredSize(new Dimension(250, 50));
        JScrollPane commentsScroll = new JScrollPane(commentsField);
        commentsScroll.setAlignmentX(0.0f);

        JLabel prescriptionsLabel = new JLabel("Prescriptions: ");
        prescriptionsField = new JTextArea();
        prescriptionsField.setPreferredSize(new Dimension(250, 50));
        JScrollPane prescriptionScroll = new JScrollPane(prescriptionsField);
        prescriptionScroll.setAlignmentX(0.0f);

        // Panel to keep the labels together
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.add(docNameLabel);
        labelPanel.add(patNameLabel);
        labelPanel.add(date);
        labelPanel.add(commentsLabel);
        labelPanel.add(commentsScroll);
        labelPanel.add(prescriptionsLabel);
        labelPanel.add(prescriptionScroll);
        labelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Panel to keep the buttons together
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(backButton);
        buttonsPanel.add(saveButton);

        // Make buttons appear at the bottom of the window
        add(labelPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Set the window properties
        setSize(400, 300);
        setVisible(true);
    }

    /**
     * Handles the button clicks
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Back button
        if (e.getSource() == backButton) {
            new BookingsUI();
            dispose();
        }
        // Save button, closes the ui and commits all changes to the database
        else if (e.getSource() == saveButton) {
            DBManager.enterPastVisitDetails(patName, bookingDate, commentsField.getText(), prescriptionsField.getText());
            new BookingsUI();
            dispose();
        }
    }
}
