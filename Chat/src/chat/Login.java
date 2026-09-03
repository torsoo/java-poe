/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chat;

import java.util.Scanner;

/**
 *
 * @author thand
 */
public class Login {
    
    //declaration 
    String username;
    String password;
    String firstName;
    String lastName;
    String cellPhoneNumber; 
    String ValidPassword;
    String validCellPhoneNumber;
    
    public boolean checkUsername(String username) {
        //declaration 
        boolean validUsername = false;
        
        if (username.contains("_") && username.length() <= 5) {
            validUsername = true ;
            
        }
        return validUsername;
    }
    
    public boolean checkPasswordComplexity(String Password) {
        //declaration 
        boolean bolHasUpperCase = false;
        boolean bolHasDigit = false;
        boolean bolHasSpecial = false;
        //boolean validPassword = false;
        
        if (Password == null ||  Password.length() < 8) {
            return false;
        }
        
        //Checks if all requirements for the password are actually met one by one 
        for (int i = 0 ; i < Password.length(); i++) {
            char chara = Password.charAt(i);
            
            if (Character.isUpperCase(chara)){
                bolHasUpperCase = true;
            }else if (Character.isLetterOrDigit(chara)){
                bolHasDigit = true;
            }else if (!Character.isDigit(chara)) {            
                bolHasSpecial = true;
            }
        }
        //Only valid if all three were met for the password
        return bolHasUpperCase && bolHasDigit && bolHasSpecial;
    }
    
    public boolean checkCellPhoneNumber (String cellPhoneNumber) {
        //declaration 
        boolean validCell = false;
        
        if (cellPhoneNumber != null){
          
           validCell = cellPhoneNumber.matches("\\+27[0-9]{9}");
        }
        return validCell;
    }
    //register user
    //
    public String registerUser(String username, String password, String firstName, String lastName,String cellPhoneNumber ){
        //declaration 
        String message = "";
        boolean validUsername = checkUsername(username);
        boolean validPassword = checkPasswordComplexity(password);
        boolean validCellphone = checkCellPhoneNumber(cellPhoneNumber);
        
        
        if (validUsername && validPassword && validCellphone) {
            //Store the registered user's details so LoginUser() can check againt them
            this.username = username;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.cellPhoneNumber =cellPhoneNumber;
            
            message = "Username successfully captured. Password successfully captured." + "Cell phone number successfully added. You have been registered successfully.";
        }else {
            if (!validUsername) {
                message = "Username is not correctly formatted ;please ensure that your "+ "username contains an underscore and is no more than five"+"characters in length.";
            }
            
            if (!validPassword) {
                message ="Password is not correctly formatted; please ensure that the "+ "password contains atleast eight characters , a capital letter ,"+"a number , and a special character.";
            }
            
            if (!validCellphone) {
                message = "Cell phone number incorrectly formatted or does not contain" + " international code";
            }
        }
        return message;
    }
    //Login user
    public boolean loginUser(String username, String password) {
        //declaration
        boolean validLogin = false;
       
        if(this.username != null && this.username.equals(username) && this.password.equals(password)) {
            validLogin = true;
        }
        return validLogin;
    }
    
  
    
    public String returnLoginStatus (boolean loginSuccess) {
        //declaration
        String status= " ";
        
        if (loginSuccess){
            status ="Welcome "+ firstName +"  " +lastName +" it is great to see you again.";
            
        }else {
            status = "Username or password incorrect,please try again.";
        }
        return status;
    }
    public void promptLogin(Scanner scanner){
        while (true){
            System.out.print("Enter your registered username:");
            String enteredUsername = scanner.nextLine();
            
            System.out.print("Enter your registered password:");
            String enteredPassword = scanner.nextLine();
            
            boolean loginSuccess = loginUser(enteredUsername, enteredPassword);
            
            if(loginSuccess) {
                System.out.println(returnLoginStatus(loginSuccess));
                break;
            } else { 
                System.out.println(returnLoginStatus(loginSuccess));
                //no break here, the loop goes back to the top and asks again
            }
            
           
            
            
        }
        
    }
}
