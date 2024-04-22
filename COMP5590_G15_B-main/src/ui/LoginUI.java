package src.ui;
// import src.Main;

import src.engine.DBManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URI;

public class LoginUI extends JFrame implements ActionListener, KeyListener {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton logInButton;
    private final JButton emailSupportButton;

    /**
     * Constructor method for LogInUI class
     */
    public LoginUI() {
        // Initialize the UI components
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        passwordField.addKeyListener(this);
        logInButton = new JButton("Log In");
        emailSupportButton = new JButton("Email Support");

        // Action listeners for the buttons
        logInButton.addActionListener(this);
        emailSupportButton.addActionListener(this);

        // Adds tooltips to all the components
        usernameField.setToolTipText("Please enter your GP login here");
        passwordField.setToolTipText("Please enter your GP password here");
        logInButton.setToolTipText("Click here to log in");

        // Set the layout of the window
        setLayout(new BorderLayout());

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(logInButton);
        buttonPanel.add(emailSupportButton);

        // Set up a sprint layout
        SpringLayout layout = new SpringLayout();

        // Create a panel for the entered fields
        JPanel loginpanel = new JPanel(layout);
        JLabel userLabel = new JLabel("Username:");
        userLabel.setVerticalAlignment(JLabel.CENTER);
        JLabel passLabel = new JLabel("Password:");

        // Add fields and labels to the panel
        loginpanel.add(usernameField);
        loginpanel.add(userLabel);
        loginpanel.add(passLabel);
        loginpanel.add(passwordField);

        // Align to the top
        layout.putConstraint(SpringLayout.NORTH, userLabel, 5, SpringLayout.NORTH, loginpanel);
        // Put user label and field beside each other
        layout.putConstraint(SpringLayout.WEST, usernameField, 5, SpringLayout.EAST, userLabel);

        // Add password fields underneath username
        layout.putConstraint(SpringLayout.NORTH, passLabel, 10, SpringLayout.SOUTH, userLabel);
        layout.putConstraint(SpringLayout.NORTH, passwordField, 5, SpringLayout.SOUTH, userLabel);
        layout.putConstraint(SpringLayout.WEST, passwordField, 0, SpringLayout.WEST, usernameField);

        // Add components to the frame
        add(loginpanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        this.getRootPane().setDefaultButton(logInButton);

        // Set the window properties
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Opens the users email with a pre-filled subject and body to tech support.
     */
    private void openSupportEmail() {
        try {
            Desktop desktop = Desktop.getDesktop();
            URI mailto = new URI("mailto:techsupport@canterburyGP.com?subject=Need%20help%20logging%20in&body=Please%20explain%20the%20problem%20you%20are%20facing");
            desktop.mail(mailto);
        } catch (Exception ex) {
            ex.printStackTrace();
            // If the email fails to open, user is prompted to manually email tech support
            JOptionPane.showMessageDialog(this, "Failed to open tech support email. Please manually email techsupport@canterburyGP.com with any issues you are having.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles the button clicks
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logInButton) {
            login();
        } else if (e.getSource() == emailSupportButton) {
            openSupportEmail();
        }
    }

    /**
     * Logs the user in and progresses onto the main menu if their details match the database
     */
    public void login() {
        // Doctor login
        if (logInButton() == DBManager.Person.DOCTOR) {
            // If the login is successful, close the window
            new MainMenuUI();
            dispose();
        }
        // Patient login
        else if (logInButton() == DBManager.Person.PATIENT) {
            // If the login is successful, close the window
            System.out.println("Login successful. Welcome Patient!");
        } else {
            // If the login fails, display an error message
            JOptionPane.showMessageDialog(this, "Login failed. Please check your username and password and try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles the login button click
     *
     * @return 'doc', 'pat' or 'fail' depending on what's in the database table
     */
    public Enum<DBManager.Person> logInButton() {
        return DBManager.login(usernameField.getText(), new String(passwordField.getPassword()));
    }

    /**
     * Handles when a key is typed - no functionality but required
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        //Nothing - Required for KeyListener
    }

    /**
     * Handles keyboard input - specifically when the enter key is pressed
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == passwordField) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                login();
            }
        }
    }

    /**
     * Handles when a key is released - no functionality but required
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        //Nothing - Required for KeyListener
    }
}
