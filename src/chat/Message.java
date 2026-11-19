/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author thand
 */
package chat;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Parts 2 and 3 - represents a single message and keeps the shared arrays
 * that hold every sent, stored and disregarded message for this run.
 *
 * ATTRIBUTION (Part 3, reading and writing JSON): the JSONParser, JSONArray
 * and JSONObject code below follows the usage examples in the json-simple
 * library documentation (https://github.com/fangyidong/json-simple).
 
 *
 * @author thand
 */
public class Message {

    // === flags that say what happened to a message ===
    public static final String FLAG_SENT = "Sent";
    public static final String FLAG_STORED = "Stored";

    // returned by sentMessage() when the user's choice is not recognised
    public static final String INVALID_CHOICE = "Invalid choice.";

    // +27 followed by exactly 9 digits (same rule as Part 1 and Part 2)
    private static final Pattern CELL_PATTERN = Pattern.compile("\\+27[0-9]{9}$");

    // === shared arrays (ArrayLists are used as resizable arrays) ===

    // the message text of every message, grouped by what the user chose
    private static final List<String> sentMessages = new ArrayList<>();
    private static final List<String> disregardedMessages = new ArrayList<>();
    private static final List<String> storedMessages = new ArrayList<>();

    // PARALLEL ARRAYS: index 0 of each list describes the same message.
    // They hold every sent and stored message. Disregarded messages are
    // deleted, so they only appear in disregardedMessages.
    private static final List<String> messageIDs = new ArrayList<>();
    private static final List<String> messageHashes = new ArrayList<>();
    private static final List<String> messageRecipients = new ArrayList<>();
    private static final List<String> messageTexts = new ArrayList<>();
    private static final List<String> messageFlags = new ArrayList<>();

    // where stored messages are saved (tests change this to a temp file)
    private static String storedFilePath = "storedMessages.json";

    // the logged-in user, shown as the sender of stored messages
    private static String sender = "Unknown sender";


    // === fields specific to THIS message ===
    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;


    // creates a new message and gives it a random, unused 10-digit ID
    public Message(String recipient, String messageText) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
    }


    // builds a random 10-digit ID, one digit at a time, and tries again
    // if the ID is already in use
    private static String generateMessageID() {
        Random random = new Random();
        String id;
        do {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                builder.append(random.nextInt(10));
            }
            id = builder.toString();
        } while (messageIDs.contains(id));
        return id;
    }


    // === validation (static so Main and the tests can call them
    // without creating a throwaway Message) ===

    // an ID is valid when it is exactly 10 digits
    public static boolean checkMessageID(String messageID) {
        return messageID != null && messageID.matches("[0-9]{10}");
    }

    public boolean checkMessageID() {
        return checkMessageID(this.messageID);
    }

    // checks the recipient against the SA cell pattern and returns the
    // exact success or failure text from the brief
    public static String checkRecipientCell(String recipientNumber) {
        if (recipientNumber != null && CELL_PATTERN.matcher(recipientNumber).matches()) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain "
                + "an international code. Please correct the number and try again.";
    }

    public String checkRecipientCell() {
        return checkRecipientCell(this.recipient);
    }

    // checks the text against the 250 character limit
    public static String checkMessageLength(String messageText) {
        if (messageText != null && messageText.length() > 250) {
            int over = messageText.length() - 250;
            return "Message exceeds 250 characters by " + over + "; please reduce the size.";
        }
        return "Message ready to send.";
    }

    public String checkMessageLength() {
        return checkMessageLength(this.messageText);
    }


    // === message hash ===

    // first two digits of the ID : message number : first + last word, in capitals
    // e.g. ID 0012345678, number 0, "Hi Mike ... tonight?" gives 00:0:HITONIGHT
    public static String createMessageHash(String messageID, int messageNumber, String messageText) {
        String idPrefix = messageID.substring(0, 2);

        String[] words = messageText.trim().split("\\s+");
        String firstWord = stripPunctuation(words[0]);
        String lastWord = stripPunctuation(words[words.length - 1]);

        return idPrefix + ":" + messageNumber + ":" + (firstWord + lastWord).toUpperCase();
    }

    // builds and remembers the hash for THIS message
    public String createMessageHash(int messageNumber) {
        this.messageHash = createMessageHash(this.messageID, messageNumber, this.messageText);
        return this.messageHash;
    }

    // keeps only letters and digits, so "tonight?" becomes "tonight"
    private static String stripPunctuation(String word) {
        return word.replaceAll("[^a-zA-Z0-9]", "");
    }


    // === send, store or disregard ===

    // handles the user's choice for this message. messageNumber must be a
    // counter that goes up for EVERY message (not only sent ones)
    public String sentMessage(String choice, int messageNumber) {
        String normalizedChoice = choice.toLowerCase();

        if (normalizedChoice.contains("send")) {
            createMessageHash(messageNumber);
            addToArrays(FLAG_SENT);
            return "Message successfully sent.";
        }
        if (normalizedChoice.contains("store")) {
            createMessageHash(messageNumber);
            addToArrays(FLAG_STORED);
            saveStoredMessages();
            return "Message successfully stored.";
        }
        if (normalizedChoice.contains("disregard")) {
            disregardedMessages.add(messageText);
            return "Press 0 to delete the message.";
        }
        return INVALID_CHOICE;
    }

    // adds this message to the parallel arrays and to the sent or stored array
    private void addToArrays(String flag) {
        messageIDs.add(messageID);
        messageHashes.add(messageHash);
        messageRecipients.add(recipient);
        messageTexts.add(messageText);
        messageFlags.add(flag);

        if (flag.equals(FLAG_SENT)) {
            sentMessages.add(messageText);
        } else {
            storedMessages.add(messageText);
        }
    }


    // === Part 3 features (all work on the shared arrays) ===

    // how many messages have been sent so far
    public static int returnTotalMessages() {
        return sentMessages.size();
    }

    // full details of every sent message
    public static String printMessages() {
        return buildReport(FLAG_SENT);
    }

    // 2a - sender and recipient of every stored message
    public static String displaySendersAndRecipients() {
        StringBuilder result = new StringBuilder("=== Stored Messages: Sender and Recipient ===\n");
        int count = 0;
        for (int i = 0; i < messageFlags.size(); i++) {
            if (messageFlags.get(i).equals(FLAG_STORED)) {
                count++;
                result.append("Sender: ").append(sender)
                        .append(" | Recipient: ").append(messageRecipients.get(i)).append("\n");
            }
        }
        if (count == 0) {
            result.append("No stored messages.\n");
        }
        return result.toString();
    }

    // 2b - the longest stored message
    public static String getLongestStoredMessage() {
        if (storedMessages.isEmpty()) {
            return "No stored messages.";
        }
        return longestOf(storedMessages);
    }

    // the longest message of all sent and stored messages
    public static String getLongestMessage() {
        if (messageTexts.isEmpty()) {
            return "No messages.";
        }
        return longestOf(messageTexts);
    }

    // walks through a list and keeps the longest text it has seen
    private static String longestOf(List<String> texts) {
        String longest = "";
        for (String text : texts) {
            if (text.length() > longest.length()) {
                longest = text;
            }
        }
        return longest;
    }

    // 2c - search for a message ID: the message and the recipient
    public static String findMessageByID(String id) {
        int index = messageIDs.indexOf(id);
        return index == -1 ? "Message ID not found." : messageTexts.get(index);
    }

    public static String findRecipientByID(String id) {
        int index = messageIDs.indexOf(id);
        return index == -1 ? "Message ID not found." : messageRecipients.get(index);
    }

    // 2d - every sent or stored message for one recipient
    public static List<String> searchByRecipient(String recipientNumber) {
        List<String> found = new ArrayList<>();
        for (int i = 0; i < messageRecipients.size(); i++) {
            if (messageRecipients.get(i).equals(recipientNumber)) {
                found.add(messageTexts.get(i));
            }
        }
        return found;
    }

    // 2e - delete a message using its hash, from every array
    public static String deleteMessageByHash(String hash) {
        int index = messageHashes.indexOf(hash);
        if (index == -1) {
            return "Message hash not found.";
        }

        String text = messageTexts.get(index);
        String flag = messageFlags.get(index);

        // remove from the parallel arrays, then from the sent/stored array
        messageIDs.remove(index);
        messageHashes.remove(index);
        messageRecipients.remove(index);
        messageTexts.remove(index);
        messageFlags.remove(index);

        if (flag.equals(FLAG_SENT)) {
            sentMessages.remove(text);
        } else {
            storedMessages.remove(text);
            saveStoredMessages();
        }
        return "Message: \"" + text + "\" successfully deleted.";
    }

    // 2f - report with the full details of every sent OR stored message
    public static String buildReport(String flag) {
        StringBuilder report = new StringBuilder("=== " + flag + " Messages Report ===\n");
        int count = 0;
        for (int i = 0; i < messageFlags.size(); i++) {
            if (messageFlags.get(i).equals(flag)) {
                count++;
                report.append("Message Hash: ").append(messageHashes.get(i)).append("\n")
                        .append("Message ID: ").append(messageIDs.get(i)).append("\n")
                        .append("Recipient: ").append(messageRecipients.get(i)).append("\n")
                        .append("Message: ").append(messageTexts.get(i)).append("\n\n");
            }
        }
        if (count == 0) {
            report.append("No ").append(flag.toLowerCase()).append(" messages.\n");
        }
        return report.toString();
    }


    // === JSON file (json-simple) ===

    // writes every STORED message to the JSON file, one object per line.
    // json-simple escapes quotes, backslashes and line breaks for us.
    @SuppressWarnings("unchecked")
    public static void saveStoredMessages() {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < messageFlags.size(); i++) {
            if (messageFlags.get(i).equals(FLAG_STORED)) {
                JSONObject entry = new JSONObject();
                entry.put("messageID", messageIDs.get(i));
                entry.put("messageHash", messageHashes.get(i));
                entry.put("recipient", messageRecipients.get(i));
                entry.put("message", messageTexts.get(i));
                lines.add("  " + entry.toJSONString());
            }
        }

        String content = "[\n" + String.join(",\n", lines) + (lines.isEmpty() ? "" : "\n") + "]\n";
        try {
            Files.write(Paths.get(storedFilePath), content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("Could not write to " + storedFilePath + ": " + e.getMessage());
        }
    }

    // reads the JSON file into the stored arrays. Messages whose ID is
    // already in the arrays are skipped, so nothing is added twice.
    // Returns how many messages were added.
  
    // ATTRIBUTION: reading the file with JSONParser and walking the JSONArray
    // follows the json-simple documentation (https://github.com/fangyidong/json-simple),
    // the same source named at the top of this class.
    
    public static int loadStoredMessages() {
        Path path = Paths.get(storedFilePath);
        if (!Files.exists(path)) {
            return 0;
        }

        int loaded = 0;
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            Object parsed = new JSONParser().parse(reader);
            if (parsed instanceof JSONArray) {
                for (Object item : (JSONArray) parsed) {
                    JSONObject entry = (JSONObject) item;
                    String id = (String) entry.get("messageID");
                    String hash = (String) entry.get("messageHash");
                    String storedRecipient = (String) entry.get("recipient");
                    String text = (String) entry.get("message");

                    if (id == null || hash == null || storedRecipient == null || text == null
                            || messageIDs.contains(id)) {
                        continue;
                    }

                    messageIDs.add(id);
                    messageHashes.add(hash);
                    messageRecipients.add(storedRecipient);
                    messageTexts.add(text);
                    messageFlags.add(FLAG_STORED);
                    storedMessages.add(text);
                    loaded++;
                }
            }
        } catch (IOException | ParseException | ClassCastException e) {
            System.out.println("Could not read " + storedFilePath + ": " + e.getMessage());
        }
        return loaded;
    }


    // === settings and test helpers ===

    public static void setSender(String senderName) {
        sender = senderName;
    }

    public static void setStoredFilePath(String path) {
        storedFilePath = path;
    }

    // empties every array - used by the unit tests so they start clean
    public static void resetAll() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageIDs.clear();
        messageHashes.clear();
        messageRecipients.clear();
        messageTexts.clear();
        messageFlags.clear();
    }

    // copies of the arrays, so callers cannot change the real ones
    public static List<String> getSentMessages() {
        return new ArrayList<>(sentMessages);
    }

    public static List<String> getDisregardedMessages() {
        return new ArrayList<>(disregardedMessages);
    }

    public static List<String> getStoredMessages() {
        return new ArrayList<>(storedMessages);
    }

    public static List<String> getMessageIDs() {
        return new ArrayList<>(messageIDs);
    }

    public static List<String> getMessageHashes() {
        return new ArrayList<>(messageHashes);
    }


    // === getters for this message ===

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
