 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chat;

import java.util.List;
import java.util.Scanner;

/**
 * Console application: registration, login, QuickChat messaging (Part 2)
 * and the Stored Messages menu (Part 3).
 *
 * @author thand
 */
public class Main {

    public static void main(String[] args) {

        // reads everything the user types into the console
        Scanner scanner = new Scanner(System.in);

        // the Login object that holds this session's registered user
        Login login = new Login();

        System.out.println("=== User Registration ===");

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        // keep asking for a username until checkUsername() accepts it
        String username;
        while (true) {
            System.out.print("Enter a username (must contain '_' and be <= 5 characters): ");
            username = scanner.nextLine();

            if (login.checkUsername(username)) {
                break;
            }

            System.out.println("Username is not correctly formatted; please ensure that your "
                    + "username contains an underscore and is no more than five "
                    + "characters in length.");
        }

        // same pattern for the password
        String password;
        while (true) {
            System.out.print("Enter a password (8+ chars, capital, number, special char): ");
            password = scanner.nextLine();

            if (login.checkPasswordComplexity(password)) {
                break;
            }

            System.out.println("Password is not correctly formatted; please ensure that the "
                    + "password contains at least eight characters, a capital letter, "
                    + "a number, and a special character.");
        }

        // same pattern for the cell number
        String cellPhoneNumber;
        while (true) {
            System.out.print("Enter your SA cell number (e.g. +27838968976): ");
            cellPhoneNumber = scanner.nextLine();

            if (login.checkCellPhoneNumber(cellPhoneNumber)) {
                break;
            }

            System.out.println("Cell phone number incorrectly formatted or does not contain "
                    + "international code.");
        }

        // all three fields are valid here, so this always succeeds
        String message = login.registerUser(username, password, firstName, lastName, cellPhoneNumber);
        System.out.println(message);

        System.out.println();
        System.out.println("=== User Login ===");

        // keeps asking until login succeeds - QuickChat starts after this
        login.promptLogin(scanner);

        // Part 3: remember who the sender is, and read any messages
        // stored in the JSON file by earlier runs
        Message.setSender(firstName + " " + lastName);
        Message.loadStoredMessages();

        runQuickChat(scanner);

        scanner.close();
    }


    // the main menu loop. Only reachable after a successful login.
    private static void runQuickChat(Scanner scanner) {
        System.out.println();
        System.out.println("Welcome to QuickChat.");

        // becomes true once the user has entered their messages for
        // this session (the number is only asked once)
        boolean messageLimitReached = false;

        while (true) {
            System.out.println();
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    if (messageLimitReached) {
                        System.out.println("You have reached your message limit for this session.");
                    } else {
                        sendMessages(scanner);
                        messageLimitReached = true;
                    }
                    break;

                case "2":
                    System.out.println(Message.printMessages());
                    break;

                case "3":
                    System.out.println("Total messages sent: " + Message.returnTotalMessages());
                    System.out.println(Message.printMessages());
                    return;

                case "4":
                    runStoredMessagesMenu(scanner);
                    break;

                default:
                    System.out.println("Invalid option, please choose 1, 2, 3, or 4.");
                    break;
            }
        }
    }


    // asks how many messages to enter, then uses a FOR loop to take
    // exactly that many - whatever the user does with each one
    private static void sendMessages(Scanner scanner) {
        int numMessages = readPositiveNumber(scanner, "How many messages do you wish to enter? ");

        // messageNumber (i) goes up for EVERY message, so each hash
        // gets its own number even for stored and disregarded messages
        for (int messageNumber = 0; messageNumber < numMessages; messageNumber++) {
            System.out.println();
            System.out.println("--- Message " + (messageNumber + 1) + " of " + numMessages + " ---");

            String recipient = readRecipient(scanner);
            String messageText = readMessageText(scanner);

            Message newMessage = new Message(recipient, messageText);

            // keep asking until the choice is Send, Store or Disregard
            String sendChoice;
            String result;
            do {
                System.out.print("Send, Store, or Disregard this message? ");
                sendChoice = scanner.nextLine();
                result = newMessage.sentMessage(sendChoice, messageNumber);
                System.out.println(result);
            } while (result.equals(Message.INVALID_CHOICE));

            showOutcome(scanner, newMessage, sendChoice);
        }
    }


    // after a message is handled: show its details if it was sent,
    // or wait for the user to press 0 if it was disregarded
    private static void showOutcome(Scanner scanner, Message handled, String sendChoice) {
        String lowerChoice = sendChoice.toLowerCase();

        if (lowerChoice.contains("send")) {
            System.out.println("Message ID: " + handled.getMessageID());
            System.out.println("Message Hash: " + handled.getMessageHash());
            System.out.println("Recipient: " + handled.getRecipient());
            System.out.println("Message: " + handled.getMessageText());

        } else if (lowerChoice.contains("disregard")) {
            while (true) {
                String deleteInput = scanner.nextLine();
                if (deleteInput.equals("0")) {
                    System.out.println("Message deleted.");
                    break;
                }
                System.out.println("Invalid input. Press 0 to delete the message.");
            }
        }
        // a stored message was already saved by sentMessage()
    }


    // asks for a whole number above 0 until the user gives one
    private static int readPositiveNumber(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int number = Integer.parseInt(scanner.nextLine().trim());
                if (number > 0) {
                    return number;
                }
                System.out.println("Please enter a number greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }


    // asks for a recipient until checkRecipientCell() accepts it
    private static String readRecipient(Scanner scanner) {
        while (true) {
            System.out.print("Enter recipient cell number (e.g. +27718693002): ");
            String recipient = scanner.nextLine().trim();
            String cellResult = Message.checkRecipientCell(recipient);
            System.out.println(cellResult);
            if (cellResult.equals("Cell phone number successfully captured.")) {
                return recipient;
            }
        }
    }


    // asks for the message text until checkMessageLength() accepts it
    private static String readMessageText(Scanner scanner) {
        while (true) {
            System.out.print("Enter your message (should not exceed 250 characters): ");
            String messageText = scanner.nextLine();
            String lengthResult = Message.checkMessageLength(messageText);
            System.out.println(lengthResult);
            if (lengthResult.equals("Message ready to send.") && !messageText.trim().isEmpty()) {
                return messageText;
            }
            if (messageText.trim().isEmpty()) {
                System.out.println("A message cannot be empty.");
            }
        }
    }


    // Part 3 - the fourth main menu option
    private static void runStoredMessagesMenu(Scanner scanner) {
        // read the JSON file into the stored array and show how many were read
        Message.loadStoredMessages();
        System.out.println(Message.getStoredMessages().size()
                + " stored message(s) read from storedMessages.json.");

        while (true) {
            System.out.println();
            System.out.println("=== Stored Messages ===");
            System.out.println("a) Display the sender and recipient of all stored messages");
            System.out.println("b) Display the longest stored message");
            System.out.println("c) Search for a message ID");
            System.out.println("d) Search for all messages for a recipient");
            System.out.println("e) Delete a message using its message hash");
            System.out.println("f) Display a report of all stored messages");
            System.out.println("x) Back to the main menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "a":
                    System.out.println(Message.displaySendersAndRecipients());
                    break;
                case "b":
                    System.out.println("Longest stored message: " + Message.getLongestStoredMessage());
                    break;
                case "c":
                    searchByMessageID(scanner);
                    break;
                case "d":
                    searchByRecipient(scanner);
                    break;
                case "e":
                    System.out.print("Enter the message hash to delete: ");
                    System.out.println(Message.deleteMessageByHash(scanner.nextLine().trim()));
                    break;
                case "f":
                    System.out.println(Message.buildReport(Message.FLAG_STORED));
                    break;
                case "x":
                    return;
                default:
                    System.out.println("Invalid option, please choose a, b, c, d, e, f or x.");
                    break;
            }
        }
    }


    // 2c - shows the recipient and message for a message ID
    private static void searchByMessageID(Scanner scanner) {
        System.out.print("Enter the message ID: ");
        String id = scanner.nextLine().trim();
        System.out.println("Recipient: " + Message.findRecipientByID(id));
        System.out.println("Message: " + Message.findMessageByID(id));
    }


    // 2d - shows every message for one recipient
    private static void searchByRecipient(Scanner scanner) {
        System.out.print("Enter the recipient cell number: ");
        String recipient = scanner.nextLine().trim();
        List<String> found = Message.searchByRecipient(recipient);

        if (found.isEmpty()) {
            System.out.println("No messages found for " + recipient + ".");
            return;
        }
        for (String text : found) {
            System.out.println("Message: " + text);
        }
    }
}
