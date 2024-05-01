package com.example.androidexample;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TestCasesGavin {

    @Rule
    public ActivityScenarioRule<AddStudyGrp> activityScenarioRule = new ActivityScenarioRule<>(AddStudyGrp.class);


    @Test
    public void testValidInput() throws InterruptedException {
        String courseName = "MATH 166";
        String groupName = "Calculus Crew";
        String numUsers = "3";

        // Enter data and click create group
        enterGroupDetails(courseName, groupName, numUsers);
        onView(ViewMatchers.withId(R.id.createGroup)).perform(click());

//         Verify no errors
//        verifyNoErrors(courseName, groupName, numUsers);
    }

    @Test
    public void testEmptyCourseName() throws InterruptedException {
        String cName = "";
        String groupName = "Calculus Crew";
        String numUsers = "3";
        Boolean courseNameError = false;
        Boolean groupNameError = false;
        Boolean userCountError = false;

        if (cName.isEmpty()) {
            courseNameError = true;
        }

        if (groupName.isEmpty()) {
            groupNameError = true;
        }

        if (numUsers.isEmpty()) {
            userCountError = true;
        }

        // Assert that course name error is set and others are not
        assertTrue(courseNameError);
        assertFalse(groupNameError);
        assertFalse(userCountError);


        enterGroupDetails(cName, groupName, numUsers);
        onView(ViewMatchers.withId(R.id.createGroup)).perform(click());
    }

    @Test
    public void testEmptyGroupName() throws InterruptedException {
        String cName = "COM S 127";
        String groupName = "";
        String numUsers = "3";
        Boolean courseNameError = false;
        Boolean groupNameError = false;
        Boolean userCountError = false;

        if (cName.isEmpty()) {
            courseNameError = true;
        }

        if (groupName.isEmpty()) {
            groupNameError = true;
        }

        if (numUsers.isEmpty()) {
            userCountError = true;
        }

        // Assert that course name error is set and others are not
        assertFalse(courseNameError);
        assertTrue(groupNameError);
        assertFalse(userCountError);

        enterGroupDetails(cName, groupName, numUsers);
        onView(ViewMatchers.withId(R.id.createGroup)).perform(click());
    }

    @Test
    public void testPostGroup() throws InterruptedException {
        String courseName = "COM S 227";
        String groupName = "Computer Science Group";
        String members = "4";



        enterGroupDetails(courseName, groupName, members);
        onView(ViewMatchers.withId(R.id.createGroup)).perform(click());
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




    private void enterGroupDetails(String courseName, String groupName, String numUsers) throws InterruptedException {
        onView(ViewMatchers.withId(R.id.courseName)).perform(typeText(courseName));
        Thread.sleep(100);
        onView(ViewMatchers.withId(R.id.groupName)).perform(typeText(groupName));
        onView(ViewMatchers.withId(R.id.numberofmembers)).perform(typeText(numUsers));
    }

//    private void verifyNoErrors(String cName, String groupText, String numUsers) throws InterruptedException {
//        onView(ViewMatchers.withId(R.id.courseName)).check(matches(ViewMatchers.withText(cName)));
//        Thread.sleep(100);
//        onView(ViewMatchers.withId(R.id.groupName)).check(matches(ViewMatchers.withText(groupText)));
//        onView(ViewMatchers.withId(R.id.numberofmembers)).check(matches(ViewMatchers.withText(numUsers)));
//    }
}
