package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class JoinStudyGroupCourses extends AppCompatActivity
{

    private LinearLayout courseholder;

    private String user;

    private String groups;


//    private TextView members;
    private TextView courseHeader;
    private String courseHead;

    private AppCompatImageView backButton, qrButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_study_group_courses);
        courseHead = CourseNameSingleton.getInstance().getCourseName();
        user = UsernameSingleton.getInstance().getUserName();

        backButton = findViewById(R.id.imageBack);
        qrButton=findViewById(R.id.imageQRScan);
        courseHeader = findViewById(R.id.CourseNameInfo);
        courseHeader.setText(courseHead);
        Log.e("courseHeadddd","courseHead " + courseHead);

        getRequest();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(JoinStudyGroupCourses.this, ClassFragment.class);
                startActivity(intent);
            }
        });
        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JoinStudyGroupCourses.this, QRScannerActivity.class);
                startActivity(intent);
            }
        });

    }
    private void getRequest()
    {
        Log.e("courseHeadddd","courseHead " + courseHead);
        String url = "http://coms-309-016.class.las.iastate.edu:8080/course/groups/" + courseHead + "/";
        Log.e("the url","url " + url);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
//                                users = "";
                            for(int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject jsonObj = jsonArray.getJSONObject(i);
                                groups = jsonObj.getString("groupName");
                                Log.e("WHAT","WHAT IS THE GROUP" + groups);

                                Log.e("groups","groups of course " + groups);
//                                    members.setText(users);
//                                GroupSingleton.getInstance().setGroupName(name);

                                courseholder = findViewById(R.id.coursesHolder);
                                CardView cardView = createCard(groups);
////                                GroupSingleton.getInstance().setGroupName(groupName);
                                courseholder.addView(cardView);
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

    private CardView createCard(String group) {
        // Create a new CardView and set up its layout parameters
//        String groupRate = users;
//        Log.e("users", "user name: " + users);
        Log.e("name of group","groups " + group);

        CardView cardView = new CardView(this);
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

        // Set the CardView's appearance
        cardView.setCardElevation(convertDpToPixels(4, this));
        cardView.setRadius(convertDpToPixels(4, this));


        LinearLayout cardContentLayout = new LinearLayout(this);
        cardContentLayout.setOrientation(LinearLayout.HORIZONTAL);
        cardContentLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        // Create a TextView for the group name
        TextView groupNameView = new TextView(this);
        groupNameView.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
        ));
        groupNameView.setText(group);
        groupNameView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        groupNameView.setGravity(Gravity.CENTER_VERTICAL);

        Button entergroup = new Button(this);
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        buttonLayoutParams.gravity = Gravity.END;
        entergroup.setLayoutParams(buttonLayoutParams);
        entergroup.setText("Join");
        entergroup.setOnClickListener(v -> {
            GroupSingleton.getInstance().setGroupName(group);
            putRequest();
            Intent intent = new Intent(JoinStudyGroupCourses.this, StudyGroupFragment.class);
            startActivity(intent);
        });

        cardContentLayout.addView(groupNameView);
        cardContentLayout.addView(entergroup);
        cardView.addView(cardContentLayout);
        return cardView;
    }

    private int convertDpToPixels(float dp, Context context)
    {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    private void putRequest()
    {
        String groups = GroupSingleton.getInstance().getGroupName();
        String url = "http://coms-309-016.class.las.iastate.edu:8080/groups/addUser/" + groups + "/" + user;
        Log.e("url", url);

        StringRequest request = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

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