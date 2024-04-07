package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;
import java.util.Map;

public class GroupManagementActivity extends AppCompatActivity {

    String deleteUrl;
    boolean isFirstClick;
    private EditText group_name;
    private TextView chat;
    private Button create_group, read_group, update_group, delete_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_management);
        isFirstClick = true;
        deleteUrl = "http://coms-309-016.class.las.iastate.edu:8080/groups/delete/";
        chat = findViewById(R.id.all_chat);
        group_name = findViewById(R.id.groupName);
        create_group = findViewById(R.id.createGroup);
        read_group = findViewById(R.id.readGroup);
        update_group = findViewById(R.id.updateGroup);
        delete_group = findViewById(R.id.deleteGroup);

        create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chat.setText("make this method map to the create group screen");
                //Map to create group screen
            }
        });

        delete_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = group_name.getText().toString();
                if(isFirstClick)
                {
                    Toast.makeText(GroupManagementActivity.this, "Click again to delete group, enter user name and click to remove user", Toast.LENGTH_SHORT).show();
                    deleteUrl += temp;
                    getUsersRequest();
                    isFirstClick = false;
                    group_name.setText("");

                }
                else {
                    if(!temp.isEmpty())
                    {
                        deleteUrl += "/" + temp;
                        Toast.makeText(GroupManagementActivity.this, deleteUrl, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(GroupManagementActivity.this, deleteUrl, Toast.LENGTH_SHORT).show();
                    }
                    isFirstClick = true;
                    deleteRequest();
                    deleteUrl = "http://coms-309-016.class.las.iastate.edu:8080/groups/delete/";
                }
            }
        });

        read_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRequest();
            }
        });

        update_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putRequest();
            }
        });
        //Most of the methods are in StudyGroupFragment. Copy over from there.
    }

    /**
     * Deletes study group that the user is currently in
     *
     * IMPORTANT: Must change url to delete the group using given name
     */
    private void deleteRequest()
    {
        String url = deleteUrl;
        // Convert input to JSONObject
        JSONObject deleteBody = null;

        try
        {
            Log.e("Try Entered","oisafuhgiureshg");
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            deleteBody = new JSONObject();
//            deleteBody.put("group_id", updateGrp.getText().toString());
            Log.e("what is putbody",deleteBody.toString());
            Log.e("Try BLAH","oisafuhgiureshg");
            url += "/" + "5";
            Log.e("What is url",url.toString());
////                    + "/" + loginPassword.getText().toString();
        }
        catch (Exception e)
        {
            Log.e("Catch Entered","wkerufhieuwhf");
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                deleteBody,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        chat.setText(response.toString());
                        Log.e("Response Entered",response.toString());

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e("Error Response", error.toString());
                        Toast.makeText(GroupManagementActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

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

    /**
     * Retrieves all study groups that the user is in given user name
     */
    private void getRequest()
    {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/groups/all/";
        url += group_name.getText().toString();

        chat.setText(url);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Display the first 500 characters of the response string.
                        // String response can be converted to JSONObject via
                        // JSONObject object = new JSONObject(response);
                        chat.setText(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GroupManagementActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
     * Retrieves all users in study group
     */
    private void getUsersRequest()
    {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/groups/all/users/";
        url += group_name.getText().toString();
        //CHANGE TO USERS IN GROUP

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Display the first 500 characters of the response string.
                        // String response can be converted to JSONObject via
                        // JSONObject object = new JSONObject(response);
                        chat.setText(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GroupManagementActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
     * Updates study group name that the user is in
     */
    private void putRequest()
    {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/groups/update";
        // Convert input to JSONObject
        JSONObject putBody = null;

        try
        {
            Log.e("Try Entered","oisafuhgiureshg");
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            putBody = new JSONObject();
            putBody.put("groupName", group_name.getText().toString());
            Log.e("what is putbody",putBody.toString());
            Log.e("Try BLAH","oisafuhgiureshg");
            url += "/" + "8";
            Log.e("What is url",url.toString());
////                    + "/" + loginPassword.getText().toString();
        }
        catch (Exception e)
        {
            Log.e("Catch Entered","wkerufhieuwhf");
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                putBody,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.e("Response Entered",response.toString());
                        chat.setText(response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e("Error Response", error.toString());
                        Toast.makeText(GroupManagementActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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