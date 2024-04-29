package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
        
        getRequest();

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
        cardView.setRadius(convertDpToPixels(8, this));

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

        // Set background color initially
        cardView.setBackgroundColor(getResources().getColor(android.R.color.white)); // or any other color you prefer

        // Set OnClickListener
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current background color
                int currentColor = ((ColorDrawable) cardView.getBackground()).getColor();

                // Toggle background color between white and blue
                if (currentColor == getResources().getColor(android.R.color.white)) {
                    cardView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                    // Set clicked value to true
                    cardView.setTag(true);
                } else {
                    cardView.setBackgroundColor(getResources().getColor(android.R.color.white));
                    // Set clicked value to false
                    cardView.setTag(false);
                }
            }
        });

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

    /**
     * Retrieves study group that the user is in
     */
    private void getRequest()
    {
        String url;
        url = "http://coms-309-016.class.las.iastate.edu:8080/courses/all";


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            Log.e("group", "response: " + response);
                            for(int i = 0; i < jsonArray.length(); i++) {
                                // Get each JSONObject within the array
                                JSONObject jsonObj = jsonArray.getJSONObject(i);
                                cardsContainer = findViewById(R.id.linearLayoutCourses);
                                // Access the value associated with the key "name"
                                courseDept = jsonObj.getString("courseDepartment");
                                courseCode = jsonObj.getString("courseCode");
//                                GroupSingleton.getInstance().setGroupName(name);
//                                String groupRate = name + "\n" + "Group Rating: "+ rating;
//
                                CardView cardView = createCard(courseDept, courseCode);
//                                GroupSingleton.getInstance().setGroupName(groupName);
                                cardsContainer.addView(cardView);


                            }
                        }
                        catch (JSONException err)
                        {
                            Log.d("Error", err.toString());
                        }
                        // Display the first 500 characters of the response string.
                        // String response can be converted to JSONObject via
                        // JSONObject object = new JSONObject(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
                //                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //                params.put("param1", "value1");
                //                params.put("param2", "value2");
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

}