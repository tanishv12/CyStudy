package com.example.androidexample;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProfileActivity extends AppCompatActivity {
    TextView name, email, username, password;
    //updatedName, updatedPassword used to hold updated values in the popup
    String nameValue, emailValue, usernameValue, passwordValue, updatedName, updatedPassword;
    //typeCodeTemp used to store the type of value being changed for putRequest
    String typeCodeTemp;
    Button logout, updateButton;

    /**
     * Creates home page UI
     * @param savedInstanceState Stores information needed to reload UI on system crashes
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);
        super.onCreate(savedInstanceState);

        name=findViewById(R.id.signup_name_textView);

        email=findViewById(R.id.signup_email_textView);

        username=findViewById(R.id.signup_username_textView);
        usernameValue = UsernameSingleton.getInstance().getUserName();

        password=findViewById(R.id.signup_password_textView);

        logout=findViewById(R.id.logout_button);

        getRequest();

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeCodeTemp = "name";
                showCustomDialog();
            }
        });

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeCodeTemp = "username";
                showCustomDialog();
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeCodeTemp = "password";
                showCustomDialog();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putActiveRequest();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //Navigation Bar Code Start
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavbar);
        bottomNavigationView.setSelectedItemId(R.id.Profile);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.Home)
            {
                return true;
            }
            if(item.getItemId() == R.id.Classes)
            {
                startActivity(new Intent(getApplicationContext(), ClassFragment.class));
                return true;
            }
            if(item.getItemId() == R.id.StudyGroups)
            {
                startActivity(new Intent(getApplicationContext(), StudyGroupFragment.class));
                return true;
            }
            if(item.getItemId() == R.id.Profile)
            {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                return true;
            }
            return false;
        });
        //Navigation Bar Code End

    }
    // Method to show the custom dialog
    private void showCustomDialog() {
        // Inflate the custom dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.update_info_activity, null);

        // Find the EditText views
        EditText updatedNameEditText = dialogView.findViewById(R.id.updatedNameEditText);
        EditText currentPasswordEditText = dialogView.findViewById(R.id.currentPasswordEditText);
        updateButton=dialogView.findViewById(R.id.updateButton);

        updatedNameEditText.setHint("Enter new " + typeCodeTemp);

        // Create a dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        // Create the dialog
        AlertDialog dialog = builder.create();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the text entered in the EditText views
                updatedName = updatedNameEditText.getText().toString();
                updatedPassword = currentPasswordEditText.getText().toString();

                if(typeCodeTemp.equals("name")){
                    putRequestName(typeCodeTemp, usernameValue, nameValue, updatedName);
                } else if (typeCodeTemp.equals("username")) {
                    putRequestUsername(typeCodeTemp, usernameValue, updatedName, updatedPassword);
                } else if (typeCodeTemp.equals("password")) {
                    putRequestPassword(typeCodeTemp, usernameValue, updatedPassword, updatedName);
                }
                // Dismiss the dialog after processing the values
                dialog.dismiss();
                getRequest();
            }
        });

        // Show the dialog
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    /**
     * Updates study group name that the user is in
     */
    private void putActiveRequest()
    {
        Log.d("DialogActions", "putRequest method called");
        String url = "http://coms-309-016.class.las.iastate.edu:8080/users/logout/" + usernameValue;

        JSONObject putBody = null;

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

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    /**
     * Retrieves study group that the user is in
     */
    private void getRequest()
    {
        String url;
        url = "http://coms-309-016.class.las.iastate.edu:8080/users/" + usernameValue;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String currentText;

                            nameValue=jsonObject.getString("name");
                            currentText = "Name: ";
                            currentText+= nameValue;
                            name.setText(currentText);

                            emailValue=jsonObject.getString("emailId");
                            currentText = "Email: ";
                            currentText+= emailValue;
                            email.setText(currentText);

                            usernameValue=jsonObject.getString("userName");
                            currentText = "Username: ";
                            currentText+= usernameValue;
                            username.setText(currentText);

                            passwordValue=jsonObject.getString("password");
                            currentText = "Password: ";
                            currentText+= "●●●●●●●●";
                            password.setText(currentText);

                        }
                        catch (JSONException err)
                        {
                            Log.d("Error", err.toString());
                        }
                        // Display the first 500 characters of the response string.
                        // String response can be converted to JSONObject via
                        // JSONObject object = new JSONObject(response);

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

    private void putRequestName(String tempType, String value1, String value2, String value3)
    {
        Log.d("DialogActions", "putRequest method called");
        String url = "http://coms-309-016.class.las.iastate.edu:8080/users/"+tempType +"/" + value1 +"/"+ value2 +"/"+ value3;

        StringRequest request = new StringRequest(
                Request.Method.PUT,
                url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.e("Response Entered",response);
                        Toast.makeText(ProfileActivity.this, response, Toast.LENGTH_SHORT).show();
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

    private void putRequestUsername(String tempType, String value1, String value2, String value3)
    {
        Log.d("DialogActions", "putRequest method called");
        String url = "http://coms-309-016.class.las.iastate.edu:8080/users/"+tempType +"/" + value1 +"/"+ value2 +"/"+ value3;

        StringRequest request = new StringRequest(
                Request.Method.PUT,
                url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        UsernameSingleton.getInstance().setUserName(value2);
                        username.setText("Username: " + value2);
                        usernameValue = value2;
                        Toast.makeText(ProfileActivity.this, response, Toast.LENGTH_SHORT).show();
                        Log.e("Response Entered",response);

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e("Error Response", error.toString());
                        Toast.makeText(ProfileActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

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

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void putRequestPassword(String tempType, String value1, String value2, String value3)
    {
        Log.d("DialogActions", "putRequest method called");
        String url = "http://coms-309-016.class.las.iastate.edu:8080/users/"+tempType +"/" + value1 +"/"+ value2 +"/"+ value3;

        StringRequest request = new StringRequest(
                Request.Method.PUT,
                url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(ProfileActivity.this, response, Toast.LENGTH_SHORT).show();
                        Log.e("Response Entered",response);
                        passwordValue = value3;

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

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}