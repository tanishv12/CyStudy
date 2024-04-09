package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminPanelActivity extends AppCompatActivity {
    TextView lowestRatedGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        Button chatLogs, groupManagement, userManagement, returnHome;

        chatLogs = findViewById(R.id.chat_logs);
        groupManagement = findViewById(R.id.group_management);
        userManagement = findViewById(R.id.user_management);
        returnHome = findViewById(R.id.return_home);
         lowestRatedGroups = findViewById(R.id.lowest_rated_groups);
        getRequest();

        chatLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPanelActivity.this, ChatLogsActivity.class);
                startActivity(intent);
            }
        });

        groupManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPanelActivity.this, GroupManagementActivity.class);
                startActivity(intent);
            }
        });

        returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPanelActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        userManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPanelActivity.this, UserManagementActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Retrieves lowest study groups
     */
    private void getRequest()
    {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/rating/lowestRatedGroups";
        //Change url to lowest rated grous

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Convert the response string to a JSONArray
                            JSONArray jsonArray = new JSONArray(response);

                            // Initialize a StringBuilder to store all group names and ratings
                            StringBuilder groupsInfo = new StringBuilder();

                            // Iterate through the JSONArray to extract group names and ratings
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject groupObject = jsonArray.getJSONObject(i);
                                String groupName = groupObject.getString("groupName");
                                String ratingList = groupObject.getString("ratingList");

                                // Append group name and rating to the StringBuilder
                                groupsInfo.append("Group Name: ").append(groupName).append("\n");
                                groupsInfo.append("Rating: ").append(ratingList).append("\n\n");
                            }

                            // Display the group names and ratings
                            lowestRatedGroups.setText(groupsInfo.toString());

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        lowestRatedGroups.setText(error.toString());
                    }
                }) {

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