/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chat;

/**
 *
 * @author thand
 */
import java.util.Scanner;
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try ( // TODO code application logic here
        // declaration
                Scanner scanner = new Scanner(System.in)) {
            Login login = new Login();
            
                    System.out.println("=== User Registration ===");

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

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

        String message = login.registerUser(username, password, firstName, lastName, cellPhoneNumber);
        System.out.println(message);

        System.out.println();
        System.out.println("=== User Login ===");

        // the while(true) login loop now lives inside Login.java itself -
        // Main just hands the scanner over and lets it run
        login.promptLogin(scanner);

        scanner.close();
    }
    }
}