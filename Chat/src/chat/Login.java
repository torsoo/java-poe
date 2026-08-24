/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chat;

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
    private boolean ValidPassword;
    private boolean validCellPhoneNumber;
    
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
        
        if (password != null && password.length() >= 8) {
            return false;
        }
        
        
        for (int i = 0 ; i < password.length(); i++) {
            char chara = password.charAt(i);
            
            if (Character.isUpperCase(chara) && Character.isLetterOrDigit(chara) && Character.isDigit(chara)) {
                bolHasUpperCase = true;
                bolHasDigit = true;
                bolHasSpecial = true;
            }
        }
        return bolHasUpperCase && bolHasDigit && bolHasSpecial;
    }
    
    public boolean checkCellPhoneNumber (String cellPhoneNumber) {
        //declaration 
        boolean validCell = false;
        
        
        return validCell;
    }
    
    public String registerUser(String username, String Password, String firstName, String lastName, String cellPhoneNumber, String cellPhoneNmber ){
        //declaration 
        String message = "";
        boolean validUsername = checkUsername(username);
        boolean validPassword = checkPasswordComplexity(password);
        boolean validCellphone = checkCellPhoneNumber(cellPhoneNmber);
        
        
        if (validUsername && validPassword && validCellphone) {
            message = "User Successfully registered";
        }else {
            if (!validUsername) {
                message = "Username incorrect please try again";
            }
            
            if (!validPassword) {
                message ="Password incorredct please try again";
            }
            
            if (!validCellphone) {
                message = "Cell phone number incorrectly formatted or does not contain international code";
            }
        }
        return message;
    }
    
    public boolean loginUser(String username, String password) {
        
    }
    
    public String returnLoginStatus () {
    
    }
}
