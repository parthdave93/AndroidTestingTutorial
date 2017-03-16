package com.testingandroid.Tutorial4;


import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.InstrumentationInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.testingandroid.R;
import com.testingandroid.login.LoginActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class PassDataInActivityTest {
    
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class, true) {
        @Override
        protected Intent getActivityIntent() {
            Log.d(PassDataInActivityTest.class.getCanonicalName(), "getActivityIntent() called");
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent intent = new Intent(targetContext, LoginActivity.class);
            intent.putExtra("testingcheck", true);
            return intent;
        }
    };
    
    
    @Before
    public void initThingsHere() {
        //do stuff like database or preference or image copying here
    }
    
    @Test
    public void checkBlankEmailError() {
        //to check view on screen
        Bundle bundle = mActivityTestRule.getActivity().getIntent().getExtras();
        assertThat(bundle.getBoolean("testingcheck"), is(true));
        System.out.println("testingcheck:" + bundle.getBoolean("testingcheck"));
    }
}
