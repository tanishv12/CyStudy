package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class GroupManagementActivity extends AppCompatActivity {

    EditText groupName;
    Button createGroup, readGroup, updateGroup, deleteGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_management);

        groupName = findViewById(R.id.group_name);
        createGroup = findViewById(R.id.create_group);
        readGroup = findViewById(R.id.read_group);
        updateGroup = findViewById(R.id.update_group);
        deleteGroup = findViewById(R.id.delete_group);


    }
}