package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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
    private String courseDept, courseCode, user;
    private Integer courseId;

    public int convertDpToPixels(float dp, Context context)
    {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Call the method to filter card views here
        if (cardsContainer != null) {
            filterCardViews();
        }
    }

    private void filterCardViews() {
        // Get the text entered in the department and code AutoCompleteTextViews
        String deptFilter = courseDeptAutoCompleteTextView.getText().toString().trim().toLowerCase();
        String codeFilter = courseCodeAutoCompleteTextView.getText().toString().trim().toLowerCase();

        // Iterate over each card in the cardsContainer
        for (int i = 0; i < cardsContainer.getChildCount(); i++) {
            View view = cardsContainer.getChildAt(i);
            if (view instanceof CardView) {
                CardView cardView = (CardView) view;
                // Get the course department and code from the cardView tags
                String cardDept = ((String) cardView.getTag(R.id.course_dept_tag)).toLowerCase();
                String cardCode = ((String) cardView.getTag(R.id.course_code_tag)).toLowerCase();

                // Check if the card should be visible based on the filters
                boolean shouldShow = cardDept.contains(deptFilter) && cardCode.contains(codeFilter);

                // Set the visibility of the cardView accordingly
                cardView.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course_reg);

        user = UsernameSingleton.getInstance().getUserName();

        courseDeptAutoCompleteTextView = findViewById(R.id.courseDept);
        courseCodeAutoCompleteTextView = findViewById(R.id.courseCode);
        doneBtn = findViewById(R.id.doneButton);

        getRequest();

        courseDeptAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterCardViews();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // Add TextChangedListener for code AutoCompleteTextView
        courseCodeAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterCardViews();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iterate over each card in the cardsContainer
                for (int i = 0; i < cardsContainer.getChildCount(); i++) {
                    view = cardsContainer.getChildAt(i);
                    if (view instanceof CardView) {
                        CardView cardView = (CardView) view;
                        // Check if the cardView has been clicked (has a blue background color)
                        if (cardView.getTag(R.id.if_selected_tag) != null && (boolean) cardView.getTag(R.id.if_selected_tag)) {
                            // Get the courseId from the tag
                            Integer courseId = (Integer) cardView.getTag(R.id.course_id_tag);
                            // Call postRequest with the courseId
                            Log.e("name of the user", user != null ? user : "User is null");
                            postRequest(user, courseId);
                        }
                    }
                }
            }
        });

    }



    private CardView createCard(String courseDept, String courseCode, int courseId)
    {
        CardView cardView = new CardView(this);
        String courseNameCombined = courseDept + " " + courseCode;

        Log.e("group", "group name: " + courseNameCombined);
        cardView.setCardElevation(convertDpToPixels(4, this));
        cardView.setRadius(convertDpToPixels(8, this));

        cardView.setTag(R.id.course_id_tag, courseId);
        cardView.setTag(R.id.if_selected_tag, false);
        cardView.setTag(R.id.course_dept_tag, courseDept);
        cardView.setTag(R.id.course_code_tag, courseCode);

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
        // Get the drawable resource
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.rounded_background);

        // Set the drawable as the background of the CardView
        cardView.setBackground(drawable);

        // Set OnClickListener
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current selected state of the CardView
                boolean isSelected = cardView.isSelected();

                // Toggle the selected state of the CardView
                cardView.setSelected(!isSelected);

                // Update background color based on selected state
//                int backgroundColor = isSelected ? getResources().getColor(android.R.color.white)
//                        : getResources().getColor(android.R.color.holo_blue_light);
//                cardView.setBackgroundColor(backgroundColor);

                // Set clicked value to the opposite of the previous selected state
                cardView.setTag(R.id.if_selected_tag, !isSelected);
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
        courseNameView.setPadding(10,10,10,10);

        cardContentLayout.addView(courseNameView);
        cardView.addView(cardContentLayout);

        return cardView;
    }

    //Gets all selected courses and adds them to jsonObject
    private JSONObject getUserCourses() {
        JSONObject userCourses = new JSONObject();
        try {
            // Iterate over each card in the cardsContainer
            for (int i = 0; i < cardsContainer.getChildCount(); i++) {
                View view = cardsContainer.getChildAt(i);
                if (view instanceof CardView) {
                    CardView cardView = (CardView) view;
                    // Check if the cardView has been clicked (has a blue background color)
                    if (cardView.getTag() != null && (boolean) cardView.getTag()) {
                        // Get the course name combined from the TextView in the card content layout
                        LinearLayout cardContentLayout = (LinearLayout) cardView.getChildAt(0);
                        TextView courseNameView = (TextView) cardContentLayout.getChildAt(0);
                        String courseNameCombined = courseNameView.getText().toString();

                        // Add the course name to the userCourses JSONObject
                        userCourses.put("course_" + i, courseNameCombined);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userCourses;
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
                                courseId = jsonObj.getInt("id");
//                                GroupSingleton.getInstance().setGroupName(name);
//                                String groupRate = name + "\n" + "Group Rating: "+ rating;
//
                                CardView cardView = createCard(courseDept, courseCode, courseId);
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

    /**
     * Creates new study group which will contain the user
     */
    private void postRequest(String userName, int tempCourseId)
    {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/users/"+ userName +"/courses/" + tempCourseId;
        Log.e("url", url);
        // Convert input to JSONObject
        JSONObject postBody = null;

        try
        {
            Log.e("Try Entered","oisafuhgiureshg");
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            postBody = new JSONObject();
            Log.e("JSON Created","Json was created");
        }
        catch (Exception e)
        {
            Log.e("Catch Entered","wkerufhieuwhf");
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                postBody,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.e("Response Entered",response.toString());
//                        Toast.makeText(NewCourseRegActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(NewCourseRegActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e("Error Response", error.toString());
//                        Toast.makeText(StudyGroupFragment.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
        ){
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
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

}