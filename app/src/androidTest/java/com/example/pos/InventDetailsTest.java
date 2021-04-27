package com.example.pos;


import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class InventDetailsTest {

    private InventObj inventObj;


    @Before
    public void setUp(){


        inventObj= new InventObj();
    }


    @Test
    public void itemNameTest() throws Exception{

        inventObj.setItemName("coke");
        String input=inventObj.getItemName();
        assertEquals("coke",input);
    }

    @Test
    public void sellTest(){

        inventObj.setCostPrice(15.5);
        double input=inventObj.getCostPrice();
        assertEquals(15.5,input,15.5);
    }

    @Test
    public void typeTest (){
        inventObj.setItemType("dry");
        String input=inventObj.getItemType();
        assertEquals("dry", input);
    }
    @Test
    public void itemNoTest (){
        inventObj.setItemNo(123);
        long input=inventObj.getItemNo();
        assertEquals(123, input);
    }


}
