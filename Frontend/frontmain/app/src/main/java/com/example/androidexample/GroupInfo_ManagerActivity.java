package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GroupInfo_ManagerActivity extends AppCompatActivity {

    private AppCompatImageView backToGrpChat;

    private AppCompatImageView optionsGrpBtn;

    private LinearLayout calenderInfo;

    private String groupNameSet;

    private Button editGroupBtn;

    private String user;
    private String users;
    private TextView GroupHeadingName;

    private Button leaveGroupBtn;
    private Button doneButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info_manager);
    }
}