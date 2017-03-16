package com.testingandroid.Tutorial3;


import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.testingandroid.R;
import com.testingandroid.login.LoginActivity;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class PerfomClickAndCheckTextError {
    
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class, true);
    
    //To get resources in testing
    Resources resources;
    
    @Before
    public void initThingsHere() {
        //do stuff like database or preference or image copying here
        resources = InstrumentationRegistry.getTargetContext().getResources();
    }
    
    @Test
    public void checkBlankEmailError() {
        //to check view on screen
        onView(withId(R.id.btnLoginButton)).perform(click());
        onView(withId(R.id.edUsername)).check(matches(hasErrorText(resources.getString(R.string.msg_enter_valid_email))));
    }
    
    @Test
    public void checkBlankPasswordError() {
        //to check view on screen
        onView(withId(R.id.edUsername)).perform(typeText("youremail@yopmail.com"), closeSoftKeyboard());
        onView(withId(R.id.btnLoginButton)).perform(click());
        onView(withId(R.id.edPassword)).check(matches(hasErrorText(resources.getString(R.string.msg_enter_valid_password))));
    }
    
}
