package src.ui;

import src.engine.DBManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

public class BookingsUI extends JFrame {
    // Constructor class for bookingsui
    private JFormattedTextField dateField;
    private JButton searchButton;
    private JButton mainMenu;
    private JPanel scrollPanel;

    /**
     * Constructor method for BookingsUI - creates the ui
     */
    public BookingsUI() {
        initFrame();

        // Automatically populate with everything
        List<List<String>> set = DBManager.allBookings();

        // Populate bookings if the query was successful
        if (set != null) {
            try {
                populateBookings(set);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void initFrame(){
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

        JLabel dateLabel = new JLabel("Enter month and year:");

        searchButton = new JButton("Search");
        searchButton.addActionListener(event -> actionPerformed(event, "", null));

        mainMenu = new JButton("Back");
        mainMenu.addActionListener(event -> actionPerformed(event, "", null));

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
        bookingsPane.setPreferredSize(new Dimension(750, 150));
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
        setSize(750, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Fills the scroll panel with labels that show each booking in the database
     *
     * @param set a list of the stored values from the bookings table in the database
     * @throws SQLException Errors if the database query fails
     */
    public void populateBookings(List<List<String>> set) throws SQLException {
        scrollPanel.removeAll();

        // Loop through the list
        Iterator<List<String>> myIt = set.iterator();
        while (myIt.hasNext()) {
            //Each row is an entity in the DB
            List<String> row = (List<String>) myIt.next();
            String Doc_F_Name = row.get(0);
            String Doc_L_Name = row.get(1);
            String Pat_F_Name = row.get(2);
            String Pat_L_Name = row.get(3);
            String date = row.get(4);

            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new FlowLayout());

            // Add a jbutton to show the bookings
            String text = "Doctor: " + Doc_F_Name + " " + Doc_L_Name + ", Patient: " + Pat_F_Name + " " + Pat_L_Name + ", Date: " + date;
            rowPanel.add(new JLabel(text));
            JButton newDetailButton = new JButton("Enter new details");
            rowPanel.add(newDetailButton);
            newDetailButton.addActionListener(event -> actionPerformed(event, "NewDetails", row));

            // Add jbutton to assign a new doctor
            JButton newDoctorButton = new JButton("Assign new doctor");
            rowPanel.add(newDoctorButton);
            newDoctorButton.addActionListener(event -> actionPerformed(event, "NewDoctor", row));

            scrollPanel.add(rowPanel);
        }
        // System.out.println("Populate successful");
        scrollPanel.revalidate();
        scrollPanel.repaint();
    }

    /**
     * Handles the actions
     * @param e   The event to be performed
     * @param buttonType  A string value that determines what button called the function (since temporary buttons generated
     *                    from the table cannot be seen as variables)
     * @param row  A list value that is only called if (buttonType == true), as the table buttons need the row data to
     *             perform actions
     */
    public void actionPerformed(ActionEvent e, String buttonType, List<String> row) {
        // Assign new doctor
        if (buttonType.equals("NewDoctor")) {
            // Empty row - assign new doctor
            new AssignNewDoctorUI(row);
            dispose();
        }
        // Row supplied - enter new details
        else if (buttonType.equals("NewDetails")){
            new EnterDetailsUI(row);
            dispose();
        }
        // Main menu button
        else if (e.getSource() == mainMenu) {
            // Handle the button click
            // System.out.println("Back to main menu successful");
            new BookingSelectorUI();
            dispose();
        }
        // Search button
        else if (e.getSource() == searchButton) {
            // System.out.println("Search button click successful");

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
