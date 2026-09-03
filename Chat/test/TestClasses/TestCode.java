
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package TestClasses;

import chat.Login;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author thand
 */
public class TestCode {

     //checkUserName() returns boolean 
     @Test
    public void TestUserName()
    {
        Login l = new Login();
        boolean actual = l.checkUsername("kyl_1");
        assertTrue(actual);
    }

    @Test
    public void TestUserNameIncorrect()
    {
        Login l = new Login();
        boolean actual = l.checkUsername("kyle!!!!!");
        assertFalse(actual);
    }

    // === checkPasswordComplexity() returns boolean ===

    @Test
    public void TestPasswordComplexity()
    {
        Login l = new Login();
        boolean actual = l.checkPasswordComplexity("Ch&&sec@ke99!");
        assertTrue(actual);
    }

    @Test
    public void TestPasswordComplexityIncorrect()
    {
        Login l = new Login();
        boolean actual = l.checkPasswordComplexity("password");
        assertFalse(actual);
    }

    // === checkCellPhoneNumber() returns boolean ===

    @Test
    public void TestCellPhoneNumber()
    {
        Login l = new Login();
        boolean actual = l.checkCellPhoneNumber("+27838968976");
        assertTrue(actual);
    }

    @Test
    public void TestCellPhoneNumberIncorrect()
    {
        Login l = new Login();
        boolean actual = l.checkCellPhoneNumber("08966553");
        assertFalse(actual);
    }

    // === registerUser() returns String - assertEquals against brief wording ===

    @Test
    public void TestRegisterUserUsernameIncorrect()
    {
        Login l = new Login();
        String expected = "Username is not correctly formatted; please ensure that your "
                + "username contains an underscore and is no more than five "
                + "characters in length.";
        String actual = l.registerUser("kyle!!!!!", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        assertEquals(expected, actual);
    }

    @Test
    public void TestRegisterUserPasswordIncorrect()
    {
        Login l = new Login();
        String expected = "Password is not correctly formatted; please ensure that the "
                + "password contains at least eight characters, a capital letter, "
                + "a number, and a special character.";
        String actual = l.registerUser("kyl_1", "password", "Kyle", "Smith", "+27838968976");
        assertEquals(expected, actual);
    }

    @Test
    public void TestRegisterUserCellPhoneIncorrect()
    {
        Login l = new Login();
        String expected = "Cell phone number incorrectly formatted or does not contain "
                + "international code.";
        String actual = l.registerUser("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "08966553");
        assertEquals(expected, actual);
    }

    @Test
    public void TestRegisterUserAllValid()
    {
        Login l = new Login();
        String expected = "Username successfully captured. Password successfully captured. "
                + "Cell phone number successfully added. You have been registered successfully.";
        String actual = l.registerUser("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        assertEquals(expected, actual);
    }

    // === loginUser() returns boolean ===

    @Test
    public void TestLoginSuccessful()
    {
        Login l = new Login();
        l.registerUser("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        boolean actual = l.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertTrue(actual);
    }

    @Test
    public void TestLoginFailed()
    {
        Login l = new Login();
        l.registerUser("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        boolean actual = l.loginUser("kyl_1", "wrongPassword1!");
        assertFalse(actual);
    }

    // === returnLoginStatus() returns String - assertEquals ===

    @Test
    public void TestReturnLoginStatusSuccess()
            
    {
        Login l = new Login();
        l.registerUser("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        boolean loginSuccess = l.loginUser("kyl_1", "Ch&&sec@ke99!");
        String expected = "Welcome Kyle, Smith it is great to see you again.";
        String actual =  l.returnLoginStatus(loginSuccess);
        assertEquals(expected, actual);
    }

    @Test
    public void TestReturnLoginStatusFailure()
    {
        Login l = new Login();
        l.registerUser("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        boolean loginSuccess = l.loginUser("kyl_1", "wrongPassword1!");
        String expected = "Username or password incorrect, please try again.";
        String actual = l.returnLoginStatus(loginSuccess);
        assertEquals(expected, actual);
    }
}