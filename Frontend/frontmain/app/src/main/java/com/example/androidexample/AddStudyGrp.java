package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class AddStudyGrp extends AppCompatActivity {

    private EditText numUsers;

    private int count = 2;
    private ImageButton increaseUser;
    private ImageButton decreaseUser;

    private Button createGrp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_study_grp);


        increaseUser = findViewById(R.id.increaseCount);
        decreaseUser = findViewById(R.id.decreaseCount);
        numUsers = findViewById(R.id.numberofmembers);
        createGrp = findViewById(R.id.createGroup);


        createGrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        increaseUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                count += 1;
                if(count <= 2)
                {
                    count = 2;
                    numUsers.setText("" + count);
                }
                numUsers.setText("" + count);
            }
        });

        decreaseUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count -= 1;
                if(count <= 2)
                {
                    count = 2;
                    numUsers.setText("" + count);
                }
                numUsers.setText("" + count);
            }
        });


    }
}