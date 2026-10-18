
# PROG5121POE

PROG5121POE
Name: Thandeka Nobuhle Radebe Student Number: ST10530150 
Project: Registration and login Feature-Portfolio of EvIdence(POE)

project Description
This project is a Java console application that simulates the registration and login functionality of a user account system. The program validates a new user's username, password, and South African cell phone number against a set of formatting rules before allowing an account to be registered, then authenticates that user against their stored credentials before granting access. The project was built using object-oriented principles, separating the input/output logic (Main) from the business logic and validation rules (Login).

2.Registration The user is prompted for a password. The system checks that it is at least eight characters long and contains at least one capital letter, a digit, and a special character, else it re-prompts until the input is valid.

The user is also prompted for a South African cell phone number. The system checks that it contains the international code (+27) followed by nine digits, it re-prompts if the input is invalid until it is valid.

Once all three fields pass validation, the user's details are stored and a success message is displayed.

Login: The user is prompted for their registered username and password. The system checks the entered credentials against what was stored during registration. If they match, a personalised welcome message is displayed. If not, the system displays an error message and re-prompts until the user inputs the correct stored credential then login succeeds.

How to Run
1.Open project in NetBeans 2.Right-click Main.java...-Run file 3.Follow the console prompts to register then login.

How to Run the Tests
Make sure JUint is added under project properties--libraries -> Test Libraries 2.Right-click TestCode.java -> Test File 3.Check the Test Results panel for pass/fail results Test data used matches the exact values given in the assignment brief (e.g. username kyl_1, password Ch&&sec@ke99!, cell number +27838968976), so results can be checked directly against the brief's expected outputs.
Pushing Changes to GitHub(via NetBeans)
1.Save the file in NetBeans 2.Right-click the file -> Git > Add 3.Right-click again -> Git > Commit, with a descriptive message 4.Push it -> Git > Remote > Push 5.Check the GitHub repo online afterward to confirm it updated

Technologies Used
Java JUnit 4 (unit testing)- I used it to write and run automated tests (TestCode.java) that verify each method inLogin.java behaves correctly against the test data specified in the assignment brief

NetBeans IDE Git& GitHub- i used these for version control , tracking changes in the project over time through commits, and hosting final repository for

W3Schools-to verify help me with some of my work

I also used this YouTube channel to guide me as well https://youtube.com/@brocodez?si=RIxW1G6njghsDZ4E

Author
Thandeka Radebe


origin/main

**Name:** Thandeka Nobuhle Radebe 
**Student Number:** ST10530150
**Project:** Registration and login Feature-Portfolio of EvIdence(POE)

---

## project Description
This project is a Java console application that simulates the
registration and login functionality of a user account system. The
program validates a new user's username, password, and South
African cell phone number against a set of formatting rules before
allowing an account to be registered, then authenticates that user
against their stored credentials before granting access. The project
was built using object-oriented principles, separating the
input/output logic (Main) from the business logic and validation
rules (Login).

---

## How part 1 functions
So this is where the full account registration and login works 
1.Firstly the user is prompted for a username. The system will then 
check if contains an underscore (_) and is no more that 5 characters long,
else the system will re-prompt until the input is valid 

2.**Registration**
The user is prompted for a password. The system checks that it
is at least eight characters long and contains at least one
capital letter, a digit, and a special character, else it
re-prompts until the input is valid.

The user is also prompted for a South African cell phone number. The
system checks that it contains the international code (+27)
followed by nine digits, it re-prompts if the input is invalid until it is valid.

Once all three fields pass validation, the user's details are
stored and a success message is displayed.

**Login:**
The user is prompted for their registered username and
password.
The system checks the entered credentials against what was
stored during registration.
If they match, a personalised welcome message is displayed. If
not, the system displays an error message and re-prompts until
the user inputs the correct stored credential then login succeeds.

---

## How to Run 
1.Open project in NetBeans 
2.Right-click Main.java...-Run file
3.Follow the console prompts to register then login.


## How to Run the Tests
1. Make sure JUint is added under project properties--libraries 
-> Test Libraries
2.Right-click TestCode.java -> Test File
3.Check the Test Results panel for pass/fail results
Test data used matches the exact values given in the assignment
brief (e.g. username kyl_1, password Ch&&sec@ke99!, cell number
+27838968976), so results can be checked directly against the
brief's expected outputs.


## Pushing Changes to GitHub(via NetBeans)
1.Save the file in NetBeans
2.Right-click the file -> Git > Add
3.Right-click again -> Git > Commit, with a descriptive message
4.Push it -> Git > Remote > Push
5.Check the GitHub repo online afterward to confirm it updated


---

## Technologies Used
Java
JUnit 4 (unit testing)- I used it to write and run
automated tests (TestCode.java) that verify each method inLogin.java 
behaves correctly against the test data specified in
the assignment brief

NetBeans IDE
Git& GitHub- i used these for version control , tracking changes in
the project over time through commits, and hosting final repository for 

W3Schools-to verify help me with some of my work 

I also used this YouTube channel to guide me as well https://youtube.com/@brocodez?si=RIxW1G6njghsDZ4E

## Part 2: Sending messages
### Project Description
Part 2 adds messaging to the chat application. After logging in, the user can create messages, validate them, and choose to send, store or disregard each one. The Message class holds the message data and the rules, and Main.java handles the menu and user input.

### Menu Options
1. *Send Messages* - enter how many messages to create, then enter each recipient and message text.
2. *Show recently sent messages* - prints the messages sent during this run of the program.
3. *Quit* - exits the program and shows the total number of messages sent.

### Message Rules
- *Message ID:* a randomly generated 10-digit number.
- *Recipient number:* must be a valid South African cell phone number with the international code (for example +27718693002).
- *Message text:* a maximum of 250 characters. If it is too long, the program tells the user by how many characters it exceeds the limit (for example "Message exceeds 250 characters by 10; please reduce the size.").
- *Message hash:* each message gets a hash built from its ID, its number and its text.
- *Send options:* the user can *Send, **Store* or *Disregard* each message.

### Data Storage
Messages the user chooses to store are saved to storedMessages.json using the json-simple library (lib/json-simple-1.1.jar).

### Unit Tests
MessageTest.java has JUnit 4 tests that cover:
- message length (success and failure cases)
- recipient number validation
- message ID and message hash creation

To run them in NetBeans, right-click MessageTest.java and choose *Test File*.

### How to Run
1. Clone the repository and open the project in NetBeans.
2. Check that json-simple and JUnit are listed under Libraries.
3. Run Main.java, register or log in, then use the menu.

## Author
Thandeka Radebe
