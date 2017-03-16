package com.testingandroid.Tutorial1;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.testingandroid.login.LoginActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class CheckTextOnScreenTest {
    
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class,true);
    
    
    @Before
    public void initThingsHere() {
        //do stuff like database or preference or image copying here
    }
    
    @Test
    public void checkTextOnScreen() {
        //to check view on screen
        onView(withText("Hello Floks!")).check(matches(isDisplayed()));
    }
    
    
}
