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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserManagementActivity extends AppCompatActivity {

    Button createUser, deleteUser;
    EditText userName;
    TextView chat;
    String deleteUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        createUser = findViewById(R.id.create_user);
        deleteUser = findViewById(R.id.delete_user);
        userName = findViewById(R.id.edit_user);
        chat = findViewById(R.id.user_chat);
        deleteUrl = "http://coms-309-016.class.las.iastate.edu:8080/users/delete/";

        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = userName.getText().toString();
                deleteUrl += temp;
                deleteRequest();
                deleteUrl = "http://coms-309-016.class.las.iastate.edu:8080/users/delete/";
                userName.setText("");
            }
        });

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserManagementActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    private void deleteRequest()
    {
        String url = deleteUrl;
        // Convert input to JSONObject
        JSONObject deleteBody = null;

//        try
//        {
//            Log.e("Try Entered","oisafuhgiureshg");
//            // etRequest should contain a JSON object string as your POST body
//            // similar to what you would have in POSTMAN-body field
//            // and the fields should match with the object structure of @RequestBody on sb
//            deleteBody = new JSONObject();
////            deleteBody.put("group_id", updateGrp.getText().toString());
//            Log.e("what is putbody",deleteBody.toString());
//            Log.e("Try BLAH","oisafuhgiureshg");
//            Log.e("What is url",url.toString());
//////                    + "/" + loginPassword.getText().toString();
//        }
//        catch (Exception e)
//        {
//            Log.e("Catch Entered","wkerufhieuwhf");
//            e.printStackTrace();
//        }

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
                        Toast.makeText(UserManagementActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

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