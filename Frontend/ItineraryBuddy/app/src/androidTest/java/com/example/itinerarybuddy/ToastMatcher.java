package com.example.itinerarybuddy;

import android.os.IBinder;
import android.view.WindowManager;

import androidx.test.espresso.Root;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ToastMatcher extends TypeSafeMatcher<Root> {
    @Override
    protected boolean matchesSafely(Root item) {
        int type = item.getWindowLayoutParams().get().type;
        if((type == WindowManager.LayoutParams.TYPE_TOAST)){
            IBinder token1 = item.getDecorView().getWindowToken();
            IBinder token2 = item.getDecorView().getApplicationWindowToken();
            if(token1 == token2){
                return true;
            }
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("toast");
    }
}
