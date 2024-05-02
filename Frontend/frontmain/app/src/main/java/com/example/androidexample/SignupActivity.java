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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
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

    private String url;

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
        url = "http://coms-309-016.class.las.iastate.edu:8080/users/register";

        /**
         * This is the on click function for the sign up button creates the user in
         * the application after they enters their information and click the button.
         */
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                postRequest();
            }
        });
        /**
         * This function is involved in redirecting users who already have accounts so they
         * don't have to sign up again on the registration page on clicking the "Not yet registered? Sign Up".
         */
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
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
        String name = signupName.getText().toString().trim();
        String email = signupEmail.getText().toString().trim();
        String username = signupUsername.getText().toString().trim();
        String password = signupPassword.getText().toString().trim();

        // Validate user input
        if (!validateCredentials()) {
            Toast.makeText(SignupActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a JSON object for the request body
        JSONObject postBody = new JSONObject();
        try {
            postBody.put("name", name);
            postBody.put("emailId", email);
            postBody.put("emailId", email);
            postBody.put("userName", username);
            postBody.put("password", password);
            Log.e("body", postBody.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a request
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                postBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String stringResponse;
                        try {
                            stringResponse = response.getString("message");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        if(stringResponse.equals("User created successfully")){
                            Toast.makeText(SignupActivity.this, stringResponse, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupActivity.this, NewCourseRegActivity.class);
                            UsernameSingleton.getInstance().setUserName(username);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(SignupActivity.this, stringResponse, Toast.LENGTH_SHORT).show();
                        }
                        Log.e("response: ", response.toString());
                        // Registration successful, handle the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Error occurred, handle the error
                        Log.e("error: ", error.toString());
                    }
                }
        );

        // Add the request to the Volley request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void handleResponse(JSONObject response) {
        // Handle the server response when registration is successful
        Intent intent = new Intent(SignupActivity.this, NewCourseRegActivity.class);
        startActivity(intent);
    }

    private void handleError(VolleyError error) {
        // Handle errors when registering the user
        if (error.networkResponse != null && error.networkResponse.data != null) {
            try {
                // Parse the error message from the response body
                String errorMessage = new String(error.networkResponse.data, "UTF-8");
                Toast.makeText(SignupActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                Log.e("error: ", errorMessage);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            // Handle other types of errors (e.g., network error)
            Toast.makeText(SignupActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}