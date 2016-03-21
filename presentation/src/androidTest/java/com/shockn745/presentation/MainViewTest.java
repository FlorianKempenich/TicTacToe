package com.shockn745.presentation;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.shockn745.presentation.game.GameActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainViewTest {

    @Rule
    public ActivityTestRule<GameActivity> mActivityRule = new ActivityTestRule(GameActivity.class);

//    @Test
//    public void clickOnButton_xDisplayed() {
//        onView(withId(R.id.button00)).perform(click());
//        onView(withId(R.id.button00)).check(matches(withText("x")));
//    }

}