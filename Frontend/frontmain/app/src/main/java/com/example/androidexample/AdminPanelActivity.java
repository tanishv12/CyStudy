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
                        if (response != null && response.length() >= 2) {
                            response = response.substring(1, response.length() - 1);
                        }

                        try {
                            JSONObject object = new JSONObject(response);
                            response = "Lowest rated group: " + object.getString("groupName");
                            response += "\nRating: " + object.getString("ratingList");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        // Display the first 500 characters of the response string.
                        // String response can be converted to JSONObject via
                        // JSONObject object = new JSONObject(response);
                        lowestRatedGroups.setText(response);

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