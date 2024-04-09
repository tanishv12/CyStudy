package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ChatLogsActivity extends AppCompatActivity {

    Button btnCheckMessages;
    EditText editUser, editGroupName;
    TextView txtMessages;

    String url = "http://coms-309-016.class.las.iastate.edu:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_logs);

        btnCheckMessages = findViewById(R.id.check_messages);
        editUser = findViewById(R.id.chatlogs_username);
        editGroupName = findViewById(R.id.chatlogs_group);
        txtMessages = findViewById(R.id.all_chatlogs);

        btnCheckMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String group = editGroupName.getText().toString();
                String user = editUser.getText().toString();
                if(user.isEmpty())
                {
                    url += "/messages/all/group/" + group;
                }
                else {
                    url += "/messages/all/user/" + user;
                    postRequest();
                }

            }
        });
    }

    private void postRequest() {
        //Change the URL to whatever they say the endpoint is
        // Convert input to JSONObject
        JSONObject postBody = null;

        try{
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            postBody = new JSONObject();
            postBody.put("username", editUser.getText().toString());
            postBody.put("group", editGroupName.getText().toString());
            url += editUser.getText().toString() + "/" + editGroupName.getText().toString();
        } catch (Exception e){
            e.printStackTrace();
        }

        // Create a StringRequest
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        txtMessages.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors here
                        String errorMessage;
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            errorMessage = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        } else {
                            errorMessage = error.toString();
                        }
                        Log.e("Error response", errorMessage);
                        txtMessages.setText(errorMessage);
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