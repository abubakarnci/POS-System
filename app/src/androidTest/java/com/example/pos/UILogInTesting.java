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
import static org.hamcrest.Matchers.not;

import androidx.test.ext.junit.rules.ActivityScenarioRule;


@RunWith(AndroidJUnit4.class)
public class UILogInTesting {

    @Rule
    public ActivityTestRule<LoginActivity> loginActivity= new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp(){
        loginActivity.getActivity();
    }

    @Test
    public void emailFieldDisplay(){

        onView(withId(R.id.email)).check(matches((isDisplayed())));
    }

    @Test
    public void passFieldDisplay(){

        onView(withId(R.id.password)).check(matches((isDisplayed())));
    }

    @Test
    public void typeEmail(){

        onView(withId(R.id.email)).perform(typeText("a@gmail"));


    }
    @Test
    public void typePass(){
        onView(withId(R.id.password)).perform(typeText("qwer1234"));

    }
    @Test
    public void clickLogin(){

        onView(withId(R.id.button)).perform(click());

    }

    @Test
    public void clickForgot(){

        onView(withId(R.id.forgot)).perform(click());

    }

    @Test
    public void clickSignup(){

        onView(withId(R.id.textView)).perform(click());

    }

}
