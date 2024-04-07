package com.example.androidexample;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * StudyGroupFragment instance used for group members to see
 * who is joining and leaving the study group.
 *
 * Utilizes websockets to a live notification display
 * as well as CRUDL operations for study groups
 */
public class StudyGroupFragment extends AppCompatActivity implements WebSocketListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private StringBuilder names = new StringBuilder();

    private String user;

    private TextView gresponse;
    private Button getButton;
    private Button postButton;
    private Button studyGroupsToClasses;

    private Button updateButton;
    private EditText updateGrp;

    private Button studyGroupsToMessages;

    private FloatingActionButton addGrp;

    private Button deleteBTN;

    private Button connectButton;

    private static final int CREATE_GROUP_REQUEST_CODE = 1;

    private EditText GroupText;

    private static String serverURL;

    private TextView studyGrpHead;

    private LinearLayout cardsContainer;

    private ActivityResultLauncher<Intent> mCreateGroupResultLauncher;





    public int convertDpToPixels(float dp, Context context)
    {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    /**
     * Creates a study group user interface
     * @param savedInstanceState Stores information needed to reload UI on system crashes
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.fragment_study_group);
        user = UsernameSingleton.getInstance().getUserName();
        getRequest();
        ActivityResultLauncher<Intent> mCreateGroupResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        // Retrieve the group information
                        studyGrpHead = findViewById(R.id.studyHead);
                        String groupName = result.getData().getStringExtra("groupName");
                        Log.e("group", "group name: " + groupName); //Learn how to completely transfer data this is temporary.
                        names.append(groupName).append("\n");
                        gresponse.setText(names);
                    }
                }
        );

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavbar);
        bottomNavigationView.setSelectedItemId(R.id.StudyGroups);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.StudyGroups) {
                return true;
            } if (item.getItemId() == R.id.Home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            } if (item.getItemId() == R.id.Classes) {
                startActivity(new Intent(getApplicationContext(), ClassFragment.class));
                return true;
            }
            return false;
        });

        super.onCreate(savedInstanceState);

        WebSocketManager.getInstance().setWebSocketListener(StudyGroupFragment.this);

        addGrp = findViewById(R.id.addGroup);
        studyGroupsToMessages = findViewById(R.id.toMessages);
//        getButton = findViewById(R.id.getBUTTON);
        gresponse = findViewById(R.id.getresponse);
//        postButton = findViewById(R.id.postButton);
//        updateButton = findViewById(R.id.putButton);
//        updateGrp = findViewById(R.id.updateGroup);
//        GroupText = findViewById(R.id.groupText);
//        deleteBTN = findViewById(R.id.deleteGrpButton);
//        connectButton = findViewById(R.id.userConnectButton);


        addGrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(StudyGroupFragment.this, AddStudyGrp.class);
                mCreateGroupResultLauncher.launch(intent);

//                Intent intent = new Intent(StudyGroupFragment.this, AddStudyGrp.class);
//                startActivity(intent);
//                activityResultLauncher.launch(intent);
            }
        });

        /**
         * Connects user to chat server using websockets
         */
//        connectButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                user = UsernameSingleton.getInstance().getUserName();
//                serverURL = "ws://coms-309-016.class.las.iastate.edu:8080/user/"+user;
//
//                WebSocketManager.getInstance().connectWebSocket(serverURL);
//                WebSocketManager.getInstance().setWebSocketListener(StudyGroupFragment.this);
//            }
//        });

        /**
         * Gets study group name
         */
//        getButton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                getRequest();
//            }
//        });

        /**
         * Deletes study group
         */
//        deleteBTN.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                deleteRequest();
//            }
//        });

        /**
         * Creates study group
         */
//        postButton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                postRequest();
//            }
//        });

        /**
         * Updates study group name
         */
//        updateButton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                putRequest();
//            }
//        });





        /**
         * Redirects user to groupchat activity
         */
        studyGroupsToMessages.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudyGroupFragment.this, MessageActivity.class);
                startActivity(intent);
            }
        });
    }

    //group_id   course_id
    public StudyGroupFragment()
    {
        // Required empty public constructor
    }

    /**
     * Deletes study group that the user is currently in
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


    /**
     * Updates study group name that the user is in
     */
    private void putRequest()
    {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/groups/update";
        // Convert input to JSONObject
        JSONObject putBody = null;

        try
        {
            Log.e("Try Entered","oisafuhgiureshg");
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            putBody = new JSONObject();
            putBody.put("groupName", updateGrp.getText().toString());
            Log.e("what is putbody",putBody.toString());
            Log.e("Try BLAH","oisafuhgiureshg");
            url += "/" + "8";
            Log.e("What is url",url.toString());
////                    + "/" + loginPassword.getText().toString();
        }
        catch (Exception e)
        {
            Log.e("Catch Entered","wkerufhieuwhf");
            e.printStackTrace();
        }

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

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }




    public static JSONObject JsonConverter(String jsonString) {
        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            return null; // Or handle the error as appropriate for your application
        }
    }

    public static JSONArray stringToJsonArray(String jsonString) {
        try {
            return new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            return null; // Or handle the error as appropriate for your application
        }
    }



    /**
     * Retrieves study group that the user is in
     */
    private void getRequest()
    {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/groups/all";
        url = "http://coms-309-016.class.las.iastate.edu:8080/groups/all" + "/" + user;





        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){


                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i < jsonArray.length(); i++) {
                                // Get each JSONObject within the array
                                JSONObject jsonObj = jsonArray.getJSONObject(i);

                                // Access the value associated with the key "name"
                                String name = jsonObj.getString("groupName");
                                names.append(name).append("\n");
                            }
                            gresponse.setText(names);
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

    /**
     * Creates new study group which will contain the user
     */
    private void postRequest()
    {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/groups/post";
        // Convert input to JSONObject
        JSONObject postBody = null;

        try
        {
            Log.e("Try Entered","oisafuhgiureshg");
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            postBody = new JSONObject();
            Log.e("JSON Created","Json was created bla bla");
            postBody.put("studyGroup", GroupText.getText().toString());
            url += "/" + GroupText.getText().toString();
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


    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    /**
     * Gets websocket message
     * @param message The received WebSocket message.
     */
    @Override
    public void onWebSocketMessage(String message) {
        runOnUiThread(() ->
        {
            String s = gresponse.getText().toString();
            gresponse.setText(s + "\n"+message);
        });

    }

    /**
     * Runs on websocket closure
     * @param code   The status code indicating the reason for closure.
     * @param reason A human-readable explanation for the closure.
     * @param remote Indicates whether the closure was initiated by the remote endpoint.
     */
    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {

    }

    /**
     * Runs when websocket encounters an error
     * @param ex The exception that describes the error.
     */
    @Override
    public void onWebSocketError(Exception ex) {

    }
}