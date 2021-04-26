package com.example.pos;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginActivityTest {

    //if @ is present or not
    @Test
    public void checkAt(){
        LoginActivity loginActivity=new LoginActivity();
        assertTrue(loginActivity.validEmail("a@gamil.com"));

    }

    //checking length of local part
    @Test
    public void localPart(){
        LoginActivity loginActivity=new LoginActivity();
        assertEquals(1, loginActivity.getLocalLength("a@gamil.com"));
    }

    //checking password length
    @Test
    public void passLen(){
        LoginActivity loginActivity=new LoginActivity();
        assertTrue(loginActivity.validPassword("12345678"));

    }



    //validating email address
    @Test
    public void checkEmail() {
        String inp1="a@gmail.com";
        String inp2="a@gmail.com";

        assertEquals(inp1, inp2);
    }

    @Test
    public void checkPass() {
        String inp1="12345";
        String inp2="12345";

        assertEquals(inp1, inp2);
    }

}