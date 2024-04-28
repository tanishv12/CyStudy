package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import org.java_websocket.handshake.ServerHandshake;

public class NewCourseRegActivity extends AppCompatActivity implements WebSocketListener {

    private ScrollView cardsContainer;
    private AutoCompleteTextView courseDeptAutoCompleteTextView;
    private AutoCompleteTextView courseCodeAutoCompleteTextView;

    public int convertDpToPixels(float dp, Context context)
    {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course_reg);

        //uncomment when trying with server.
        //WebSocketManager.getInstance().setWebSocketListener(NewCourseRegActivity.this);
    }

    private CardView createCard(String courseDept, String courseCode)
    {
        CardView cardView = new CardView(this);
        cardView.setCardElevation(convertDpToPixels(4, this));
        cardView.setRadius(convertDpToPixels(4, this));


    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onWebSocketMessage(String message) {

    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onWebSocketError(Exception ex) {

    }
}