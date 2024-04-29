package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

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


    private void optionsDialog(Context c)
    {
        LayoutInflater inflater = LayoutInflater.from(c);
        View dialogView = inflater.inflate(R.layout.group_options_manager, null);

        BottomSheetDialog dialog = new BottomSheetDialog(c);
        dialog.setTitle("Options");
        dialog.setContentView(dialogView);
        dialog.create();

        leaveGroupBtn = dialogView.findViewById(R.id.leaveGroup);
        editGroupBtn = dialogView.findViewById(R.id.editGrpName);
        doneButton = dialogView.findViewById(R.id.doneBtn);

        leaveGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss();
            }
        });

        editGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss();
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss(); // Close the dialog if needed
            }
        });

        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info_manager);
        optionsGrpBtn = findViewById(R.id.options);
        optionsGrpBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                optionsDialog(GroupInfo_ManagerActivity.this);
            }
        });
    }
}