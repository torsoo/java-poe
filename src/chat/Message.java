/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author thand
 */
package chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Part 2 - represents a single message, and tracks all messages sent
 * during this run of the program.
 *
 * @author thand
 */
public class Message {
 
    // === shared data - tracked across EVERY Message object, not just one ===
 
    // how many messages have actually been sent (not stored, not disregarded)
    private static int totalMessagesSent = 0;
 
    // full details of every sent message, in the order they were sent -
    // this is what printMessages() reads from
    private static List<String> sentMessageLog = new ArrayList<>();
 
    // the same +27 + 9 digit pattern used in Part 1's checkCellPhoneNumber(),
    // reused here per the brief's instruction to reuse the last task's method
    private static final Pattern CELL_PATTERN = Pattern.compile("\\+27[0-9]{9}$");
 
 
    // === fields specific to THIS message ===
 
    // randomly generated 10-digit identifier for this message
    private String messageID;
 
    // the recipient's cell number
    private String recipient;
 
    // the actual text of the message
    private String messageText;
 
    // set once createMessageHash() has run for this message
    private String messageHash;
 
 
    // creates a new message: stores the recipient and text, and
    // immediately generates a random message ID for it
    public Message(String recipient, String messageText) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
    }
 
 
    // builds a random 10-digit numeric string, one digit at a time
    private String generateMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }
 
 
    // checks that a message ID is no more than 10 characters long.
    // takes the ID as a parameter so it can be unit tested with fixed
    // values, rather than only ever checking the random one
    public boolean checkMessageID(String messageID) {
        return messageID != null && messageID.length() <= 10;
    }
 
    // convenience version - checks THIS message's own ID
    public boolean checkMessageID() {
        return checkMessageID(this.messageID);
    }
 
 
    // checks a recipient number against the SA cell number pattern,
    // and returns the exact success/failure message the brief specifies
    public String checkRecipientCell(String recipientNumber) {
        if (recipientNumber != null && CELL_PATTERN.matcher(recipientNumber).matches()) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain "
                    + "an international code. Please correct the number and try again.";
        }
    }
 
    public String checkRecipientCell() {
        return checkRecipientCell(this.recipient);
    }
 
 
    // builds the message hash: first two digits of the ID, a colon,
    // the message's position number, another colon, then the first
    // and last words of the message text, all in capitals.
    // e.g. ID "0012345678", message number 0, "Hi Mike...tonight?"
    // produces "00:0:HITONIGHT"
    //
    // takes messageID as a parameter (rather than always using this
    // object's own random one) so it can be unit tested against a
    // fixed, predictable ID
    public String createMessageHash(String messageID, int messageNumber, String messageText) {
        String idPrefix = messageID.substring(0, 2);
 
        String[] words = messageText.trim().split("\\s+");
        String firstWord = stripPunctuation(words[0]);
        String lastWord = stripPunctuation(words[words.length - 1]);
 
        return idPrefix + ":" + messageNumber + ":"
                + (firstWord + lastWord).toUpperCase();
    }
 
    // convenience version - builds and stores the hash for THIS
    // message, using its own randomly generated ID, and remembers
    // the result in messageHash for later (e.g. printing after send)
    public String createMessageHash(int messageNumber) {
        this.messageHash = createMessageHash(this.messageID, messageNumber, this.messageText);
        return this.messageHash;
    }
 
    // removes anything that isn't a letter or digit from a word -
    // used to strip trailing punctuation like "?" or "," before
    // building the hash, so "tonight?" becomes "tonight"
    private String stripPunctuation(String word) {
        return word.replaceAll("[^a-zA-Z0-9]", "");
    }
 
 
    // checks the message text against the 250 character limit.
    // wording here matches the brief's unit test table exactly,
    // since that's what assertEquals checks against
    public String checkMessageLength(String messageText) {
        if (messageText.length() > 250) {
            int over = messageText.length() - 250;
            return "Message exceeds 250 characters by " + over + "; please reduce the size.";
        } else {
            return "Message ready to send.";
        }
    }
 
    public String checkMessageLength() {
        return checkMessageLength(this.messageText);
    }
 
 
    // handles the user's choice once a message has been written:
    // send it, store it for later, or disregard it. Uses .contains()
    // rather than an exact match so it accepts both short choices
    // ("send") and the brief's full test phrases ("Send Message")
    public String sentMessage(String choice, int messageNumber) {
        String result;
        String normalizedChoice = choice.toLowerCase();
 
        if (normalizedChoice.contains("send")) {
            // build the hash, count it toward the total, log its
            // details, and save it to the JSON file
            createMessageHash(messageNumber);
            totalMessagesSent++;
            sentMessageLog.add("Message ID: " + messageID
                    + "\nMessage Hash: " + messageHash
                    + "\nRecipient: " + recipient
                    + "\nMessage: " + messageText);
            storeMessage();
            result = "Message successfully sent.";
 
        } else if (normalizedChoice.contains("store")) {
            // still build the hash and save to JSON, but don't count
            // it as "sent" or add it to the sent-messages log
            createMessageHash(messageNumber);
            storeMessage();
            result = "Message successfully stored.";
 
        } else if (normalizedChoice.contains("disregard")) {
            // nothing is saved anywhere - just tell the caller what
            // confirmation message to show
            result = "Press 0 to delete the message.";
 
        } else {
            result = "Invalid choice.";
        }
 
        return result;
    }
 
 
    // returns every sent message's details, one after another, in
    // the order they were sent - used when showing a summary at quit
    public String printMessages() {
        StringBuilder sb = new StringBuilder();
        for (String entry : sentMessageLog) {
            sb.append(entry).append("\n\n");
        }
        return sb.toString();
    }
 
 
    // returns how many messages have actually been sent so far this
    // session (not stored, not disregarded - just sent).
    // NOTE: the brief's method table spells this returnTotalMessagess
    // with a double "s" - treating that as a typo and using the
    // correctly spelled name here
    public int returnTotalMessages() {
        return totalMessagesSent;
    }
 
 
    // appends this message's details to storedMessages.json as one
    // more entry in a JSON array. Reads whatever's already in the
    // file first so previous entries aren't lost, then rewrites the
    // whole file with the new entry added on the end.
    // Written by hand (no external JSON library) since the brief
    // only asks to "research" this, without naming a specific library.
    private void storeMessage() {
        java.io.File file = new java.io.File("storedMessages.json");
 
        // this message's own details, formatted as one JSON object
        String jsonEntry = "  {\n"
                + "    \"messageID\": \"" + messageID + "\",\n"
                + "    \"messageHash\": \"" + messageHash + "\",\n"
                + "    \"recipient\": \"" + recipient + "\",\n"
                + "    \"message\": \"" + messageText.replace("\"", "\\\"") + "\"\n"
                + "  }";
 
        try {
            // every entry already in the file, so the new one can be
            // appended rather than overwriting what's there
            List<String> existingEntries = new ArrayList<>();
 
            if (file.exists()) {
                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
                content = content.trim();
 
                // only parse if it looks like a JSON array already
                if (content.startsWith("[") && content.endsWith("]")) {
                    content = content.substring(1, content.length() - 1).trim();
                    if (!content.isEmpty()) {
                        // split the existing entries apart at the
                        // boundary between one object and the next
                        String[] parts = content.split("\\},\\s*\\{");
                        for (int i = 0; i < parts.length; i++) {
                            String part = parts[i];
                            if (!part.startsWith("{")) part = "{" + part;
                            if (!part.endsWith("}")) part = part + "}";
                            existingEntries.add(part);
                        }
                    }
                }
            }
 
            // add this message onto the end of whatever was already there
            existingEntries.add(jsonEntry);
 
            // rebuild the whole file as one JSON array
            StringBuilder output = new StringBuilder();
            output.append("[\n");
            for (int i = 0; i < existingEntries.size(); i++) {
                output.append(existingEntries.get(i));
                if (i < existingEntries.size() - 1) {
                    output.append(",");
                }
                output.append("\n");
            }
            output.append("]\n");
 
            java.nio.file.Files.write(file.toPath(), output.toString().getBytes());
 
        } catch (java.io.IOException e) {
            // if writing fails for any reason, say so rather than
            // silently losing the message
            System.out.println("Could not write to storedMessages.json: " + e.getMessage());
        }
    }
 
 
    // === getters - let Main.java (and tests) read this message's
    // details without touching the private fields directly ===
 
    public String getMessageID() {
        return messageID;
    }
   
    public String getMessageHash() {
        return messageHash;
    }
 
    public String getRecipient() {
        return recipient;
    }
 
    public String getMessageText() {
        return messageText;
    }
 
}