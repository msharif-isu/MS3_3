package com.example.itinerarybuddy;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.containsString;

import android.widget.DatePicker;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.itinerarybuddy.ui.home.HomeFragment;

import org.hamcrest.Matchers;
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

    @Test
    public void testShowDatePickerDialog() {

        onView(withId(R.id.addItinerary)).perform(click());

        // Click on the "Start Date" input field
        onView(withId(R.id.startDateEditText)).perform(click());

        // Verify that the date picker dialog is displayed
        onView(withText("OK")).check(matches(isDisplayed()));
    }


    @Test
    public void testCreateItinerary() {
        // Click on the add itinerary button
        onView(withId(R.id.addItinerary)).perform(click());

        // Enter destination
        onView(withId(R.id.destinationEditText))
                .perform(typeText("Paris"), closeSoftKeyboard());

        // Set start date to today
        onView(withId(R.id.startDateEditText))
                .perform(click());
        // Set date and click OK
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2024, 4, 22)); // Set your desired date
        onView(withText("OK")).perform(click());

        // Wait for end date EditText to become visible
        onView(withId(R.id.endDateEditText)).check(matches(isDisplayed()));

        // Set end date to two days later
        onView(withId(R.id.endDateEditText))
                .perform(click());
        // Set date and click OK
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2024, 4, 23)); // Set your desired date
        onView(withText("OK")).perform(click());

        // Click on continue
        onView(withText("Continue")).perform(click());

        // Verify that the new itinerary is added
        onView(withText(containsString("Paris"))).check(matches(isDisplayed()));
    }

    @Test
    public void testDeleteItinerary(){
        onView(withId(R.id.addItinerary)).perform(click());

        // Enter destination
        onView(withId(R.id.destinationEditText))
                .perform(typeText("Japan"), closeSoftKeyboard());

        // Set start date to today
        onView(withId(R.id.startDateEditText))
                .perform(click());
        // Set date and click OK
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2024, 4, 22)); // Set your desired date
        onView(withText("OK")).perform(click());

        // Wait for end date EditText to become visible
        onView(withId(R.id.endDateEditText)).check(matches(isDisplayed()));

        // Set end date to two days later
        onView(withId(R.id.endDateEditText))
                .perform(click());
        // Set date and click OK
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2024, 4, 28)); // Set your desired date
        onView(withText("OK")).perform(click());

        // Click on continue
        onView(withText("Continue")).perform(click());

        onView(withId(R.id.iconPopUp)).perform(click());

        // Now, let's check if the popup menu is displayed
        onView(withText("Edit")).check(matches(isDisplayed()));
        onView(withText("Delete")).check(matches(isDisplayed()));

        // Click on "Delete" in the popup menu
        onView(withText("Delete")).perform(click());

        // Now, let's check if the delete confirmation dialog is displayed
        onView(withText("Confirm Delete")).check(matches(isDisplayed()));

        // Click on "Yes" to confirm deletion
        onView(withText("Yes")).perform(click());

        // Now, let's check if the itinerary is deleted from the list
        onView(withText(containsString("Destination: Japan"))).check(doesNotExist());
    }


}