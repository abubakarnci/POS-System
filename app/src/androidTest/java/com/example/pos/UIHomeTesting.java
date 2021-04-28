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
public class UIHomeTesting {

    @Rule
    public ActivityTestRule<HomeActivity> homeActivity= new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void setUp(){
        homeActivity.getActivity();
    }

    @Test
    public void clickSales(){

        onView(withId(R.id.sales)).perform(click());
    }
    @Test
    public void clickScan(){

        onView(withId(R.id.scan)).perform(click());
    }@Test
    public void clickOld(){

        onView(withId(R.id.oldInvoice)).perform(click());
    }@Test
    public void clickInven(){

        onView(withId(R.id.items)).perform(click());
    }@Test
    public void clickMap(){

        onView(withId(R.id.map)).perform(click());
    }@Test
    public void clickLogout(){

        onView(withId(R.id.logout)).perform(click());
    }


}
