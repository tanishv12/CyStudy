package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminPanelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        Button chatLogs, groupManagement, userManagement, addResources, returnHome;

        chatLogs = findViewById(R.id.chat_logs);
        groupManagement = findViewById(R.id.group_management);
        userManagement = findViewById(R.id.user_management);
        addResources = findViewById(R.id.add_resources);
        returnHome = findViewById(R.id.return_home);

        chatLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPanelActivity.this, ChatLogsActivity.class);
                startActivity(intent);
            }
        });

        groupManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPanelActivity.this, GroupManagementActivity.class);
                startActivity(intent);
            }
        });

        returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPanelActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}