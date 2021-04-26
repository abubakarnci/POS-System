package com.example.pos;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ChatActivityTest {


    //comparing input
    @Test
    public void checkRes(){
        ChatActivity chatActivity=new ChatActivity();
        assertTrue(chatActivity.getInput("what you doing"));

    }



    @Test
    public void valid() {
        assertTrue("Coke",true);
    }


}