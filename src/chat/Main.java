 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chat;
import java.util.Scanner;
/**
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
 
        // all three fields are already valid at this point, so this
        // always succeeds - it just stores the details and returns
        // the success message
        String message = login.registerUser(username, password, firstName, lastName, cellPhoneNumber);
        System.out.println(message);
 
        System.out.println();
        System.out.println("=== User Login ===");
 
        // keeps asking for username/password until login actually
        // succeeds, then returns - QuickChat can only start after this
        login.promptLogin(scanner);
 
        // === Part 2: Sending Messages ===
        runQuickChat(scanner);
 
        scanner.close();
    }
 
 
    // Part 2's menu-driven message sending flow. Only reachable after
    // a successful login (handled above). Follows the brief's exact
    // order: welcome message, then the menu appears immediately -
    // "how many messages" is only asked the first time option 1 is
    // chosen, not before the menu shows up.
    private static void runQuickChat(Scanner scanner) {
        System.out.println();
        System.out.println("Welcome to QuickChat.");
 
        // -1 means "not asked yet" - this only gets set once, the
        // first time the user picks option 1
        int numMessages = -1;
 
        // how many messages have actually been sent so far this session
        int messagesSent = 0;
 
        // the most recently created message - also used to call
        // returnTotalMessages()/printMessages() at quit, since those
        // read from shared (static) data that any Message can access
        Message sessionMessage = null;
 
        while (true) {
            System.out.println();
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
 
            switch (choice) {
                case "1":
                    // ask how many messages this session allows -
                    // but only the very first time option 1 is picked
                    if (numMessages == -1) {
                        while (true) {
                            System.out.print("How many messages do you wish to enter? ");
                            try {
                                numMessages = Integer.parseInt(scanner.nextLine());
                                if (numMessages > 0) {
                                    break;
                                }
                                System.out.println("Please enter a number greater than 0.");
                            } catch (NumberFormatException e) {
                                System.out.println("Please enter a valid number.");
                            }
                        }
                    }
 
                    // stop here if the session's message limit has
                    // already been reached
                    if (messagesSent >= numMessages) {
                        System.out.println("You have reached your message limit for this session.");
                        break;
                    }
 
                    // --- recipient: loop until checkRecipientCell() accepts it ---
                    String recipient;
                    Message tempMessage;
                    while (true) {
                        System.out.print("Enter recipient cell number (e.g. +27718693002): ");
                        recipient = scanner.nextLine();
                        // a throwaway Message object just to run the check -
                        // the real one gets created further down once both
                        // the recipient and message text are valid
                        tempMessage = new Message(recipient, "");
                        String cellResult = tempMessage.checkRecipientCell();
                        System.out.println(cellResult);
                        if (cellResult.equals("Cell phone number successfully captured.")) {
                            break;
                        }
                    }
 
                    // --- message text: loop until checkMessageLength() accepts it ---
                    String messageText;
                    while (true) {
                        System.out.print("Enter your message (should not exceed 250 characters): ");
                        messageText = scanner.nextLine();
                        String lengthResult = tempMessage.checkMessageLength(messageText);
                        System.out.println(lengthResult);
                        if (lengthResult.equals("Message ready to send.")) {
                            break;
                        }
                    }
 
                    // both recipient and message text are valid now -
                    // create the real message object
                    sessionMessage = new Message(recipient, messageText);
 
                    System.out.print("Send, Store, or Disregard this message? ");
                    String sendChoice = scanner.nextLine();
 
                    // sentMessage() handles the actual logic and
                    // returns the matching confirmation message
                    String result = sessionMessage.sentMessage(sendChoice, messagesSent);
                    System.out.println(result);
 
                    if (sendChoice.equalsIgnoreCase("send") || sendChoice.toLowerCase().contains("send")) {
                        // only a genuinely SENT message counts toward
                        // the session total and gets its full details printed
                        messagesSent++;
                        System.out.println("Message ID: " + sessionMessage.getMessageID());
                        System.out.println("Message Hash: " + sessionMessage.getMessageHash());
                        System.out.println("Recipient: " + sessionMessage.getRecipient());
                        System.out.println("Message: " + sessionMessage.getMessageText());
 
                    } else if (sendChoice.toLowerCase().contains("disregard")) {
                        // the "Press 0 to delete the message." line was
                        // already printed above via System.out.println(result) -
                        // this just waits for the actual "0" before moving on,
                        // instead of silently falling back to the main menu
                        while (true) {
                            String deleteInput = scanner.nextLine();
                            if (deleteInput.equals("0")) {
                                System.out.println("Message deleted.");
                                break;
                            } else {
                                System.out.println("Invalid input. Press 0 to delete the message.");
                            }
                        }
                    }
                    // if "store" was chosen, nothing extra needs to
                    // happen here - sentMessage() already saved it
                    break;
 
                case "2":
                    // feature intentionally not built yet, per the brief
                    System.out.println("Coming Soon.");
                    break;
 
                case "3":
                    // returnTotalMessages() and printMessages() read from
                    // shared (static) data, so any Message object can call
                    // them - this handles the case where the user quits
                    // without ever sending a message
                    if (sessionMessage == null) {
                        sessionMessage = new Message("", "");
                    }
                    System.out.println("Total messages sent: " + sessionMessage.returnTotalMessages());
                    System.out.println(sessionMessage.printMessages());
                    return;
 
                default:
                    System.out.println("Invalid option, please choose 1, 2, or 3.");
                    break;
            }
        }
    }
}