package src.ui;

import src.engine.DBManager;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PastVisitsUI extends JFrame implements ActionListener {

    private JButton back;

    public PastVisitsUI() {
        initFrame();
    }

    private JTable table;

    /**
     * Initiates the frame that the UI is based on
     */
    private void initFrame() {
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
     * Initiates the back button
     */
    private void initBack() {
        back = new JButton("Back");
        back.addActionListener(this);
        add(back);
    }

    /**
     * Initiates the table and populates it with the getVisitDetails() method's query results
     */
    private void initTable() {

        ArrayList<ArrayList<String>> response = DBManager.getVisitDetails();
        // code below converts 2D arrayList to 2d array
        String[][] data = response.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
        String[] headers = {"VisitID", "Date", "First Name", "Last Name", "Comments", "Prescriptions"};


        // A method that overrides the table model in order to make only the last two columns editable
        DefaultTableModel model = new DefaultTableModel(data, headers)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return column == 4 || column == 5;
            }
        };


        // Assign the new table model to the table
        table = new JTable(model);
        add(new JScrollPane(table));

        // Listens for when the table is edited
        table.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                // If the table is edited, send the edited row's data to be committed to the database
                int r = e.getLastRow();

                String vid = (String) table.getValueAt(r, 0);
                String comment = (String) table.getValueAt(r, 4);
                String prescription = (String) table.getValueAt(r, 5);
                DBManager.updateVisitDetails(vid, comment, prescription);
            }
        });
    }

    /**
     * Action listener
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle the button click
        if (e.getSource() == back) {
            // System.out.println("Back");
            new BookingSelectorUI();
            dispose();
        }
    }
}
