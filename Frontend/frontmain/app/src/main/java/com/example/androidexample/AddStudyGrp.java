package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class AddStudyGrp extends AppCompatActivity {

    private EditText numUsers;

    private int count = 2;
    private ImageButton increaseUser;
    private ImageButton decreaseUser;

    private String gName;

    private String user;
    private String postResponse;

    private String courseName;

    private AutoCompleteTextView cName;
    private Button createGrp;

    private EditText groupText;
    private static final String[] COURSES = new String[]
            {
                    "COMS 309", "MATH 165", "MATH 166", "COMS 311", "ECON 101", "ECON 321", "MUSIC 102", "PHYS 231", "CHEM 177", "BIOL 211"
            };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_study_grp);

        increaseUser = findViewById(R.id.increaseCount);
        decreaseUser = findViewById(R.id.decreaseCount);
        numUsers = findViewById(R.id.numberofmembers);
        createGrp = findViewById(R.id.createGroup);
        cName = findViewById(R.id.courseName);

        groupText = findViewById(R.id.groupName);

        user = UsernameSingleton.getInstance().getUserName();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COURSES);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                cName;
        textView.setAdapter(adapter);

        createGrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                courseName = cName.getText().toString();
                gName = groupText.getText().toString();
                GroupMasterSingleton.getInstance().setCreateGrpMaster(user);
                String userCount = numUsers.getText().toString();
                if (courseName.isEmpty())
                {
                    cName.setError("Course Name cannot be empty");
                }
                else if (gName.isEmpty())
                {
                    // If groupText field is empty, display a Error message indicating the error
                    groupText.setError("Group Name cannot be left empty");
                }
                else if (userCount.isEmpty())
                {
                    numUsers.setError("Number of Users cannot be left empty");
                }
                else
                {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("groupName", gName);//Essentially should be what the user gives

                    Log.e("groupName", "group: " + gName);
                    postRequest();
                    try {
                        Toast.makeText(AddStudyGrp.this, postResponse, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e("ToastError", "Error showing toast", e);
                    }
//                    finish();
                }
            }
        });

        increaseUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                count += 1;
                if(count <= 2)
                {
                    count = 2;
                    numUsers.setText("" + count);
                }
                else if(count >= 10)
                {
                    count = 10;
                    numUsers.setText("" + count);
                }
                numUsers.setText("" + count);
            }
        });

        decreaseUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count -= 1;
                if(count <= 2)
                {
                    count = 2;
                    numUsers.setText("" + count);
                }
                numUsers.setText("" + count);
            }
        });
    }
    private void postRequest()
    {
        String base_url = "http://coms-309-016.class.las.iastate.edu:8080/groups/post/" + gName + "/" + user + "/" + courseName + "/" + count;
        String url = base_url.replace(" ", "%20");
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        if (response != null)
                        {
                            Toast.makeText(AddStudyGrp.this, response, Toast.LENGTH_SHORT).show();
                            if(response.equals("Group created successfully!"))
                            {
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("groupName", gName);//Essentially should be what the user gives
                                setResult(Activity.RESULT_OK, returnIntent);
                                finish();
                            }
                        }
                        else {
                            Toast.makeText(AddStudyGrp.this, "Response is empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e("Error Response", error.toString());

                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
                headers.put("Content-Type", "application/json");
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