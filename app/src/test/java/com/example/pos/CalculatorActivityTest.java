package com.example.pos;

import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorActivityTest {

    @Test
    //sending inputs
    public void sendInputs(){
        CalculatorActivity calculatorActivity=new CalculatorActivity();
        assertEquals(25, calculatorActivity.getNumbers(5 ,5));
    }


    //if . is present or not
    @Test
    public void checkDot(){
        CalculatorActivity calculatorActivity=new CalculatorActivity();
        assertTrue(calculatorActivity.validDot("1.23"));

    }


    @Test
    public void simpAdd() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void simpMulti() {
        assertEquals(6, 3 * 2);
    }

    @Test
    public void simpDivi() {
        assertEquals(2, 4 / 2);
    }
}