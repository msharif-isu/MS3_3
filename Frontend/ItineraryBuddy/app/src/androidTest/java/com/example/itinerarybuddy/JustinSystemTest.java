package com.example.itinerarybuddy;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.itinerarybuddy.activities.LoginActivity;
import com.example.itinerarybuddy.data.UserData;
import com.example.itinerarybuddy.ui.home.ListGroups;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JustinSystemTest {

    private static final int DELAY_MS = 1000;

    private static final String id = "10";

    @Rule   // needed to launch the activity
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setup() {
        String username = "Justin";
        String password = "1";
        onView(withId(R.id.username_input)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.password_input)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());
        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void test1Login() {
        // Assert that the intended user has been logged in upon setup()
        assertEquals("Justin", UserData.getUsername());
    }

    @Test
    public void test2JoinGroup() {
        onView(withId(R.id.group_button)).perform(click());
        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }

        int start = ListGroups.getAdapter().getCount();
        onView(withId(R.id.add_group_button)).perform(click());
        onView(withId(R.id.group_code_input)).perform(typeText(id), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).perform(click());
        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }
        int end = ListGroups.getAdapter().getCount();

        //onView(withText("Test99\nDestination: Canada")).check(matches(isDisplayed()));

        assertEquals(start + 1, end);
    }

    @Test
    public void test3EditGroup() {
        onView(withId(R.id.group_button)).perform(click());
        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }

        // Make edits to group
        onView(withText("Test99\nDestination: Canada")).perform(click());
        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.options_button)).perform(click());
        onView(withText("Edit Travel Group")).perform(click());
        onView(withId(R.id.group_name_input)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.group_name_input)).perform(typeText("System Test"), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).perform(click());

        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }

        // Verify edits worked
        onView(withText("System Test\nDestination: Canada")).perform(click());
        onView(withId(R.id.group_title)).check(matches(withText("System Test")));

        // Restore group to original state
        onView(withId(R.id.options_button)).perform(click());
        onView(withText("Edit Travel Group")).perform(click());
        onView(withId(R.id.group_name_input)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.group_name_input)).perform(typeText("Test99"), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).perform(click());

        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void test4LeaveGroup() {
        onView(withId(R.id.group_button)).perform(click());
        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }

        int start = ListGroups.getAdapter().getCount();
        onView(withText("Test99\nDestination: Canada")).perform(click());
        onView(withId(R.id.options_button)).perform(click());
        onView(withText("Leave Group")).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }
        int end = ListGroups.getAdapter().getCount();

        //onView(withText("Test99\nDestination: Canada")).check(matches(not(isDisplayed())));

        assertEquals(start - 1, end);
    }

        /*
    @Test
    public void test2CreateGroup(){
        onView(withId(R.id.group_button)).perform(click());
        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }

        int start = ListGroups.getAdapter().getCount();
        onView(withId(R.id.create_group_button)).perform(click());
        onView(withId(R.id.group_name_input)).perform(typeText("System Test"), closeSoftKeyboard());
        onView(withId(R.id.group_destination_input)).perform(typeText("System Test"), closeSoftKeyboard());
        onView(withId(R.id.group_description_input)).perform(typeText("System Test"), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).perform(click());

        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }
        int end = ListGroups.getAdapter().getCount();

        assertEquals(start + 1, end);
    }
    */
}
