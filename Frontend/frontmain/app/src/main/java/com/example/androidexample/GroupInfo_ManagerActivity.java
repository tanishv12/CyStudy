package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GroupInfo_ManagerActivity extends AppCompatActivity {

    private AppCompatImageView backToGrpChat;

    private AppCompatImageView optionsGrpBtn;

    private LinearLayout calenderInfo;

    private Boolean groupMaster = false;

    private String groupNameSet;

    private LinearLayout userContainer;
    private Button editGroupBtn;

    private String user;

    private String username;
    private String users;
    private String newGroupName;
    private EditText enterEditGroup;
    private TextView GroupHeadingName;


    private String location;

//    private String GroupMaster;
//    private String CreateGroupMaster;
    private Button saveBtn;

    private Button meetingInformation;



    private Button leaveGroupBtn;
    private Button doneButton;


    private static final String[] Duration = new String[]
            {
                    "1 hour", "2 hours", "3 hours", "4 hours"
            };


    private EditText enterDate;
    private EditText enterTime;
    private AutoCompleteTextView enterDuration;
    private Button updateButton;

    private int durationTime;


    private LocalTime time;
    private LocalDate selectedDate;

    private EditText SetLocation;

    private int hours,minutes;


    private void showDatePicker() {
        // Get current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create and show DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    // Create LocalDate from the selected date
                    selectedDate = LocalDate.of(year1, monthOfYear + 1, dayOfMonth);
                    // Optionally, format LocalDate to display it
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String formattedDate = selectedDate.format(formatter);

                    // Set the formatted date to TextView
                    enterDate.setText(formattedDate);
                }, year, month, day);

        datePickerDialog.show();
    }

    private void meetinginformation(Context c)
    {
        LayoutInflater inflater = LayoutInflater.from(c);
        View dialogView2 = inflater.inflate(R.layout.activity_calender_info, null);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Set Calender Meeting")
                .setView(dialogView2)
                .setNegativeButton("Cancel", null)
                .create();

        enterDate = dialogView2.findViewById(R.id.setDay);
        enterTime = dialogView2.findViewById(R.id.setTime);
        enterDuration = dialogView2.findViewById(R.id.setDuration);
        SetLocation = dialogView2.findViewById(R.id.setLocation);

        Log.e("duration time","time int"+durationTime);


        updateButton = dialogView2.findViewById(R.id.buttonUpdate);
//        updateGrpName.setText(groupname);


//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line, Days);
//        AutoCompleteTextView textView = (AutoCompleteTextView)
//                enterDay;
//        textView.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Duration);
        AutoCompleteTextView textView2 = (AutoCompleteTextView)
                enterDuration;
        textView2.setAdapter(adapter2);


        enterDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                showDatePicker();
            }
        });



        enterTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        GroupInfo_ManagerActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                hours = hourOfDay;
                                minutes = minute;

                                time = LocalTime.of(hours,minutes);
                                Log.e("time","local time"+ time);
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                                String formattedTime = time.format(formatter);
//                                Log.e("time","local time"+ formattedTime);
                                enterTime.setText(formattedTime);
                            }
                        }, 12, 0, false
                );
                timePickerDialog.updateTime(hours, minutes);
                timePickerDialog.show();
            }
        });



        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location = SetLocation.getText().toString();
                String dur = enterDuration.getText().toString();
                String dura = dur.replace(" hour", "");
                durationTime = Integer.parseInt(dura);
                postMeetingTimes();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void postMeetingTimes()
    {
        String base_url = "http://coms-309-016.class.las.iastate.edu:8080/timings/post/" + groupNameSet + "/end";
        String url = base_url.replace(" ", "%20");
        JSONObject postBody = null;
        try
        {
            Log.e("duration","timeeee "+durationTime);
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            postBody = new JSONObject();
            postBody.put("date", selectedDate);
            postBody.put("startTime", time);
            postBody.put("duration", durationTime);
            postBody.put("day", selectedDate.getDayOfWeek());
            postBody.put("location", location);
        }
        catch (Exception e)
        {
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




    private void editGroupNameDialog(Context c)
    {
        LayoutInflater inflater = LayoutInflater.from(c);
        View dialogView = inflater.inflate(R.layout.activity_edit_groupname, null);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Edit Group Name")
                .setView(dialogView)
                .setNegativeButton("Cancel", null)
                .create();

        enterEditGroup = dialogView.findViewById(R.id.changeGrpName);
//        enterEditGroup.setText(groupNameSet);
        saveBtn = dialogView.findViewById(R.id.buttonSave);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                newGroupName = String.valueOf(enterEditGroup.getText());
                putRequest();
                GroupHeadingName.setText(newGroupName);
                dialog.dismiss();
            }
        });
        dialog.show();
    }



    private void optionsDialog(Context c)
    {
        LayoutInflater inflater = LayoutInflater.from(c);
        View dialogView = inflater.inflate(R.layout.group_options_manager, null);

        BottomSheetDialog dialog = new BottomSheetDialog(c);
        dialog.setTitle("Options");
        dialog.setContentView(dialogView);
        dialog.create();

        leaveGroupBtn = dialogView.findViewById(R.id.leaveGroup);
        editGroupBtn = dialogView.findViewById(R.id.editGrpName);
        doneButton = dialogView.findViewById(R.id.doneBtn);
        meetingInformation = dialogView.findViewById(R.id.calenderInfo);

        leaveGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                deleteGroupRequest();
                Intent intent = new Intent(GroupInfo_ManagerActivity.this, StudyGroupFragment.class);
                startActivity(intent);
            }
        });

        editGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                editGroupNameDialog(GroupInfo_ManagerActivity.this);
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss(); // Close the dialog if needed
            }
        });


        meetingInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                meetinginformation(GroupInfo_ManagerActivity.this);
            }
        });

        dialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info_manager);
        optionsGrpBtn = findViewById(R.id.options);
        GroupHeadingName = findViewById(R.id.GroupHeadInfo);
        backToGrpChat = findViewById(R.id.imageBackToGroup);
        optionsGrpBtn = findViewById(R.id.options);
//        GroupMaster = GroupMasterSingleton.getInstance().getGroupMaster();

        userContainer = findViewById(R.id.usersHolder);

        groupNameSet = GroupSingleton.getInstance().getGroupName();
        Log.e("group","groupNameSet" + groupNameSet);

        getRequest();

        backToGrpChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(GroupInfo_ManagerActivity.this, MessageActivity.class);
                startActivity(intent);
            }
        });

        GroupHeadingName.setText(groupNameSet);
        optionsGrpBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                optionsDialog(GroupInfo_ManagerActivity.this);
            }
        });
    }
    private void deleteGroupRequest()
    {
        String groupLeave = groupNameSet;
        String url = "http://coms-309-016.class.las.iastate.edu:8080/groups/delete/" + groupLeave + "/" + "end";
        StringRequest request = new StringRequest(
                Request.Method.DELETE,
                url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

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

    private void removeUserRequest()
    {
        String groupLeave = groupNameSet;
        String USER = UsernameSingleton.getInstance().getUser();
        String url = "http://coms-309-016.class.las.iastate.edu:8080/groups/delete/" + groupLeave + "/" + USER;
        StringRequest request = new StringRequest(
                Request.Method.DELETE,
                url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

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


    private void getGroupMaster(String userNAME)
    {

        String url = "http://coms-309-016.class.las.iastate.edu:8080/groupMasterCheck/" + groupNameSet + "/" + userNAME;
        url = url.replace(" ", "%20");
        Log.e("get url","this is the url " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        if(response != null && !response.isEmpty())
                        {
                            Log.e("whaat is the response","true of falseeee " + response);
                            if(("true").equals(response))
                            {
                                groupMaster = true;
                                Log.e("master in true","group master" + groupMaster);
                            }
                            else if(("false").equals(response))
                            {
                                groupMaster = false;
                                Log.e("master in false","group master" + groupMaster);
                            }
                            else
                            {
                                Toast.makeText(GroupInfo_ManagerActivity.this, "Response is empty or null", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e("error","error response "+ error);
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
//        return groupMaster;
    }



    private void getRequest()
    {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/groups/all/users/" + groupNameSet;
        Log.e("group","groupNameSet" + groupNameSet);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            Log.e("users","user name" + response);
                            JSONArray jsonArray = new JSONArray(response);

//                                users = "";
                            for(int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject jsonObj = jsonArray.getJSONObject(i);
                                user = jsonObj.getString("name");
                                username = jsonObj.getString("userName");
                                int groupId = jsonObj.getInt("id");
                                Log.e("user","user name"+user);
//                                  users.add(user);

//                                  String groupRate = name + "\n" + "Group Rating: "+ rating;
//                                    users +=  user + "\n";
                                Log.e("user","users " + user);
//                                    members.setText(users);
//                                GroupSingleton.getInstance().setGroupName(name);

                                CardView cardView = createCard(user, username);
////                                GroupSingleton.getInstance().setGroupName(groupName);
                                userContainer.addView(cardView);
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

    private CardView createCard(String nameOfUser, String username) {
        // Create a new CardView and set up its layout parameters
//        String groupRate = users;
//        Log.e("users", "user name: " + users);
        Log.e("name of user","users " + nameOfUser);

        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                convertDpToPixels(70, this)
        );
        layoutParams.setMargins(
                convertDpToPixels(16, this),
                convertDpToPixels(8, this),
                convertDpToPixels(16, this),
                convertDpToPixels(8, this)
        );
        cardView.setLayoutParams(layoutParams);

        // Set the CardView's appearance
        cardView.setCardElevation(convertDpToPixels(4, this));
        cardView.setRadius(convertDpToPixels(4, this));


        LinearLayout cardContentLayout = new LinearLayout(this);
        cardContentLayout.setOrientation(LinearLayout.HORIZONTAL);
        cardContentLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        cardView.setBackgroundResource(R.drawable.card_background);

        // Create a TextView for the group name
        TextView userNameView = new TextView(this);
        userNameView.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
        ));


        userNameView.setText(nameOfUser);
        userNameView.setTypeface(null, Typeface.BOLD);
        userNameView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        userNameView.setGravity(Gravity.LEFT);
//        userNameView.setGravity(Gravity.CENTER);
        cardContentLayout.addView(userNameView);
        cardView.addView(cardContentLayout);



        if(!groupMaster)
        {
            Button removeButton = new Button(this);
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            buttonLayoutParams.gravity = Gravity.END;
            removeButton.setLayoutParams(buttonLayoutParams);
            removeButton.setText("Remove");
            cardContentLayout.addView(removeButton);

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    UsernameSingleton.getInstance().setUser(username);
                    removeUserRequest();
                    Intent intent = new Intent(GroupInfo_ManagerActivity.this, GroupInfo_ManagerActivity.class);
                    startActivity(intent);
                }
            });
        }
        else
        {
            return cardView;
        }

//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(StudyGroupFragment.this, MessageActivity.class);
//                GroupSingleton.getInstance().setGroupName(name);
//                startActivity(intent);
//            }
//        });


//        // Set a long-click listener on the CardView
//        cardView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                // Handle the long click event
////                showForgotDialog(StudyGroupFragment.this);
//
//                // Return true to indicate that you have handled the long click event
//                return true;
//            }
//        });
        return cardView;
    }

    private void putRequest()
    {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/groups/update/" + groupNameSet + "/" + newGroupName;
        StringRequest request = new StringRequest(
                Request.Method.PUT,
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

    private int convertDpToPixels(float dp, Context context)
    {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
}
