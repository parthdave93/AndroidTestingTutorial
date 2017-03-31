# AndroidTestingTutorial
Getting started with Espresso Unit Testing

Why Testing?
-------------------------------
testing is the process of evaluation a software item to detect differences between given input and expected output. 
<br/>

Categories of Testing
-------------------------------
1. Black box testing
2. White box testing<br> 
etc... others can be found <a href="http://www.softwaretestinghelp.com/types-of-software-testing/">here</a>
<br/>

Black box testing
-------------------------------
Tests are based on requirements and functionality.
<br/>

White box testing
-------------------------------
Tests are based on coverage of code statements, branches, paths, conditions.



TDD
-------------------------------
Test-driven development (TDD) is a software development process that relies on the repetition of a very short development cycle: requirements are turned into very specific test cases, then the software is improved to pass the new tests, only.
<br/>

Android Testing Tools
-------------------------------
1. <a href="https://developer.android.com/training/testing/ui-testing/espresso-testing.html">Espresso by google team</a>
2. <a href="http://robolectric.org/">Roboletric</a> 
3. <a href="https://appium.io/slate/en/tutorial/android.html?ruby#">Appium</a>



Espresso
-------------------------------
Mainly focused on UI and Thread idealization, which helps the unit tests to run without worring about api response state
It checks the threads and waits for ui thread to be idealize which is dismiss progress bar or any event which shows that activity is performing some event.
<br/>


Why Espresso
-------------------------------
Other tools like Roboletric is also famous for testing android apps but it has it's own android jar which is our basic android kit classes.
As google updates their support library often it's hard to keep in update for Roboletric. 
And to mock the android classes becomes hard with Roboletric.
Check out link here <a href="http://blog.triona.de/development/java/selecting-the-appropriate-android-test-framework.html">Roboletric vs Espresso</a> for more details.
 
 Let's start with testing
-------------------------------------
Let's check how to write test so in Espresso we have 

````
@Rule
public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class, true);
````
Above test rule defines for which activity you'r going to write test class
<br/>
Now <kbd>new ActivityTestRule<>(LoginActivity.class, true);</kbd> if you do want to open activity which is not launcher activity then pass true in constructor else pass nothing for second parameter <kbd>new ActivityTestRule<>(LoginActivity.class);</kbd>
<br/>
There is multiple ways to open activity so do checkout <a href="https://developer.android.com/training/testing/ui-testing/espresso-testing.html">Espresso Doc</a>. 

````

@RunWith(AndroidJUnit4.class)
public class PerfomClickAndCheckTextError {
    
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class, true);
    .......
}
````

You need to define that your going to use junit4 test class to run with below test class.
if your test case is large then mention <kbd>@LargeTest</kbd> above class

Now before going any further with real test code other things to mention here is if we want to run some method before activity launches you can do it by specifying <kbd>@Before</kbd> annotation above your method.

````

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
    }
````
 
Like above I had taken resources.
 
Tutorial 1
-------------------------------

We have started programming with hello world! program and for testing we will do the same. Simple step check the text is on the screen.
but before that let's check how to <kbd>find view</kbd> from the screen in Espresso

To check there is 2 steps:
1. Find view on screen
2. Check if exists or displayed
<br/>

Find view on screen
-----------------------------------

<br/>
As we have findViewById in android, so in background what android system will do is it will check for that id view from rootview and give it back,
we have <kbd>onView()</kbd> but to find views we can use multiple methods not by just ids.
we have <kbd>withId(), withText(), withTagKey()</kbd> etc.

Check if exists or displayed
-------------------------------

</br>
To check we have <kbd>check()</kbd> method in which we will give matcher which will conditionalize the view like if it displayed or not.

So we have 
````
    onView(withText("Hello Floks!")).check(matches(isDisplayed()));
````

above code checked that screen has some textview having text Hello Floks!


<br/>
<br/>

Tutorial 2
-------------------------------
Now that we have successed in finding view and performing checks we will move to step 2 which is perform events like typing and clicking.
to click
````
    onView(withText("Login")).perform(click());
````
<br/>
<br/>

Tutorial 3
-------------------------------
Merge click and checks in one 
````
    onView(withId(R.id.btnLoginButton)).perform(click());
    onView(withId(R.id.edUsername)).check(matches(hasErrorText(resources.getString(R.string.msg_enter_valid_email))));
````
<br/>
<br/>

<video width="500" controls>
  <source src="./screenshots/login_error_checks.mp4" type="video/mp4">
  Your browser does not support HTML5 video.
</video>
<br/>

Tutorial 4
-------------------------------
open activity with data in bundle as it's important to pass data with activity

you want to check activity with custom data
so before writing test if 

````
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
````


Tutorial 5
-------------------------------
Responding to external intents like gallery picks
It's hard to control external apps as with device applications can have different views so it's not steady like your UI. in this condition what you can do is develop dependency injected code where you can mock the intents results or you can give result of intents in testing.
We are trying to archive this:

<video width="500" controls>
  <source src="./screenshots/normal_gallery_pick.mp4" type="video/mp4">
  Your browser does not support HTML5 video.
</video>

Let's check without DI(Dependency Injection)
-----------------

For this you need to have espresso intents dependency in build.gradle file
````
    androidTestCompile ('com.android.support.test.espresso:espresso-intents:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })
````

excludes are important to exclude support libraries inside the espresso and use libs what you have included.

Now after dependency added we can move on to the main course which is to mock the intent results.
<br/>

2 things to keep in mind is <kbd>intending</kbd> and <kbd>intended</kbd>
1. Intending
It is used for specifying Espresso that when unit test wants to open this type of intent please respond with intended result which i'm giving you to give.
</br>
2. Intended
It is used to check if the event intended to open some activity or package? we can check that thing by this.

```
    Intent resultData = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    resultData.setData(Uri.parse(("content://media/external/images/media/162")));
    Matcher<Intent> MediaPickIntent = allOf(hasAction(Intent.ACTION_PICK), hasData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
    Intents.init();
    intending(MediaPickIntent).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData));
```

In above example we have action pick event matcher which gives espresso hint that i'm finding this intent and by initlizing the intent we are starting intent checks for every intents.
while intending tells that when I intend to do respond with the intent i'm giving.
Output:
<embed src="./screenshots/gallery_pick.mp4" autostart="false" height="500" width="500" />

Tutorial 6
-----------------------------------------------
Espresso works like charm for views with view hierarchy but it's difficult to find if view is not bound by viewgroup like google map.

It's hard to find marker in map as it's part of google map but not actual viewgroup so to perform the click event on google map marker what we can do here is
Use UiAutomator.

UiAutomator is a library which uses accessiblity nodes to determine the views and performs assertions or actions upon that.

add UiAutomator in build.gradle
````

    androidTestCompile("com.android.support.test.uiautomator:uiautomator-v18:2.1.1", {
        exclude group: "com.android.support", module: "support-annotations"
    })

````

After adding in build.gradle your ready to go
We will create map activity for testing this feature.
now to use UiAutomator we need to start with finding UIDevice.

```
    UiDevice mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
```
and to find the marker we need to find marker view or in UiAutomator world it is UiSelector.

````
    UiObject marker = mDevice.findObject(new UiSelector().descriptionContains("Marker in Sydney"));
````

in above we are finding the view or object which has "Marker in sydney" as their description. and our marker had one.
once we found the object we can perform click operation.

this is easy to find marker as it is bound to node but info window has been drawn to canvas so it can not be clicked and i had used a trick to click on info window.
basically info window is drawn above marker icon and so i already got marker object so i had taken marker bounds and performed click on it.


````
        try {
            marker.click();
            marker.clickTopLeft();
            Rect rects = marker.getBounds();
            mDevice.click(rects.centerX(), rects.top - 30);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
````

If you do like the Tutorials please rate this repo and do share your own testing class or methodology.
output:
<video width="500" controls>
  <source src="./screenshots/google_marker_click.mp4" type="video/mp4">
  Your browser does not support HTML5 video.
</video>