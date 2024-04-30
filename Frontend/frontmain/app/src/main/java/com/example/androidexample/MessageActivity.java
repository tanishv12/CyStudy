package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Group;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.androidexample.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.java_websocket.handshake.ServerHandshake;

/**
 * This class manages the messages' page for different study groups.
 */
public class MessageActivity extends AppCompatActivity implements WebSocketListener {
    private static EditText MessageTextSend;

    private static AppCompatImageView msgButton;
    private static TextView sentVeri;
    private static Button getMESSAGES;
    private static TextView AllMessages;
    private static Button DeleteBUTTON;
    private static EditText UPDATEtext;
    private  static Button UPDATEmsgBtn;

    private String groupmaster;

    private static TextView GroupHeading;

    private static String messages;
//    private static String message;

    private static String username;
    private static String sentMessageUser;

    private StringBuilder allMessagesBuilder = new StringBuilder();

    private static AppCompatImageView backButton;

    private static Button connectBtn;
    private static String serverURL;

    private JSONObject jsonObj;

    private static String GroupName;




    /**
     * This class creates and maps instances to different features in the messages
     * UI screen.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        groupmaster = GroupMasterSingleton.getInstance().getGroupMaster();
//        binding = ActivityChatBinding.inflate(getLayoutInflater());

        WebSocketManager.getInstance().setWebSocketListener(MessageActivity.this);
        username = UsernameSingleton.getInstance().getUserName();
        GroupName = GroupSingleton.getInstance().getGroupName();


        serverURL = "ws://coms-309-016.class.las.iastate.edu:8080/chat/" + username + "/" + GroupName + "/" + "end";
        String encodedPath = serverURL.replace(" ", "%20");
        Log.e("url", "Server URL: " + encodedPath);
        WebSocketManager.getInstance().connectWebSocket(encodedPath);




        GroupHeading = findViewById(R.id.groupHeading);
        GroupHeading.setText(GroupName);

        getRequest();


        backButton = findViewById(R.id.imageBack);


        GroupHeading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                WebSocketManager.getInstance().disconnectWebSocket();
                Log.e("user heading", "user name heading: " + username);
                Log.e("master", "group master heading: " + groupmaster);
                if(username.compareTo(groupmaster) == 0)
                {
                    Intent intent = new Intent(MessageActivity.this, GroupInfo_ManagerActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(MessageActivity.this, GroupInformation.class);
                    startActivity(intent);
                }
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                WebSocketManager.getInstance().disconnectWebSocket();
                Intent intent = new Intent(MessageActivity.this, StudyGroupFragment.class);
                startActivity(intent);
            }
        });

        MessageTextSend = findViewById(R.id.MessageText);
        msgButton = findViewById(R.id.sendBUTTON);
//        getMESSAGES = findViewById(R.id.getMessageButton);
        AllMessages = findViewById(R.id.allMessages);
//        DeleteBUTTON = findViewById(R.id.deleteMessage);
//        UPDATEtext = findViewById(R.id.updateMsgText);
//        UPDATEmsgBtn = findViewById(R.id.updateMsgButton);
//        connectBtn = findViewById(R.id.connectbutton);


        /**
         * This function works on click of the update button that allows user to
         * update messages to what is sent by the user in the updated text field.
         */
//        UPDATEmsgBtn.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                putRequest();
//            }
//        });

        /**
         * This function works on click of the get messages button that shows
         * all the messages sent in the group chat.
         */
//        getMESSAGES.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                getRequest();
//            }
//        });

        /**
         * This function works on click of the delete button that deletes
         * certain messages from the database.
         */
//        DeleteBUTTON.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                deleteRequest();
//            }
//        });

        /**
         * This function works on click of the message button that allows user to
         * send messages that is sent in the given text field.
         */
        msgButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    // send message
                    String messageToSend = MessageTextSend.getText().toString();
                    // send message
                    WebSocketManager.getInstance().sendMessage(messageToSend);


                    // Immediate feedback in the UI
                    MessageTextSend.setText("");

                    Log.e("message", allMessagesBuilder.toString());

                }
                catch (Exception e)
                {
                    Log.d("ExceptionSendMessage:", e.getMessage().toString());
                }
            }
        });
    }
    /**
     * This function works as a GET request communicating with the backend to show
     * all the messages sent.
     */
    private void getRequest()
    {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/messages/all/group" + "/" + GroupName;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        allMessagesBuilder.setLength(0);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i < jsonArray.length(); i++) {
                                // Get each JSONObject within the array
                                jsonObj = jsonArray.getJSONObject(i);
                                Log.e("json", "this is the json" + jsonObj.toString());

//                                messages = jsonObj.getString("messageContent");
//                                JSONObject senderObject = jsonObj.getJSONObject("sender");
//                                sentMessageUser = senderObject.getString("userName");
//
//                                Log.e("user", "this is the user" + sentMessageUser);
//                                allMessagesBuilder.append(sentMessageUser).append(": ").append(messages).append("\n");
                                JSONObject groupInfo = jsonObj.getJSONObject("group_id");
                                String currentGroup = groupInfo.getString("groupName");
                                Log.e("currentGp", "group" + currentGroup);
                                Log.e("GpName", "group" + GroupName);

                                if (currentGroup.equals(GroupName))
                                {
                                    String sender = jsonObj.getJSONObject("sender").getString("userName");
                                    String messageContent = jsonObj.getString("messageContent");
                                    allMessagesBuilder.append(sender).append(": ").append(messageContent).append("\n");
                                }
                            }
                            AllMessages.setText(allMessagesBuilder.toString());
                        }
                        catch (JSONException err)
                        {
                            Log.d("Error", err.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

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
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
    /**
     * This function works as a PUT request communicating with the backend to update
     * messages sent.
     */
    private void putRequest()
    {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/messages/update";
        // Convert input to JSONObject
        JSONObject putBody = null;

        try
        {
            Log.e("Try Entered","oisafuhgiureshg");
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            putBody = new JSONObject();
            putBody.put("messageContent", UPDATEtext.getText().toString());
            Log.e("what is putbody",putBody.toString());
            Log.e("Try BLAH","oisafuhgiureshg");
            url += "/" + "16";
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


    /**
     * This function works as a DELETE request communicating with the backend to delete
     * messages sent.
     */
    private void deleteRequest()
    {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/messages/delete";
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
     * This function works as a POST request communicating with the backend to send
     * to the database.
     */
    private void postRequest()
    {
//        sentVeri = findViewById(R.id.sentVerify);

        String url = "http://coms-309-016.class.las.iastate.edu:8080/messages/post/Group1/" + username;
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
            postBody.put("messageContent", MessageTextSend.getText().toString());
//            url += "/" + MessageTextSend.getText().toString();
////                    + "/" + loginPassword.getText().toString();
        }
        catch (Exception e)
        {
            Log.e("Catch Entered","wkerufhieuwhf");
            e.printStackTrace();
        }

        JSONObject finalPostBody = postBody;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                postBody,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {

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

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onWebSocketMessage(String message)
    {
        runOnUiThread(() -> {
                String formattedMessage = message + "\n";
                allMessagesBuilder.append(formattedMessage);
                AllMessages.append(formattedMessage);
        });
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote)
    {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {
//            String s = MessageTextSend.getText().toString();
//            AllMessages.setText(s + "---\nconnection closed by " + closedBy + "\nreason: " + reason);
        });
    }
    @Override
    public void onWebSocketError(Exception ex) {

    }
}