package com.example.pos;


import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class InvoiceDetailsTest {

    private DataObj dataObj;

    @Before
    public void setUp(){


        dataObj= new DataObj();
    }


    @Test
    public void testName() throws Exception{

        dataObj.setCustomerName("abu");
        String input=dataObj.getCustomerName();
        assertEquals("abu",input);
    }

    @Test
    public void testInvoiceNo() throws Exception{

        dataObj.setInvoiceNo(125);
        long input=dataObj.getInvoiceNo();
        assertEquals(125,input);

    }

    @Test
    public void testTBill() throws Exception{

        dataObj.settBill(11.2);
        double bill=dataObj.gettBill();
        assertEquals(11.2,bill,bill);

    }


}
