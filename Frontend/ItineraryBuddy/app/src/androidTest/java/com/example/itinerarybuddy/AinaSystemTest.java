package com.example.itinerarybuddy;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
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
    public void testGenerateUniqueTripCode() {

        onView(withId(R.id.destinationEditText))
                .perform(ViewActions.typeText("Paris"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.startDateEditText))
                .perform(ViewActions.typeText("2024-04-22"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.endDateEditText))
                .perform(ViewActions.typeText("2024-04-30"), ViewActions.closeSoftKeyboard());

        onView(withText("Continue")).perform(ViewActions.click());
        // Verify that some text indicating a generated trip code is displayed
        onView(withText("Trip Code")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));


    }

    @Test
    public void testExtractTripCode() {
        // Assuming the itinerary string is displayed in a TextView with id "itineraryTextView"
        onView(withId(R.id.itineraryTextView)).perform(ViewActions.typeText("Destination: Paris, Start Date: 2024-04-22, End Date: 2024-04-30, Trip Code: ABC12345"), ViewActions.closeSoftKeyboard());

        // Verify that the extracted trip code is displayed correctly
        onView(withId(R.id.itineraryTextView)).check(ViewAssertions.matches(withText("ABC12345")));
    }

    @Test
    public void testShowAddItineraryDialog() {
        // Click on the "Add Itinerary" button
        onView(withId(R.id.addItinerary)).perform(ViewActions.click());

        // Verify that the "Add Itinerary" dialog is displayed
        onView(withText("Add Itinerary")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testShowDatePickerDialog() {
        // Click on the "Start Date" input field
        onView(withId(R.id.startDateEditText)).perform(ViewActions.click());

        // Verify that the date picker dialog is displayed
        onView(withText("OK")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}