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

import java.util.HashMap;
import java.util.Map;

/**
 * LoginActivity instance used for the user to login
 *
 * This instance assures that the user's information is in
 * the database and correct in order to access the application.
 */
public class LoginActivity extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView signupRedirectText;


    /**
     * Creates login page user interface
     * @param savedInstanceState Stores information needed to reload UI on system crashes
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRequest();
            }
        });
        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Change MainActivity.class to SignupActivity.class when done testing
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Makes sure username field is not empty
     * @return True if not empty, false otherwise
     */
    public Boolean validateUsername()
    {
        String val = loginUsername.getText().toString();
        if(val.isEmpty())
        {
            loginUsername.setError("Username cannot be empty");
            return false;
        }
        else
        {
            loginUsername.setError(null);
            return true;
        }
    }

    /**
     * Makes sure password field is not empty
     * @return True if not empty, false otherwise
     */
    public Boolean validatePassword()
    {
        String val = loginPassword.getText().toString();
        if(val.isEmpty())
        {
            loginPassword.setError("Password cannot be empty");
            return false;
        }
        else
        {
            loginPassword.setError(null);
            return true;
        }
    }

    /**
     * Posts username and password to database as a pair
     */
    private void postRequest() {
        //Change the url from using a specific user ID to the username & pass
        String url = "http://coms-309-016.class.las.iastate.edu:8080/users/login/";
        // Convert input to JSONObject
        JSONObject postBody = null;

        UsernameSingleton.getInstance().setUserName(loginUsername.getText().toString());

        if(validateUsername() != true && validatePassword() != true)
        {
            Toast.makeText(LoginActivity.this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        } else if (validatePassword() != true) {
            Toast.makeText(LoginActivity.this, loginPassword.getError().toString(), Toast.LENGTH_SHORT).show();
            return;
        } else if (validateUsername() != true) {
            Toast.makeText(LoginActivity.this, loginUsername.getError().toString(), Toast.LENGTH_SHORT).show();
            return;
        }

        try{
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            url += loginUsername.getText().toString() + "/" + loginPassword.getText().toString();
            Log.e("link: ", url);
        } catch (Exception e){
            e.printStackTrace();
        }

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Username: ", loginUsername.getText().toString().trim());
//                        Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        if(loginUsername.getText().toString().trim().equals("admin")){
                            Intent intent = new Intent(LoginActivity.this, AdminPanelActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//                        signupRedirectText.setText(error.toString());
                        Log.e("error: ", String.valueOf(error));
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