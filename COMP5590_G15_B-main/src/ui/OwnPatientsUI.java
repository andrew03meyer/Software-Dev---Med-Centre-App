package src.ui;

import src.engine.DBManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OwnPatientsUI extends JFrame implements ActionListener {

    private JButton back;

    public OwnPatientsUI(){ initFrame(); }

    /**
     *  Creates the frame and UI
     */
    private void initFrame(){
        initTable();
        initBack();

        // Set the layout of the window
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Set the window properties
        setSize(600, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Creates the back button
     */
    private void initBack() {
        back = new JButton("Back");
        back.addActionListener(this);
        add(back);
    }

    /**
     * Creates the table with the viewOwnPatients method's results
     */
    private void initTable() {

        ArrayList<ArrayList<String>> response = DBManager.viewOwnPatients();
        // code below converts 2D arrayList to 2d array
        String[][] data = response.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
        String[] headers = {"First Name", "Last Name", "Phone number", "Address", "Email"};

        JTable table = new JTable(data, headers);
        add(new JScrollPane(table));
    }

    /**
     * Action listener event
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back){
            new PatientsSelectorUI();
            dispose();
        }
    }
}
