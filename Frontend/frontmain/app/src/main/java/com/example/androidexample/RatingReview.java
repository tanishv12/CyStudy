package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RatingReview extends AppCompatActivity
{
    private RatingBar ratingBar;
    private Button submitrate;

    private String group;

    private String user;

    private double i;
    private EditText reviewTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_review);
        ratingBar = findViewById(R.id.ratingBar);
        submitrate = findViewById(R.id.submitRate);
        reviewTxt = findViewById(R.id.ReviewText);
        user = UsernameSingleton.getInstance().getUserName();
        group = GroupNameSingleton.getInstance().getGroupName();

        submitrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String s = String.valueOf(ratingBar.getRating());
                i = Double.parseDouble(s);
                postRating();
                Toast.makeText(getApplicationContext(), i + " Stars", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void postRating()
    {
        int grp_id = 1;
        int user_id = 1;
        String url = "http://coms-309-016.class.las.iastate.edu:8080/rating/post/";
        // Convert input to JSONObject
        JSONObject postBody = null;

        try
        {
            Log.e("Try Entered","oisafuhgiureshg");
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            postBody = new JSONObject();
            url += group + "/" + user + "/" + i;
            postBody.put("rating", i);
//            url += "/" + i;


////                    + "/" + loginPassword.getText().toString();
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