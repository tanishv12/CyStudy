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


public class MainActivity extends AppCompatActivity
{
    private Button b1;
//    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        setContentView(R.layout.activity_main);
//        replaceFragment(new HomeFragment());

        Button toHome = (Button) findViewById(R.id.button12);
        Button toStudyGroups = (Button) findViewById(R.id.button13);
        Button toClasses = (Button) findViewById(R.id.button11);

        toHome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent1 = new Intent(MainActivity.this, HomeFragment.class);
                startActivity(intent1);
            }
        });


        toStudyGroups.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent2 = new Intent(MainActivity.this, StudyGroupFragment.class);
                startActivity(intent2);
            }
        });

        toClasses.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent3 = new Intent(MainActivity.this, ClassFragment.class);
                startActivity(intent3);
            }
        });




//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setSelectedItemId(R.id.Home);
//
//        bottomNavigationView.setOnItemSelectedListener(item -> {
//            int itemId = item.getItemId();
//            if (itemId == R.id.Classes)
//            {
//                replaceFragment(new ClassFragment());
//            }
//            else if (itemId == R.id.Home)
//            {
//                replaceFragment(new HomeFragment());
//            }
//            else if (itemId == R.id.StudyGroups)
//            {
//                replaceFragment(new StudyGroupFragment());
//            }
//            return true;
//        });



//        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

    }
//    private void replaceFragment(Fragment fragment)
//    {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frame_layout, fragment);
//        fragmentTransaction.commit();
//    }



}