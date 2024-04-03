package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import org.java_websocket.handshake.ServerHandshake;

/**
 * This class manages the messages' page for different study groups.
 */
public class MessageActivity extends AppCompatActivity implements WebSocketListener {
    private static EditText MessageTextSend;
    private static Button msgButton;
    private static TextView sentVeri;
    private static Button getMESSAGES;
    private static TextView AllMessages;
    private static Button DeleteBUTTON;
    private static EditText UPDATEtext;
    private  static Button UPDATEmsgBtn;

    private static Button connectBtn;
    private static String serverURL;

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
        WebSocketManager.getInstance().setWebSocketListener(MessageActivity.this);

        MessageTextSend = findViewById(R.id.MessageText);
        msgButton = findViewById(R.id.sendBUTTON);
        getMESSAGES = findViewById(R.id.getMessageButton);
        AllMessages = findViewById(R.id.allMessages);
        DeleteBUTTON = findViewById(R.id.deleteMessage);
        UPDATEtext = findViewById(R.id.updateMsgText);
        UPDATEmsgBtn = findViewById(R.id.updateMsgButton);
        connectBtn = findViewById(R.id.connectbutton);

        String username = UsernameSingleton.getInstance().getUserName();

        /**
         * This function works on click of the connect button that connects the
         * application to the remote server.
         */
        connectBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                serverURL = "ws://coms-309-016.class.las.iastate.edu:8080/chat/" + username;
//                serverURL = "ws://10.0.2.2:9090/chat/" + username;

                // Establish WebSocket connection and set listener
                WebSocketManager.getInstance().connectWebSocket(serverURL);
                WebSocketManager.getInstance().setWebSocketListener(MessageActivity.this);

            }
        });

        /**
         * This function works on click of the update button that allows user to
         * update messages to what is sent by the user in the updated text field.
         */
        UPDATEmsgBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                putRequest();
            }
        });

        /**
         * This function works on click of the get messages button that shows
         * all the messages sent in the group chat.
         */
        getMESSAGES.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getRequest();
            }
        });

        /**
         * This function works on click of the delete button that deletes
         * certain messages from the database.
         */
        DeleteBUTTON.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                deleteRequest();
            }
        });

        /**
         * This function works on click of the message button that allows user to
         * send messages that is sent in the given text field.
         */
        msgButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                postRequest();
                try
                {
                    // send message
                    WebSocketManager.getInstance().sendMessage(MessageTextSend.getText().toString());
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
        String url = "http://coms-309-016.class.las.iastate.edu:8080/messages/all";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Display the first 500 characters of the response string.
                        // String response can be converted to JSONObject via
                        // JSONObject object = new JSONObject(response);
                        AllMessages.setText(response);
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
        sentVeri = findViewById(R.id.sentVerify);

        String url = "http://coms-309-016.class.las.iastate.edu:8080/messages/post";
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
                        Log.e("Response Entered",response.toString());
                        sentVeri.setText("Response is: "+ response.toString());
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
        runOnUiThread(() ->
        {
            String s = AllMessages.getText().toString();
            AllMessages.setText(s + "\n"+message);
        });
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote)
    {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {
            String s = MessageTextSend.getText().toString();
            AllMessages.setText(s + "---\nconnection closed by " + closedBy + "\nreason: " + reason);
        });
    }

    @Override
    public void onWebSocketError(Exception ex) {

    }
}