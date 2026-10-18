/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestClasses;

import chat.Message;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author thand
 */
public class MessageTest {
 
    // === checkMessageLength() returns String - assertEquals, success and failure ===
 
    @Test
    public void TestMessageLengthSuccess() {
        Message m = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        String expected = "Message ready to send.";
        String actual = m.checkMessageLength("Hi Mike, can you join us for dinner tonight?");
        assertEquals(expected, actual);
    }
 
    @Test
    public void TestMessageLengthFailure() {
        Message m = new Message("+27718693002", "placeholder");
 
        // build a message of exactly 260 characters - 10 over the limit,
        // so the expected "exceeds by 10" message can be checked precisely
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            longMessage.append("a");
        }
 
        String expected = "Message exceeds 250 characters by 10; please reduce the size.";
        String actual = m.checkMessageLength(longMessage.toString());
        assertEquals(expected, actual);
    }
 
 
    // === checkRecipientCell() returns String - assertEquals, success and failure ===
 
    @Test
    public void TestRecipientCellSuccess() {
        Message m = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        String expected = "Cell phone number successfully captured.";
        String actual = m.checkRecipientCell("+27718693002");
        assertEquals(expected, actual);
    }
 
    @Test
    public void TestRecipientCellFailure() {
        // this number has no +27 international code, so it should fail
        Message m = new Message("08575975889", "Hi Keegan, did you receive the payment?");
        String expected = "Cell phone number is incorrectly formatted or does not contain "
                + "an international code. Please correct the number and try again.";
        String actual = m.checkRecipientCell("08575975889");
        assertEquals(expected, actual);
    }
 
 
    // === createMessageHash() returns String - assertEquals ===
 
    @Test
    public void TestMessageHashCorrect() {
        Message m = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        String expected = "00:0:HITONIGHT";
        // a fixed ID starting with "00" is passed in directly, since the
        // real message ID is randomly generated and would never reliably
        // produce the same expected output twice
        String actual = m.createMessageHash("0012345678", 0,
                "Hi Mike, can you join us for dinner tonight?");
        assertEquals(expected, actual);
    }
 
    // NOTE: the brief's point 9 also lists a test "Message ID is created"
    // expecting the output "Message ID generated: <Message ID>" - no
    // method in the brief's own method table (page 14) actually returns
    // a string in that format, so no test is written for it here. Worth
    // confirming with the lecturer what this is meant to test.
 
 
    // === checkMessageID() returns boolean - assertTrue/assertFalse ===
 
    @Test
    public void TestMessageIDCorrectLength() {
        Message m = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        // exactly 10 characters - should pass
        assertTrue(m.checkMessageID("0012345678"));
    }
 
    @Test
    public void TestMessageIDTooLong() {
        Message m = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        // 11 characters - should fail
        assertFalse(m.checkMessageID("00123456789"));
    }
 
 
    // === sentMessage() returns String - assertEquals ===
    // uses the brief's literal choice wording from point 9's "Additional
    // test data" ('Send Message', 'Disregard Message', 'Store Message')
 
    @Test
    public void TestSentMessageSendChoice() {
        Message m = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        String expected = "Message successfully sent.";
        String actual = m.sentMessage("Send Message", 0);
        assertEquals(expected, actual);
    }
 
    @Test
    public void TestSentMessageStoreChoice() {
        Message m = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        String expected = "Message successfully stored.";
        String actual = m.sentMessage("Store Message", 0);
        assertEquals(expected, actual);
    }
 
    @Test
    public void TestSentMessageDisregardChoice() {
        Message m = new Message("08575975889", "Hi Keegan, did you receive the payment?");
        String expected = "Press 0 to delete the message.";
        String actual = m.sentMessage("Disregard Message", 0);
        assertEquals(expected, actual);
    }
 
 
    // === returnTotalMessages() returns int - assertEquals ===
 
    @Test
    public void TestReturnTotalMessagesIncreases() {
        Message m1 = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
 
        // totalMessagesSent is static (shared across every Message
        // object), so "before" reflects every send test that happened
        // to run earlier in the same test session, not just this message
        int before = m1.returnTotalMessages();
 
        m1.sentMessage("Send Message", 0);
 
        int after = m1.returnTotalMessages();
        assertEquals(before + 1, after);
    }
}
