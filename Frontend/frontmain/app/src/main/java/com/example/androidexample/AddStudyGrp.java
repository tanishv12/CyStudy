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

    private String courseName;

    private AutoCompleteTextView cName;
    private Button createGrp;

    private EditText groupText;
    private static final String[] COURSES = new String[]
            {
                    "COM S 227", "MATH 165", "MATH 166", "PHYS 200", "CHEM 100"
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
                    setResult(Activity.RESULT_OK, returnIntent);
                    Log.e("groupName", "group: " + gName);
                    postRequest();
                    finish();
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
        String url = "http://coms-309-016.class.las.iastate.edu:8080/groups/post/" + gName + "/" + user + "/" + courseName + "/" + count;
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
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