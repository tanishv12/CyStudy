package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * SignupActivity Class is used for creating users on the application and manage
 * getting the information from the users.
 *
 * The Class also makes use of different CRUD operations to move information
 * to the database and communicate with the remote server.
 */
public class SignupActivity extends AppCompatActivity {

    EditText signupName, signupEmail, signupUsername, signupPassword;
    TextView loginRedirectText;
    Button signupButton;

    private EditText etRequest;



    JSONObject postBody;



    private String url = "http://coms-309-016.class.las.iastate.edu:8080/users/post";

    private String method;

    String errorUrl;

    String[] methods = new String[]{"GET", "POST"};


    /**
     * This class calls and correctly maps the different variables and initializes the ids from the application
     * layout files.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        /**
         * This is the on click function for the sign up button creates the user in
         * the application after they enters their information and click the button.
         */
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = signupName.getText().toString();
                String email = signupEmail.getText().toString();
                String username = signupUsername.getText().toString();
                String password = signupPassword.getText().toString();

                postRequest();

                //Move all to Post Request on Response
                UsernameSingleton.getInstance().setUserName(name);



                Toast.makeText(SignupActivity.this, "Sign Up Complete", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                UsernameSingleton.getInstance().setUserName(username);

            }
        });
        /**
         * This function is involved in redirecting users who already have accounts so they
         * don't have to sign up again on the registration page on clicking the "Not yet registered? Sign Up".
         */
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, AdminPanelActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Sets all signup information errors if any are empty.
     */
    public Boolean validateCredentials()
    {
        Boolean returnValue = true;
        //Sets signupName error if empty
        String val = signupName.getText().toString();
        if(val.isEmpty())
        {
            signupName.setError("Name cannot be empty");
            returnValue = false;
        }
        else
        {
            signupName.setError(null);
        }

        //Sets signupUsername error if empty
        val = signupUsername.getText().toString();
        if(val.isEmpty())
        {
            signupUsername.setError("Username cannot be empty");
            returnValue = false;
        }
        else
        {
            signupUsername.setError(null);
        }

        //Sets signupEmail error if empty
        val = signupEmail.getText().toString();
        if(val.isEmpty())
        {
            signupEmail.setError("Email cannot be empty");
            returnValue = false;
        }
        else
        {
            signupEmail.setError(null);
        }

        //Sets signupPassword error if empty
        val = signupPassword.getText().toString();
        if(val.isEmpty())
        {
            signupPassword.setError("Password cannot be empty");
            returnValue = false;
        }
        else
        {
            signupPassword.setError(null);
        }

        return returnValue;
    }

    /**
     * This class is involved in posting the users to the database, and save the information
     * of the users.
     */
    private void postRequest() {

        // Convert input to JSONObject
        JSONObject postBody = null;

        if(validateCredentials() != true)
        {
            Toast.makeText(SignupActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try{
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            postBody = new JSONObject();
            postBody.put("name", signupUsername.getText().toString());
            postBody.put("emailId",signupEmail.getText().toString());
            postBody.put("password", signupPassword.getText().toString());
            postBody.put("ifActive", "true");
        } catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                postBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(SignupActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignupActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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