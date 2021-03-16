package com.example.pos;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginActivityTest {

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