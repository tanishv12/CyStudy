package com.example.androidexample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ClassFragment extends AppCompatActivity {
    private String user;
    private String courseNAME;
    private LinearLayout cardsContainer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_class);
        user = UsernameSingleton.getInstance().getUserName();
        getRequest();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavbar);
        bottomNavigationView.setSelectedItemId(R.id.Classes);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.Classes) {
                return true;
            } if (item.getItemId() == R.id.Home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            } if (item.getItemId() == R.id.StudyGroups) {
                startActivity(new Intent(getApplicationContext(), StudyGroupFragment.class));
                return true;
            } if (item.getItemId() == R.id.Profile) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                return true;
            }
            return false;
        });
    }
    private CardView createCard(String courseName) {
//        CourseNameSingleton.getInstance().setCourseName(courseName);
        // Create a new CardView and set up its layout parameters
        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                convertDpToPixels(60, this)
        );
        layoutParams.setMargins(
                convertDpToPixels(16, this),
                convertDpToPixels(8, this),
                convertDpToPixels(16, this),
                convertDpToPixels(8, this)
        );
        cardView.setLayoutParams(layoutParams);
        cardView.setBackgroundResource(R.drawable.card_background);

        // Set the CardView's appearance
        cardView.setCardElevation(convertDpToPixels(4, this));
        cardView.setRadius(convertDpToPixels(4, this));


        LinearLayout cardContentLayout = new LinearLayout(this);
        cardContentLayout.setOrientation(LinearLayout.HORIZONTAL);
        cardContentLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        cardContentLayout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);


        // Create a TextView for the group name
        TextView groupNameView = new TextView(this);
        groupNameView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
        ));
//        groupNameView.setTextColor(Color.parseColor("#FFFFFF"));
        groupNameView.setGravity(Gravity.CENTER);
        groupNameView.setText(courseName);
        Log.e("courseNameeee","coursenamee "+courseName);
        groupNameView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        cardContentLayout.addView(groupNameView);
        cardView.addView(cardContentLayout);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ClassFragment.this, JoinStudyGroupCourses.class);
                CourseNameSingleton.getInstance().setCourseName(courseName);
                startActivity(intent);
            }
        });
        return cardView;
    }

    public int convertDpToPixels(float dp, Context context)
    {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    /**
     * Retrieves study group that the user is in
     */
    private void getRequest()
    {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/groups/all";
        url = "http://coms-309-016.class.las.iastate.edu:8080/courses/user/" + user;


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i < jsonArray.length(); i++) {
                                // Get each JSONObject within the array
                                JSONObject jsonObj = jsonArray.getJSONObject(i);
                                cardsContainer = findViewById(R.id.linearLayoutGroups);
                                // Access the value associated with the key "name"
                                String courseDep = jsonObj.getString("courseDepartment");
                                String coursecode = jsonObj.getString("courseCode");
                                String course = courseDep + " " + coursecode;
                                CardView cardView = createCard(course);
                                Log.e("courseee","course"+course);
                                cardsContainer.addView(cardView);
                            }
                        }
                        catch (JSONException err)
                        {
                            Log.d("Error", err.toString());
                        }
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