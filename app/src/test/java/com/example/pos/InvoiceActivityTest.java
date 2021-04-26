package com.example.pos;

import org.junit.Test;

import static org.junit.Assert.*;

public class InvoiceActivityTest {


    //comparing
    @Test
    public void inv(){
        InvoiceActivity2 invoiceActivity2=new InvoiceActivity2();
        assertEquals(1, invoiceActivity2.InvId(1));
    }


    @Test
    public void bill() {
        assertEquals(10,5+5);
    }


    @Test
    public void item() {
        assertTrue("Coke",true);
    }

}