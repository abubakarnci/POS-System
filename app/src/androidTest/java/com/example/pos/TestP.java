package com.example.pos;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TestP {
    //private MapActivity mapActivity;
    private DataObj dataObj;

    @Before
    public void setUp(){
        //Context ctx= InstrumentationRegistry.getContext();
        dataObj= new DataObj();
    }

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

}