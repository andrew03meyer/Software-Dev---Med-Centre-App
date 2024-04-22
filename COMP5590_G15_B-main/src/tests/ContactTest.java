package src.tests;
import org.junit.Test;
import src.ui.ContactReceptionUI;
import static org.junit.Assert.*;
import java.awt.event.ActionEvent;



public class ContactTest {

    // testing for an empty message box when the send message button is clicked
    @Test
    public void testEmptyMessage() {
        ContactReceptionUI contactReceptionUI = new ContactReceptionUI();
            clickSendMessageButton(contactReceptionUI);
            assertEquals("Message cannot be sent. Message has to be entered.", contactReceptionUI.messageLabel.getText());

        contactReceptionUI.dispose();
    }

    // testing for a message being sent when the send message button is clicked
    @Test
    public void testSendMessage() {
        ContactReceptionUI contactReceptionUI = new ContactReceptionUI();
        String testMessage = "Test message";
            contactReceptionUI.messageBox.setText(testMessage);
            clickSendMessageButton(contactReceptionUI);
            assertEquals("The message has been sent", contactReceptionUI.messageLabel.getText());
            assertTrue(contactReceptionUI.messageBox.getText().isEmpty());

        contactReceptionUI.dispose();
    }

    @Test
    public void testMessageLabelUpdating() {
        ContactReceptionUI contactReceptionUI = new ContactReceptionUI();
        String testMessage = "Test message";
            contactReceptionUI.messageBox.setText(testMessage);

            clickSendMessageButton(contactReceptionUI);
            assertEquals("The message has been sent", contactReceptionUI.messageLabel.getText());
            contactReceptionUI.messageBox.setText("");

            clickSendMessageButton(contactReceptionUI);
            assertEquals("Message cannot be sent. Message has to be entered.", contactReceptionUI.messageLabel.getText());

        contactReceptionUI.dispose();
    }

    // testing the back buttons functionality
    @Test
    public void testBackButton() {
        ContactReceptionUI contactReceptionUI = new ContactReceptionUI();
            clickBackButton(contactReceptionUI);
            assertFalse(contactReceptionUI.isVisible());
    }

    // testing the send message button functionality
    private void clickSendMessageButton(ContactReceptionUI contactReceptionUI) {
        contactReceptionUI.actionPerformed(new ActionEvent(contactReceptionUI.sendMessage, ActionEvent.ACTION_PERFORMED, ""));
    }

    // testing the back button functionality
    private void clickBackButton(ContactReceptionUI contactReceptionUI) {
        contactReceptionUI.back.doClick();
    }

}