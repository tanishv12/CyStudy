package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
public class ClassFragment extends AppCompatActivity {
    private Button enterCourse;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_class);

        enterCourse = findViewById(R.id.enterCourse);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavbar);
        bottomNavigationView.setSelectedItemId(R.id.Classes);


        enterCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ClassFragment.this, JoinStudyGroupCourses.class);
                startActivity(intent);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.Classes) {
                return true;
            } if (item.getItemId() == R.id.Home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            } if (item.getItemId() == R.id.StudyGroups) {
                startActivity(new Intent(getApplicationContext(), StudyGroupFragment.class));
                return true;
            }
            return false;
        });
    }
}