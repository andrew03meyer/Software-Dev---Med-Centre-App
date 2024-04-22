package UI;

import Engine.DBManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

public class BookingsUI extends JFrame implements ActionListener {
    // Constructor class for bookingsui

    private final JLabel dateLabel;
    private final JFormattedTextField dateField;
    private final JButton searchButton;
    private final JButton mainMenu;
    private final JPanel scrollPanel;

    /**
     * Constructor method for BookingsUI - creates the ui
     */
    public BookingsUI() {

        // Create date field
        DateFormat dateFormat = new SimpleDateFormat("MM-yyyy");

        dateField = new JFormattedTextField(dateFormat);
        dateField.setColumns(5);
        dateField.setToolTipText("Format: MM-YYYY");
        
        // Add a key listener to the date field
        dateField.addKeyListener(new KeyListener() {
            
            @Override
            public void keyTyped(KeyEvent e) {
                //Not used
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //Not used
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchButton.doClick();
                }
            }
        });

        dateLabel = new JLabel("Enter month and year:");

        searchButton = new JButton("Search");
        searchButton.addActionListener(this);

        mainMenu = new JButton("Back");
        mainMenu.addActionListener(this);

        // Set layout for the window
        setLayout(new BorderLayout());

        // Create layout for the panel
        SpringLayout layout = new SpringLayout();

        // Create panels
        JPanel datePanel = new JPanel(layout); // For the date field at the top
        JPanel buttonsPanel = new JPanel(); // For the buttons at the bottom
        scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));

        JScrollPane bookingsPane = new JScrollPane(scrollPanel);
        bookingsPane.setPreferredSize(new Dimension(500, 150));
        bookingsPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        bookingsPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Layout design
        layout.putConstraint(SpringLayout.NORTH, dateLabel, 20, SpringLayout.NORTH, datePanel);
        layout.putConstraint(SpringLayout.WEST, dateField, 5, SpringLayout.EAST, dateLabel);
        layout.putConstraint(SpringLayout.NORTH, dateField, -3, SpringLayout.NORTH, dateLabel);
        layout.putConstraint(SpringLayout.WEST, searchButton, 5, SpringLayout.EAST, dateField);
        layout.putConstraint(SpringLayout.NORTH, searchButton, -1, SpringLayout.NORTH, dateField);

        // for the table
        layout.putConstraint(SpringLayout.NORTH, bookingsPane, 10, SpringLayout.SOUTH, dateField);
        layout.putConstraint(SpringLayout.WEST, bookingsPane, 0, SpringLayout.WEST, dateLabel);

        // Add to panel
        datePanel.add(dateLabel);
        datePanel.add(dateField);
        datePanel.add(searchButton);
        datePanel.add(bookingsPane);

        buttonsPanel.add(mainMenu);

        add(datePanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Customise window
        setSize(500,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Fills the scroll panel with labels that show each booking in the database
     * @param set  a list of the stored values from the bookings table in the database
     * @throws SQLException  Errors if the database query fails
     */
    public void populateBookings(List<List<String>> set) throws SQLException {
        scrollPanel.removeAll();

        // Loop through the list
        Iterator<List<String>> myIt = set.iterator();
        while (myIt.hasNext()){
            //Each row is an entity in the DB
            List<String> row = (List<String>) myIt.next();
            String Doc_F_Name = row.get(0);
            String Doc_L_Name = row.get(1);
            String Pat_F_Name = row.get(2);
            String Pat_L_Name = row.get(3);
            String date = row.get(4);

            // Add a jlabel to show the bookngs
            String text = "Doctor: " + Doc_F_Name + " " + Doc_L_Name + ", Patient: " + Pat_F_Name + " " + Pat_L_Name + ", Date: " + date;
            scrollPanel.add(new JLabel(text));
        }
        System.out.println("Populate successful");
        scrollPanel.revalidate();
        scrollPanel.repaint();
    }

    /**
     * Handles button clicks
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        // Handle the button click
        if (e.getSource() == mainMenu) {
            System.out.println("Back to main menu successful");
            new MainMenuUI();
            dispose();
        }
        else if (e.getSource() == searchButton) {
            System.out.println("Search button click successful");

            //Calls bookingsQuery in DBManager passes in the input date
            List<List<String>> set = DBManager.bookingsQuery(dateField.getText());

            // Populate bookings if the query was successful
            if (set != null) {
                try {
                    populateBookings(set);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
