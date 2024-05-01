package com.example.androidexample;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TestCasesGavin {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);

    /**
     * This test confirms the login sequence is functional and does not allow
     * entry given a wrong password. Confirms with given user account. When in the
     * activity, ensures all toolbar buttons are functional.
     * @throws InterruptedException
     */
    @Test
    public void testValidInput() throws InterruptedException {
        String userName = "john123";
        String wrongPassword = "lol1";
        String Password= "lol";

        // Enter data and click create group
        enterLoginDetails(userName, wrongPassword);
        onView(ViewMatchers.withId(R.id.login_button)).perform(click());

        //check if display is still the same
        onView(ViewMatchers.withId(R.id.signupRedirectText)).check(matches(isDisplayed()));
    }

    @Test
    public void testLogout() throws InterruptedException {
        String userName = "john123";
        String password = "lol";
        enterLoginDetails(userName, password);

        onView(ViewMatchers.withId(R.id.login_button)).perform(click());
        onView(ViewMatchers.withId(R.id.Profile)).perform(click());

        //Make sure profile screen is displayed
        onView(ViewMatchers.withId(R.id.profile_image)).check(matches(isDisplayed()));

        //Logout
        onView(ViewMatchers.withId(R.id.logout_button)).perform(click());

        //Make sure back on login page
        onView(ViewMatchers.withId(R.id.signupRedirectText)).check(matches(isDisplayed()));
    }

    @Test
    public void navBarTest() throws InterruptedException {
        String userName = "john123";
        String password = "lol";
        enterLoginDetails(userName, password);
        Thread.sleep(100);
        onView(ViewMatchers.withId(R.id.login_button)).perform(click());
        Thread.sleep(100);
        onView(ViewMatchers.withId(R.id.Profile)).perform(click());
        Thread.sleep(100);
        onView(ViewMatchers.withId(R.id.StudyGroups)).perform(click());
        Thread.sleep(100);
        onView(ViewMatchers.withId(R.id.Classes)).perform(click());
        Thread.sleep(100);
        onView(ViewMatchers.withId(R.id.Home)).perform(click());
        Thread.sleep(100);
        onView(ViewMatchers.withId(R.id.monthYearTV)).check(matches(isDisplayed()));
    }

    @Test
    public void userRegistrationTest() throws InterruptedException {
        String userName = "john123";
        String password = "lol";
        enterLoginDetails(userName, password);
        onView(ViewMatchers.withId(R.id.login_button)).perform(click());
        onView(ViewMatchers.withId(R.id.StudyGroups)).perform(click());
    }


//
//    @Test
//    public void testEmptyGroupName() throws InterruptedException {
//        String courseName = "MATH 166";
//        String numUsers = "3";
//
//        // Enter data and click create group (empty group name)
//        enterGroupDetails(courseName, "", numUsers);
//        onView(ViewMatchers.withId(R.id.createGroup)).perform(click());
//
//        // Verify error message (replace with actual message)
//        onView(ViewMatchers.withId(R.id.groupName)).check(matches(ViewMatchers.withText("Group Name cannot be empty")));
//    }
    private void enterLoginDetails(String username, String password) throws InterruptedException {
        onView(ViewMatchers.withId(R.id.login_username)).perform(typeText(username));
        Thread.sleep(100);
        onView(ViewMatchers.withId(R.id.login_password)).perform(typeText(password));
        Thread.sleep(100);
        onView(ViewMatchers.withId(R.id.login_password)).perform(ViewActions.closeSoftKeyboard());

    }

//    private void verifyNoErrors(String cName, String groupText, String numUsers) throws InterruptedException {
//        onView(ViewMatchers.withId(R.id.courseName)).check(matches(ViewMatchers.withText(cName)));
//        Thread.sleep(100);
//        onView(ViewMatchers.withId(R.id.groupName)).check(matches(ViewMatchers.withText(groupText)));
//        onView(ViewMatchers.withId(R.id.numberofmembers)).check(matches(ViewMatchers.withText(numUsers)));
//    }
}
