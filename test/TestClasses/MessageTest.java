/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestClasses;

import chat.Message;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the Message class (Parts 2 and 3).
 * Every test starts with empty arrays and saves to a temporary JSON file,
 * so the real storedMessages.json is never touched.
 *
 * @author thand
 */
public class MessageTest {

    private static final String LATE_TEXT = "Where are you? You are late! I have asked you to be on time.";

    private File tempFile;

    // the brief's test data, filled in by loadTestData()
    private Message message1;
    private Message message2;
    private Message message3;
    private Message message4;
    private Message message5;

    @Before
    public void setUp() throws IOException {
        Message.resetAll();
        tempFile = File.createTempFile("storedMessagesTest", ".json");
        Files.write(tempFile.toPath(), "[]".getBytes());
        Message.setStoredFilePath(tempFile.getAbsolutePath());
    }

    @After
    public void tearDown() {
        Message.resetAll();
        tempFile.delete();
    }

    // creates a message, applies the flag from the brief and returns it
    private Message addTestMessage(String recipient, String text, String choice, int number) {
        Message created = new Message(recipient, text);
        created.sentMessage(choice, number);
        return created;
    }

    // loads Test Data Messages 1 to 5 from the brief, in order
    private void loadTestData() {
        message1 = addTestMessage("+27834557896", "Did you get the cake?", "Send Message", 0);
        message2 = addTestMessage("+27838884567", LATE_TEXT, "Store Message", 1);
        message3 = addTestMessage("+27834484567", "Yohoooo, I am at your gate.", "Disregard Message", 2);
        message4 = addTestMessage("0838884567", "It is dinner time!", "Send Message", 3);
        message5 = addTestMessage("+27838884567", "Ok, I am leaving without you.", "Store Message", 4);
    }


    // === message length ===

    @Test
    public void TestMessageLengthSuccess() {
        String expected = "Message ready to send.";
        String actual = Message.checkMessageLength("Hi Mike, can you join us for dinner tonight?");
        assertEquals(expected, actual);
    }

    @Test
    public void TestMessageLengthFailure() {
        // exactly 260 characters - 10 over the limit
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            longMessage.append("a");
        }
        String expected = "Message exceeds 250 characters by 10; please reduce the size.";
        assertEquals(expected, Message.checkMessageLength(longMessage.toString()));
    }


    // === recipient cell number ===

    @Test
    public void TestRecipientCellSuccess() {
        String expected = "Cell phone number successfully captured.";
        assertEquals(expected, Message.checkRecipientCell("+27718693002"));
    }

    @Test
    public void TestRecipientCellFailure() {
        // no +27 international code, so it must fail
        String expected = "Cell phone number is incorrectly formatted or does not contain "
                + "an international code. Please correct the number and try again.";
        assertEquals(expected, Message.checkRecipientCell("08575975889"));
    }


    // === message hash and message number ===

    @Test
    public void TestMessageHashCorrect() {
        // a fixed ID is passed in, because the real ID is random
        String actual = Message.createMessageHash("0012345678", 0,
                "Hi Mike, can you join us for dinner tonight?");
        assertEquals("00:0:HITONIGHT", actual);
    }

    @Test
    public void TestMessageHashUsesMessageNumber() {
        String actual = Message.createMessageHash("1234567890", 7, "Hello there friend");
        assertEquals("12:7:HELLOFRIEND", actual);
    }

    @Test
    public void TestEveryMessageGetsItsOwnNumber() {
        loadTestData();
        // sent, stored and disregarded messages all count: 0, 1, 2, 3, 4
        assertTrue(message1.getMessageHash().contains(":0:"));
        assertTrue(message2.getMessageHash().contains(":1:"));
        assertTrue(message4.getMessageHash().contains(":3:"));
        assertTrue(message5.getMessageHash().contains(":4:"));
    }


    // === message ID ===

    @Test
    public void TestMessageIDCorrectLength() {
        assertTrue(Message.checkMessageID("0012345678"));
    }

    @Test
    public void TestMessageIDTooLong() {
        assertFalse(Message.checkMessageID("00123456789"));
    }

    @Test
    public void TestMessageIDTooShort() {
        assertFalse(Message.checkMessageID("12345"));
    }

    @Test
    public void TestMessageIDNotDigits() {
        assertFalse(Message.checkMessageID("12345abcde"));
    }

    @Test
    public void TestGeneratedIDIsValid() {
        Message created = new Message("+27718693002", "Hi there");
        assertTrue(created.checkMessageID());
    }


    // === send, store, disregard ===

    @Test
    public void TestSentMessageSendChoice() {
        Message m = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message successfully sent.", m.sentMessage("Send Message", 0));
    }

    @Test
    public void TestSentMessageStoreChoice() {
        Message m = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message successfully stored.", m.sentMessage("Store Message", 0));
    }

    @Test
    public void TestSentMessageDisregardChoice() {
        Message m = new Message("08575975889", "Hi Keegan, did you receive the payment?");
        assertEquals("Press 0 to delete the message.", m.sentMessage("Disregard Message", 0));
    }

    @Test
    public void TestSentMessageInvalidChoice() {
        Message m = new Message("+27718693002", "Hi Mike");
        assertEquals(Message.INVALID_CHOICE, m.sentMessage("Maybe", 0));
    }

    @Test
    public void TestReturnTotalMessagesIncreases() {
        Message m = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        int before = Message.returnTotalMessages();
        m.sentMessage("Send Message", 0);
        assertEquals(before + 1, Message.returnTotalMessages());
    }


    // === Part 3: arrays ===

    @Test
    public void TestSentMessagesArrayCorrectlyPopulated() {
        loadTestData();
        List<String> expected = Arrays.asList("Did you get the cake?", "It is dinner time!");
        assertEquals(expected, Message.getSentMessages());
    }

    @Test
    public void TestDisregardedMessagesArrayCorrectlyPopulated() {
        loadTestData();
        List<String> expected = Arrays.asList("Yohoooo, I am at your gate.");
        assertEquals(expected, Message.getDisregardedMessages());
    }

    @Test
    public void TestStoredMessagesArrayCorrectlyPopulated() {
        loadTestData();
        List<String> expected = Arrays.asList(LATE_TEXT, "Ok, I am leaving without you.");
        assertEquals(expected, Message.getStoredMessages());
    }

    @Test
    public void TestHashAndIDArraysCorrectlyPopulated() {
        loadTestData();
        // messages 1, 2, 4 and 5 were sent or stored; message 3 was disregarded
        assertEquals(4, Message.getMessageIDs().size());
        assertEquals(4, Message.getMessageHashes().size());
        assertTrue(Message.getMessageIDs().contains(message4.getMessageID()));
        assertTrue(Message.getMessageHashes().contains(message2.getMessageHash()));
        assertFalse(Message.getMessageIDs().contains(message3.getMessageID()));
    }


    // === Part 3: stored messages menu features ===

    @Test
    public void TestDisplayLongestMessage() {
        loadTestData();
        assertEquals(LATE_TEXT, Message.getLongestMessage());
        assertEquals(LATE_TEXT, Message.getLongestStoredMessage());
    }

    @Test
    public void TestLongestStoredMessageWhenNoneStored() {
        assertEquals("No stored messages.", Message.getLongestStoredMessage());
    }

    @Test
    public void TestSearchForMessageID() {
        loadTestData();
        String id = message4.getMessageID();
        assertEquals("It is dinner time!", Message.findMessageByID(id));
        assertEquals("0838884567", Message.findRecipientByID(id));
    }

    @Test
    public void TestSearchForMessageIDNotFound() {
        loadTestData();
        assertEquals("Message ID not found.", Message.findMessageByID("0000000000"));
    }

    @Test
    public void TestSearchAllMessagesForARecipient() {
        loadTestData();
        List<String> expected = Arrays.asList(LATE_TEXT, "Ok, I am leaving without you.");
        assertEquals(expected, Message.searchByRecipient("+27838884567"));
    }

    @Test
    public void TestSearchForRecipientWithNoMessages() {
        loadTestData();
        assertTrue(Message.searchByRecipient("+27111111111").isEmpty());
    }

    @Test
    public void TestDeleteMessageUsingMessageHash() {
        loadTestData();
        String expected = "Message: \"" + LATE_TEXT + "\" successfully deleted.";
        assertEquals(expected, Message.deleteMessageByHash(message2.getMessageHash()));
        // it is gone from the arrays
        assertFalse(Message.getStoredMessages().contains(LATE_TEXT));
        assertEquals("Message ID not found.", Message.findMessageByID(message2.getMessageID()));
    }

    @Test
    public void TestDeleteMessageWithUnknownHash() {
        loadTestData();
        assertEquals("Message hash not found.", Message.deleteMessageByHash("99:9:NOPE"));
    }

    @Test
    public void TestDisplayReportForSentMessages() {
        loadTestData();
        String report = Message.buildReport(Message.FLAG_SENT);
        assertTrue(report.contains(message1.getMessageHash()));
        assertTrue(report.contains("+27834557896"));
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains(message4.getMessageHash()));
        assertTrue(report.contains("It is dinner time!"));
        // stored messages are not in the sent report
        assertFalse(report.contains("Ok, I am leaving without you."));
    }

    @Test
    public void TestDisplayReportForStoredMessages() {
        loadTestData();
        String report = Message.buildReport(Message.FLAG_STORED);
        assertTrue(report.contains(message2.getMessageHash()));
        assertTrue(report.contains("+27838884567"));
        assertTrue(report.contains(LATE_TEXT));
    }

    @Test
    public void TestSenderAndRecipientDisplay() {
        Message.setSender("Thandeka Radebe");
        loadTestData();
        String display = Message.displaySendersAndRecipients();
        assertTrue(display.contains("Sender: Thandeka Radebe | Recipient: +27838884567"));
        assertFalse(display.contains("+27834557896"));
    }


    // === Part 3: JSON file ===

    @Test
    public void TestStoredMessagesAreReadBackFromJSON() {
        loadTestData();
        List<String> storedBefore = Message.getStoredMessages();

        // empty the arrays, then read the file back in
        Message.resetAll();
        int loaded = Message.loadStoredMessages();

        assertEquals(2, loaded);
        assertEquals(storedBefore, Message.getStoredMessages());
    }

    @Test
    public void TestReadingJSONTwiceDoesNotAddDuplicates() {
        loadTestData();
        assertEquals(0, Message.loadStoredMessages());
        assertEquals(2, Message.getStoredMessages().size());
    }

    @Test
    public void TestSpecialCharactersSurviveJSON() {
        String tricky = "Path C:\\temp says \"hello\"\tand\nbye";
        addTestMessage("+27718693002", tricky, "Store Message", 0);

        Message.resetAll();
        Message.loadStoredMessages();

        assertEquals(Arrays.asList(tricky), Message.getStoredMessages());
    }

    @Test
    public void TestDeletedMessageIsRemovedFromJSON() {
        loadTestData();
        Message.deleteMessageByHash(message2.getMessageHash());

        Message.resetAll();
        Message.loadStoredMessages();

        assertEquals(Arrays.asList("Ok, I am leaving without you."), Message.getStoredMessages());
    }

    @Test
    public void TestSentMessagesAreNotSavedToJSON() {
        addTestMessage("+27834557896", "Did you get the cake?", "Send Message", 0);

        Message.resetAll();
        assertEquals(0, Message.loadStoredMessages());
    }

    @Test
    public void TestMissingJSONFileReturnsZero() {
        tempFile.delete();
        assertEquals(0, Message.loadStoredMessages());
    }
}
