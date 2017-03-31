package com.testingandroid.Tutorial5;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.testingandroid.R;
import com.testingandroid.login.LoginActivity;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class GalleryPickerTesting {
    
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class,true);
    
    
    @Before
    public void initThingsHere() {
        //do stuff like database or preference or image copying here
    }
    
    @Test
    public void checkTextOnScreen() {
        //to check view on screen
    
        Intent resultData = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultData.setData(Uri.parse(("content://media/external/images/media/337663")));
        Matcher<Intent> MediaPickIntent = allOf(hasAction(Intent.ACTION_PICK), hasData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
        Intents.init();
        intending(MediaPickIntent).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData));
    
        onView(withText("Gallery")).perform(click());
        onView(withId(R.id.btnGalleryPick)).perform(click());
        intended(MediaPickIntent);
        //To check the image pick works or not
        try{
            Thread.sleep(4500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    
}
