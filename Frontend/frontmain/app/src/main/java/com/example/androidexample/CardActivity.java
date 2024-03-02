package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class CardActivity extends AppCompatActivity implements View.OnClickListener {
    public CardView card1, card2;

    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_class);
        card1 = findViewById(R.id.math166);
        if (card1 != null)
        {
            card1.setOnClickListener(this);
        }

        card2 = findViewById(R.id.coms309);
        if (card2 != null)
        {
            card2.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view)
    {
        Intent i;
        int id = view.getId();
        if (id == R.id.math166)
        {
            i = new Intent(this, Math166.class);
            startActivity(i);
        }
        else if (id == R.id.coms309)
        {
            i = new Intent(this, ComS309.class);
            startActivity(i);
        }
    }
}
