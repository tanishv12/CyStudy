package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.java_websocket.handshake.ServerHandshake;

public class NewCourseRegActivity extends AppCompatActivity{

    private LinearLayout cardsContainer;
    private Button doneBtn;
    private AutoCompleteTextView courseDeptAutoCompleteTextView;
    private AutoCompleteTextView courseCodeAutoCompleteTextView;
    private String courseDept, courseCode;

    public int convertDpToPixels(float dp, Context context)
    {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course_reg);

        courseDeptAutoCompleteTextView = findViewById(R.id.courseDept);
        courseCodeAutoCompleteTextView = findViewById(R.id.courseCode);
        doneBtn = findViewById(R.id.doneButton);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This takes the text from the autocomplete fields. Needs to
                //be changed to take text from backend dummy courses and run the createCard
                //as well as .addView for each pair.
                //
                //This code will work for narrowing down search results.
                cardsContainer = findViewById(R.id.linearLayoutCourses);

                courseDept = courseDeptAutoCompleteTextView.getText().toString();
                courseCode = courseCodeAutoCompleteTextView.getText().toString();

                CardView cardView = createCard(courseDept, courseCode);

                //Uncomment when above method is implemented
                cardsContainer.addView(cardView);
            }
        });

    }

    private CardView createCard(String courseDept, String courseCode)
    {
        CardView cardView = new CardView(this);
        String courseNameCombined = courseDept + " " + courseCode;

        Log.e("group", "group name: " + courseNameCombined);
        cardView.setCardElevation(convertDpToPixels(4, this));
        cardView.setRadius(convertDpToPixels(4, this));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(
                convertDpToPixels(16, this),
                convertDpToPixels(8, this),
                convertDpToPixels(16, this),
                convertDpToPixels(8, this)
        );
        cardView.setLayoutParams(layoutParams);

        cardView.setCardElevation(convertDpToPixels(4, this));
        cardView.setRadius(convertDpToPixels(4, this));

        LinearLayout cardContentLayout = new LinearLayout(this);
        cardContentLayout.setOrientation(LinearLayout.HORIZONTAL);
        cardContentLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        TextView courseNameView = new TextView(this);
        courseNameView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
        ));
        courseNameView.setText(courseNameCombined);
        courseNameView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        cardContentLayout.addView(courseNameView);
        cardView.addView(cardContentLayout);

        return cardView;
    }

}