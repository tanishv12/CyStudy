package com.example.androidexample;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class ProfileActivity extends AppCompatActivity {
    TextView name, email, username, password;

    /**
     * Creates home page UI
     * @param savedInstanceState Stores information needed to reload UI on system crashes
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);
        super.onCreate(savedInstanceState);

        name=findViewById(R.id.signup_name_textView);
        email=findViewById(R.id.signup_email_textView);
        username=findViewById(R.id.signup_username_textView);
        password=findViewById(R.id.signup_password_textView);

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });

        //Navigation Bar Code Start
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavbar);
        bottomNavigationView.setSelectedItemId(R.id.Profile);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.Home)
            {
                return true;
            }
            if(item.getItemId() == R.id.Classes)
            {
                startActivity(new Intent(getApplicationContext(), ClassFragment.class));
                return true;
            }
            if(item.getItemId() == R.id.StudyGroups)
            {
                startActivity(new Intent(getApplicationContext(), StudyGroupFragment.class));
                return true;
            }
            if(item.getItemId() == R.id.Profile)
            {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                return true;
            }
            return false;
        });
        //Navigation Bar Code End

    }
    // Method to show the custom dialog
    private void showCustomDialog() {
        // Inflate the custom dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.update_info_activity, null);

        // Create a dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        // Create the dialog
        AlertDialog dialog = builder.create();

        // Show the dialog
        dialog.show();
    }
}