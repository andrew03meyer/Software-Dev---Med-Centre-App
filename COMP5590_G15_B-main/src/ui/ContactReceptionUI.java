package src.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContactReceptionUI extends JFrame implements ActionListener {

    public JButton back;
    public JTextArea messageBox;
    public JButton sendMessage;
    public JLabel messageLabel;

    public ContactReceptionUI() {
        initFrame();
        initMessageBox();
        initMessageLabel();
        initButtonsPanel();

    }

    private void initFrame() {
        // Set the layout of the window
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        // Set the window properties
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        //initButtonsPanel();
    }

    public void initButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        sendMessage = new JButton("Send Message");
        sendMessage.addActionListener(this);
        buttonsPanel.add(sendMessage);

        back = new JButton("Back");
        back.addActionListener(this);
        buttonsPanel.add(back);
        add(buttonsPanel);


    }

    public void initMessageBox() {
        messageBox = new JTextArea(10, 10);
        JScrollPane scrollPane = new JScrollPane(messageBox);
        add(scrollPane);
    }

    public void initMessageLabel() {
        messageLabel = new JLabel("");
        add(messageLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle the button click
        if (e.getSource() == back) {
            // System.out.println("Back");
            new MainMenuUI();
            dispose();
        } else if (e.getSource() == sendMessage) {
            String message = messageBox.getText();
            if (!message.isEmpty()) {
                sendMessageToReception(message);
                messageBox.setText("");
                messageLabel.setText("The message has been sent");
            } else {
                messageLabel.setText("Message cannot be sent. Message has to be entered.");
            }
        }
    }

    private void sendMessageToReception(String message) {
        System.out.println("Message to reception: " + message);
    }
}

