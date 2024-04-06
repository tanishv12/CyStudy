package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GroupManagementActivity extends AppCompatActivity {

    EditText groupName;
    Button createGroup, readGroup, updateGroup, deleteGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_management);

        groupName = findViewById(R.id.group_name);
        createGroup = findViewById(R.id.create_group);
        readGroup = findViewById(R.id.read_group);
        updateGroup = findViewById(R.id.update_group);
        deleteGroup = findViewById(R.id.delete_group);

        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        deleteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRequest();
            }
        });
        //Most of the methods are in StudyGroupFragment. Copy over from there.
    }

    /**
     * Deletes study group that the user is currently in
     *
     * IMPORTANT: Must change to deletes the group using given name
     */
    private void deleteRequest()
    {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/groups/delete";
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

                        Log.e("Response Entered",response.toString());

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