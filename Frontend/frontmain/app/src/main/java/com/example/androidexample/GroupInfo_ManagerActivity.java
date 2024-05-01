package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GroupInfo_ManagerActivity extends AppCompatActivity {

    private AppCompatImageView backToGrpChat;

    private AppCompatImageView optionsGrpBtn;

    private LinearLayout calenderInfo;

    private String groupNameSet;

    private LinearLayout userContainer;
    private Button editGroupBtn;

    private String user;

    private String username;
    private String users;
    private String newGroupName;
    private EditText enterEditGroup;
    private TextView GroupHeadingName;

    private String GroupMaster;
    private Button saveBtn;

    private Button meetingInformation;

    private Button leaveGroupBtn;
    private Button doneButton;

    private static final String[] Days = new String[]
            {
                    "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"
            };

    private static final String[] Duration = new String[]
            {
                    "1 hour", "2 hours", "3 hours", "4 hours"
            };


    private AutoCompleteTextView enterDay;
    private EditText enterTime;
    private AutoCompleteTextView enterDuration;
    private Button updateButton;

    private int hours,minutes;

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

    private void meetinginformation(Context c)
    {
        LayoutInflater inflater = LayoutInflater.from(c);
        View dialogView = inflater.inflate(R.layout.activity_calender_info, null);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Set Calender Meeting")
                .setView(dialogView)
                .setNegativeButton("Cancel", null)
                .create();

        enterDay = dialogView.findViewById(R.id.setDay);
        enterTime = dialogView.findViewById(R.id.setTime);
        enterDuration = dialogView.findViewById(R.id.setDuration);
        updateButton = dialogView.findViewById(R.id.buttonUpdate);
//        updateGrpName.setText(groupname);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Days);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                enterDay;
        textView.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Duration);
        AutoCompleteTextView textView2 = (AutoCompleteTextView)
                enterDuration;
        textView2.setAdapter(adapter2);





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
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0,0,0,hours,minutes);

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa");
                                String formattedTime = simpleDateFormat.format(calendar.getTime());
                                Log.e("time","local time"+ formattedTime);
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
                dialog.dismiss();
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
        GroupMaster = GroupMasterSingleton.getInstance().getGroupMaster();

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
                LinearLayout.LayoutParams.WRAP_CONTENT
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

        // Create a TextView for the group name
        TextView userNameView = new TextView(this);
        userNameView.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
        ));


        userNameView.setText(nameOfUser);
        userNameView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        userNameView.setGravity(Gravity.LEFT);
//        userNameView.setGravity(Gravity.CENTER);
        cardContentLayout.addView(userNameView);
        cardView.addView(cardContentLayout);

        if(!username.equals(GroupMaster))
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
