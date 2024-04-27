package com.example.itinerarybuddy;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;

import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.itinerarybuddy.activities.LoginActivity;
import com.example.itinerarybuddy.data.UserData;
import com.example.itinerarybuddy.ui.home.ListGroups;
import com.example.itinerarybuddy.ui.home.LoadGroup;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JustinSystemTest {

    private static final int DELAY_MS = 1000;

    private static final String id = "10";

    @Rule   // needed to launch the activity
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    /**
     * Used for toast processing.
     */
    private View decorView;

    @Before
    public void setup() {
        activityRule.getScenario().onActivity(new ActivityScenario.ActivityAction<LoginActivity>() {
            @Override
            public void perform(LoginActivity activity) {
                decorView = activity.getWindow().getDecorView();
            }
        });

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
        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }
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

    @Test
    public void test5ViewGroup() {
        onView(withId(R.id.group_button)).perform(click());
        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withText("Test99\nDestination: Canada")).perform(click());
        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.options_button)).perform(click());
        onView(withText("View Group Details")).perform(click());

        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withText("Justin (Group Creator)")).check(matches(isDisplayed()));
    }

    @Test
    public void test5Chat() {
        onView(withId(R.id.group_button)).perform(click());
        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withText("Test99\nDestination: Canada")).perform(click());
        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.chat_button)).perform(click());
        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }

        double message = Math.random() * 10;
        onView(withId(R.id.message_input)).perform(typeText(Double.toString(message)), closeSoftKeyboard());
        onView(withId(R.id.send_button)).perform(click());

        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withText(Double.toString(message))).check(matches(isDisplayed()));
    }

    @Test
    public void test5GroupImage() {
        onView(withId(R.id.group_button)).perform(click());
        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withText("Test99\nDestination: Canada")).perform(click());
        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.group_image)).perform(click());
        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.delete_image)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withText("Image Saved!"))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }
}
