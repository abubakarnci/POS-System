package com.example.pos;

import org.junit.Test;

import static org.junit.Assert.*;

public class HomeActivityTest {

    @Test
    public void check() {
        String inp1="hello";
        String inp2="hello";

        assertEquals(inp1, inp2);
    }


    @Test
    public void onCreate() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void onClick() {
        assertEquals(6, 3 * 2);
    }

    @Test
    public void onActivityResult() {
        assertEquals(2, 4 / 2);
    }
}