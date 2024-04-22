package src.ui;

import src.engine.DBManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AssignNewDoctorUI extends JFrame implements ActionListener {

    private final String patName;
    private final String bookingDate;
    private JButton backButton;
    private JButton saveButton;
    private JComboBox<String> dropdown;

    public AssignNewDoctorUI(List<String> row){
        patName = (row.get(2) + " " + row.get(3));
        bookingDate = row.get(4);
        // System.out.println(row);
        initFrame();
    }

    /**
     * Initiates the frame and UI
     */
    public void initFrame(){
        setLayout(new BorderLayout());

        // Create buttons
        backButton = new JButton("Back");
        backButton.addActionListener(this);

        saveButton = new JButton("Save Changes");
        saveButton.addActionListener(this);

        JLabel header = new JLabel("Assign a new Doctor");
        dropdown = new JComboBox<String>(DBManager.getDoctorNames().toArray(new String[0]));

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(header);
        main.add(dropdown);


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(saveButton);

        add(dropdown);

        add(buttonPanel, BorderLayout.SOUTH);

        // Set the window properties
        setSize(400, 300);
        setVisible(true);
    }

    /**
     * Action listener
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            new BookingsUI();
            dispose();
        } else if (e.getSource() == saveButton) {
            String docName = (String) dropdown.getSelectedItem();
            DBManager.assignNewDoctor(docName, patName, bookingDate);
            new BookingsUI();
            dispose();
        }
    }
}
