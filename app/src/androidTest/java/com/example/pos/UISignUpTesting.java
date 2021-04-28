package com.example.pos;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class UISignUpTesting {

    @Rule
    public ActivityTestRule<MainActivity> mainActivity= new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp(){
        mainActivity.getActivity();
    }

    @Test
    public void emailFieldDisplay(){

        onView(withId(R.id.email)).check(matches((isDisplayed())));
    }
    @Test
    public void nameFieldDisplay(){

        onView(withId(R.id.name)).check(matches((isDisplayed())));
    }

    @Test
    public void passFieldDisplay(){

        onView(withId(R.id.password)).check(matches((isDisplayed())));
    }
    @Test
    public void cPassFieldDisplay(){

        onView(withId(R.id.cPassword)).check(matches((isDisplayed())));
    }


    @Test
    public void typeEmail(){

        onView(withId(R.id.email)).perform(typeText("a@gmail"));


    }
    @Test
    public void typeName(){

        onView(withId(R.id.name)).perform(typeText("Sam"));


    }
    @Test
    public void typePass(){
        onView(withId(R.id.password)).perform(typeText("qwer1234"));

    }
    @Test
    public void typeCPass(){
        onView(withId(R.id.cPassword)).perform(typeText("qwer1234"));

    }
    @Test
    public void clickSignup(){

        onView(withId(R.id.button)).perform(click());

    }

    @Test
    public void clickSocial(){

        onView(withId(R.id.google)).perform(click());

    }

    @Test
    public void clickLogin(){

        onView(withId(R.id.textView)).perform(click());

    }

}
