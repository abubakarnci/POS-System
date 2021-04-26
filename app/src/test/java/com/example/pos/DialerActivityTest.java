package com.example.pos;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DialerActivityTest {

    //if +353 is present as part of country code
    @Test
    public void codePresent(){
        Dialer dialer=new Dialer();
        assertTrue(dialer.validNumber("+353855856444"));

    }

    @Test
    public void codeNotPresent(){
        Dialer dialer=new Dialer();
        assertFalse(dialer.validNumber("855856444"));

    }

}
