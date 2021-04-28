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
public class UIInventoryTesting {

    @Rule
    public ActivityTestRule<Inventory> inventoryActivity= new ActivityTestRule<>(Inventory.class);

    @Before
    public void setUp(){
        inventoryActivity.getActivity();
    }

    @Test
    public void nameDisplay(){

        onView(withId(R.id.nameTv)).check(matches((isDisplayed())));
    }
    @Test
    public void typeDisplay(){

        onView(withId(R.id.typeTv)).check(matches((isDisplayed())));
    }

    @Test
    public void cPriceDisplay(){

        onView(withId(R.id.costTv)).check(matches((isDisplayed())));
    }
    @Test
    public void sPriceDisplay(){

        onView(withId(R.id.sellTv)).check(matches((isDisplayed())));
    }




    @Test
    public void nameTF(){

        onView(withId(R.id.itemName)).perform(typeText("Coke"));
    }
    @Test
    public void typeTF(){

        onView(withId(R.id.itemType)).perform(typeText("Drink"));
    }

    @Test
    public void cPriceTF(){

        onView(withId(R.id.costPrice)).perform(typeText("11.6"));
    }
    @Test
    public void sPriceTF(){

        onView(withId(R.id.sellPrice)).perform(typeText("15.5"));
    }


    @Test
    public void clickSave(){

        onView(withId(R.id.button)).perform(click());

    }

    @Test
    public void clickEdit(){

        onView(withId(R.id.edit)).perform(click());

    }




}
