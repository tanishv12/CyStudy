package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class RatingReview extends AppCompatActivity
{
    private RatingBar ratingBar;
    private Button submitrate;

    private EditText reviewTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_review);
        ratingBar = findViewById(R.id.ratingBar);
        submitrate = findViewById(R.id.submitRate);
        reviewTxt = findViewById(R.id.ReviewText);

        submitrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String s = String.valueOf(ratingBar.getRating());
                Toast.makeText(getApplicationContext(), s + " Stars", Toast.LENGTH_SHORT).show();
            }
        });


    }
}