package com.testingandroid.recorder;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.testingandroid.R;
import com.testingandroid.login.LoginActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecorderActivityTest {
    
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);
    
    @Test
    public void recorderActivityTest() {
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.edUsername), withParent(allOf(withId(R.id.clMainContainer), withParent(withId(android.R.id.content)))), isDisplayed()));
        appCompatEditText.perform(replaceText("parth"), closeSoftKeyboard());
        
        ViewInteraction appCompatButton = onView(allOf(withId(R.id.btnLoginButton), withText("Login"), withParent(allOf(withId(R.id.clMainContainer), withParent(withId(android.R.id.content)))), isDisplayed()));
        appCompatButton.perform(click());
        
        ViewInteraction editText = onView(allOf(withId(R.id.edUsername), withText("parth"), childAtPosition(allOf(withId(R.id.clMainContainer), childAtPosition(withId(android.R.id.content), 0)), 1), isDisplayed()));
        editText.check(matches(hasErrorText("Please enter valid email address")));
        
    }
    
    private static Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position) {
        
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }
            
            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent) && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
