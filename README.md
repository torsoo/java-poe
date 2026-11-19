
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

##Changes I Made to Part 2
After getting feedback on Part 2, I went back and corrected the following. 
These changes are in the code that Part 3 is built on.

The user enters a number, and a for loop now takes exactly that many messages, whatever is chosen for each one
One counter (the loop counter) goes up for every message, so every message gets its own number and its own hash
The program now asks again until the answer is Send, Store or Disregard
checkRecipientCell and checkMessageLength are now static, so they can be called without creating a message
The ID must be exactly 10 digits
Only stored messages are saved to storedMessages.json
The file is now written with the json-simple library, which escapes them correctly, and the code is attributed in a comment
The tests now save to a temporary file, so the real file is never touched
I added tests for an ID that is too short, an ID with letters, a generated ID, the message number in the hash, and an invalid choice
I added the TestJava.yml GitHub Action, which runs the unit tests, and corrected .gitignore so the build, dist and nbproject/private folders are not committed

###Part 3: Store Data and Display

Part 3 adds arrays that are filled while the program runs (nothing is hard-coded), a fourth menu option, and automated unit tests.

**Main menu**
1.Send Messages
2.Show recently sent messages - prints the full details of every message sent in this run
3.Quit - shows the total number of messages sent, then exits
4.Stored Messages - opens the stored messages menu

**Arrays**
sentMessages-the text of every message that was sent
disregardedMessages-the text of every message that was disregarded
storedMessages-the text of every stored message, including those read from the JSON file
messageIDs-the message ID of every sent and stored message
messageHashes-	the message hash of every sent and stored message

messageRecipients, messageTexts and messageFlags run alongside messageIDs and messageHashes.
They are parallel arrays: position 0 in each of them describes the same message. They are ArrayLists, which are resizable arrays.
A disregarded message is deleted, so it only appears in disregardedMessages.

## Stored messages menu
a.Displays the sender and recipient of all stored messages
b.Displays the longest stored message
c.Searches for a message ID and displays the recipient and message
d.Searches for all messages for a particular recipient
e.Deletes a message using its message hash
f.Displays a report with the hash, ID, recipient and message of all stored messages
x.Goes back to the main menu

## JSON file

Messages the user chooses to store are saved to storedMessages.json. 
When the program starts, and each time the Stored Messages menu opens, the file is read into the 
stored arrays. A message whose ID is already in the arrays is skipped,so nothing is added twice.
Deleting a message by its hash also removes it from the file.
The file is written and read with the json-simple library (lib/json-simple-1.1.jar), which escapes quotes, 
backslashes and line breaks. The code follows the json-simple documentation and is attributed in Message.java.


## Test data
The unit tests use the test data from the assignment brief:

Test data	Recipient	Message	Flag
Message 1	+27834557896	Did you get the cake?	Sent
Message 2	+27838884567	Where are you? You are late! I have asked you to be on time.	Stored
Message 3	+27834484567	Yohoooo, I am at your gate.	Disregard
Message 4	0838884567	It is dinner time!	Sent
Message 5	+27838884567	Ok, I am leaving without you.	Stored

Message 4 uses 0838884567, which does not start with +27. The console menu does not accept it, so the unit tests create that message directly.

## How to Run
1.Open the project in NetBeans.
2.Check that json-simple is listed under Libraries.
3.Right-click Main.java and choose Run File.
4.Register, log in, then use the QuickChat menu.

## How to Run the Tests

In NetBeans

1.Make sure JUnit 4 is added under Project Properties -> Libraries -> Test Libraries.
2.Right-click MessageTest.java (the Message and Part 3 tests) or TestCode.java (the Login tests) and choose Test File.
3.Check the Test Results panel for pass or fail.

The tests save to a temporary file, so they never change the real storedMessages.json.

## On GitHub (automated)

The workflow .github/workflows/TestJava.yml runs on every push and pull request. It compiles the project, 
downloads JUnit 4, and runs MessageTest and TestCode. The result shows under the repository's Actions tab and in the badge at the top of this file.
 
## What the Part 3 tests check
**Arrays:** the sent, disregarded and stored arrays, and the hash and ID arrays, contain the expected test data
**Longest message:** the longest stored message is "Where are you? You are late! I have asked you to be on time."
**Search for a message ID:** message 4's ID returns "It is dinner time!"
**for a recipient:** +27838884567 returns messages 2 and 5
**Delete using a message hash:** message 2 is removed from the arrays and the file
**Display report:** the sent and stored reports show the hash, recipient and message
**JSON:** messages are saved and read back, special characters survive, and duplicates are not added
**Part 2 rules:** message length, recipient number, message ID, message hash and message numbers

## Version Control (Git and GitHub)
main is kept working at all times. Each part is built on its own feature branch, e.g. part2-messages and part3-stored-messages, committed in small steps with clear messages, and merged into main through a pull request.

1.Save the files in NetBeans
2.Commit the changes in GitHub Desktop with a descriptive message
3.Push the branch to GitHub
4.Open a pull request, check that the TestJava workflow passes, then merge it

## Technologies Used
-Java and NetBeans IDE - the language and the editor
-JUnit 4 - used to write and run automated tests (TestCode.java for Login.java, MessageTest.java for Message.java) against the test data in the assignment brief
-json-simple 1.1 - used to write and read storedMessages.json
-Git and GitHub - used for version control, tracking changes over time through commits, and hosting the final repository
-GitHub Actions - used to run the unit tests automatically
-W3Schools - used to verify and help with some of my work
-YouTube - I also used this channel as a guide: https://youtube.com/@brocodez?si=RIxW1G6njghsDZ4E

## Author
Thandeka Radebe
