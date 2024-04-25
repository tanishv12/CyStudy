package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroupInformation extends AppCompatActivity {

    private AppCompatImageView backToGrpChat;

    private String groupNameSet;

    private String user;
    ArrayList<String> users = new ArrayList<String>();
    private TextView GroupHeadingName;

    private TextView members;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_information);
        GroupHeadingName = findViewById(R.id.GroupHeadInfo);
        backToGrpChat = findViewById(R.id.imageBackToGroup);
        members = findViewById(R.id.allGrpMembers);


        groupNameSet = GroupSingleton.getInstance().getGroupName();

        GroupHeadingName.setText(groupNameSet);

        getRequest();

        backToGrpChat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(GroupInformation.this, MessageActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getRequest()
    {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/groups/all/users/" + groupNameSet;


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                                JSONArray jsonArray = new JSONArray(response);
                                for(int i = 0; i < jsonArray.length(); i++)
                                {
                                    JSONObject jsonObj = jsonArray.getJSONObject(i);
//                                cardsContainer = findViewById(R.id.linearLayoutGroups);

                                    user = jsonObj.getString("name");
                                    users.add(user);
                                    Log.e("user","users " + users);

//                                GroupSingleton.getInstance().setGroupName(name);
//                                String groupRate = name + "\n" + "Group Rating: "+ rating;
////
//                                CardView cardView = createCard(groupName, rating);
////                                GroupSingleton.getInstance().setGroupName(groupName);
//                                cardsContainer.addView(cardView);

                                    members.setText(users.toString());
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