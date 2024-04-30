package com.example.androidexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import org.java_websocket.handshake.ServerHandshake;

import com.google.android.material.bottomnavigation.BottomNavigationView;

//import com.example.androidexample.databinding.ActivityMainBinding;

/**
 * Home page for study group application.
 *
 * Redirects user to classes page, study group page, or back to homepage.
 */
public class MainActivity extends AppCompatActivity
{
    private Button b1;
//    ActivityMainBinding binding;

    /**
     * Creates home page UI
     * @param savedInstanceState Stores information needed to reload UI on system crashes
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavbar);
        bottomNavigationView.setSelectedItemId(R.id.Home);

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
            if(item.getItemId() == R.id.Profile)
            {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                return true;
            }
            return false;
        });
        super.onCreate(savedInstanceState);



    }
}