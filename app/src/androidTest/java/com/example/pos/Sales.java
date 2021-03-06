package com.example.pos;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class Sales {


    //private MapActivity mapActivity;
    private DataObj dataObj;
    private InventObj inventObj;

    @Before
    public void setUp(){
       // Context ctx= InstrumentationRegistry.getContext();
        dataObj= new DataObj();
        inventObj=new InventObj();
    }

    //dataObj tests
    @Test
    public void testName() throws Exception{

        dataObj.setCustomerName("abu");
        String input=dataObj.getCustomerName();
        assertEquals("abu",input);
    }

    @Test
    public void testNum() throws Exception{

        dataObj.setInvoiceNo(125);
        long input=dataObj.getInvoiceNo();
        assertEquals(125,input);
    }

    //InventObj tests

    @Test
    public void sellTest(){

        inventObj.setCostPrice(11.5);
        double input=inventObj.getCostPrice();
        assertEquals(11.5,input,11.5);
    }
    @Test
    public void typeTest (){
        inventObj.setItemType("dry");
        String input=inventObj.getItemType();
        assertEquals("dry", input);
    }




}