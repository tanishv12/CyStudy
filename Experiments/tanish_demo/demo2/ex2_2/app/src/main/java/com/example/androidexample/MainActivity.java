package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button strBtn, jsonObjBtn, jsonArrBtn, imgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jsonObjBtn = findViewById(R.id.btnJsonObjRequest);


        /* button click listeners */
        jsonObjBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        startActivity(new Intent(MainActivity.this, JsonObjReqActivity.class));

    }
}