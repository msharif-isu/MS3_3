package com.example.itinerarybuddy;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.itinerarybuddy.ui.home.HomeFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class AinaSystemTest {

    @Before
    public void setup(){

        FragmentScenario<HomeFragment> fragmentScenario = FragmentScenario.launchInContainer(HomeFragment.class);

    }

    @Test
    public void testShowAddItineraryDialog() {
        // Click on the "Add Itinerary" button
        onView(withId(R.id.addItinerary)).perform(click());

        // Verify that the "Add Itinerary" dialog is displayed
        onView(withText("Add Itinerary")).check(matches(isDisplayed()));
    }


}