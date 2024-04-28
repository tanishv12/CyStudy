package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class JoinStudyGroupCourses extends AppCompatActivity
{
    private TextView courseHeader;
    private String courseHead;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_study_group_courses);
        courseHead = CourseNameSingleton.getInstance().getCourseName();

        courseHeader = findViewById(R.id.CourseNameInfo);
        courseHeader.setText(courseHead);
    }

}