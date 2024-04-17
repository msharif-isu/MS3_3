package com.example.itinerarybuddy;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;d
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertEquals;

import com.example.itinerarybuddy.activities.LoginActivity;
import com.example.itinerarybuddy.data.UserData;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class JustinSystemTest {

    private static final int DELAY_MS = 500;

    @Rule   // needed to launch the activity
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginTest(){
        String username = "Justin";
        String password = "1";
        onView(withId(R.id.username_input)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.password_input)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
        }

        // Assert that the intended user has been logged in
        assertEquals("Justin", UserData.getUsername());
    }
}
