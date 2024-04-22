package src.ui;

// import src.ui.AllPatientsUI;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientsSelectorUI extends JFrame implements ActionListener {

    JButton myPatients;
    JButton allPatients;
    JButton back;

    public PatientsSelectorUI() {
        initFrame();
    }

    /**
     *  Creates the frame and UI
     */
    private void initFrame() {
        // initialise the buttons
        initAllPatients();
        initMyPatients();
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
     * Creates the 'all patients' button
     */
    private void initAllPatients() {
        allPatients = new JButton("View all patients");
        allPatients.addActionListener(this);
        add(allPatients);
    }

    /**
     * Creates the 'my patients' button
     */
    private void initMyPatients() {
        myPatients = new JButton("View my patients");
        myPatients.addActionListener(this);
        add(myPatients);
    }


    /**
     * Action listener
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Back button
        if (e.getSource() == back) {
            // System.out.println("Back");
            new MainMenuUI();
            dispose();
        }

        // View all patients button
        if (e.getSource() == allPatients) {
            // System.out.println("All patients");
            new AllPatientsUI();
            dispose();
        }

        // View own patients button
        if (e.getSource() == myPatients) {
            // System.out.println("My patients");
            new OwnPatientsUI();
            dispose();
        }
    }
}
