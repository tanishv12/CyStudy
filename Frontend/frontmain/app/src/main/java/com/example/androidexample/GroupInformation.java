package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroupInformation extends AppCompatActivity {

    private AppCompatImageView backToGrpChat;

    private AppCompatImageView optionsGrpBtn;

    private LinearLayout userContainer;

    private String groupNameSet;

    private String user;

    private String username;

    private String users;
    private TextView GroupHeadingName;

//    private Button editGroupBtn;
    private Button leaveGroupBtn;

    private Button rateGroupBtn;


    private Button doneButton;


    private TextView members;

    private RatingBar rating;

    private Boolean HasRated;

    private double rateNumber;

    private double newrateNumber;
    private String s;


    private void ratingDialog(Context c)
    {
        LayoutInflater inflater = LayoutInflater.from(c);
        View dialogView = inflater.inflate(R.layout.rating_activity, null);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Rate Group")
                .setView(dialogView)
                .setNegativeButton("Cancel", null)
                .create();

        rateGroupBtn = dialogView.findViewById(R.id.rateButton);
        rating = dialogView.findViewById(R.id.ratingBar);
        rateGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
//                s = String.valueOf(rating.getRating());
//                rateNumber = Double.parseDouble(s);
//                if(!HasRated)
//                {
                s = String.valueOf(rating.getRating());
                rateNumber = Double.parseDouble(s);
//                    Log.e("ratenumber","rating 1 "+rateNumber);
                postRating();
//                }
//                else
//                {
//                    s = String.valueOf(rating.getRating());
//                    newrateNumber = Double.parseDouble(s);
//                    Log.e("ratenumber","rating 2 "+newrateNumber);
//                    updateRatingRequest();
//                }

                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void optionsDialog(Context c)
    {
        LayoutInflater inflater = LayoutInflater.from(c);
        View dialogView = inflater.inflate(R.layout.groupoptions, null);
//        BottomSheetDialog dialog = new AlertDialog.Builder(c)
//                .setTitle("Group Options")
//                .setView(dialogView)
//                .setNegativeButton("Cancel", null)
//                .create();
//
//        buttonUpd = dialogView.findViewById(R.id.buttonUpdate);
//        buttonDel = dialogView.findViewById(R.id.buttonDelete);
//        buttonRat = dialogView.findViewById(R.id.buttonRating);
//        updateGrpName = dialogView.findViewById(R.id.updatedGroupName);
//        updateGrpName.setText(groupname);

        BottomSheetDialog dialog = new BottomSheetDialog(c);
        dialog.setTitle("Options");
        dialog.setContentView(dialogView); // Use setContentView instead of setView
//        dialog.setNegativeButton("Done", null);
        dialog.create();

        leaveGroupBtn = dialogView.findViewById(R.id.leaveGroup);
        rateGroupBtn = dialogView.findViewById(R.id.rateGroup);
        doneButton = dialogView.findViewById(R.id.doneBtn);

        leaveGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                deleteRequest();
                Intent intent = new Intent(GroupInformation.this, StudyGroupFragment.class);
                startActivity(intent);
            }
        });
        rateGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ratingDialog(GroupInformation.this);
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss(); // Close the dialog if needed
            }
        });

        dialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_information);
        GroupHeadingName = findViewById(R.id.GroupHeadInfo);
        backToGrpChat = findViewById(R.id.imageBackToGroup);
        optionsGrpBtn = findViewById(R.id.options);
//        members = findViewById(R.id.allGrpMembers);
        userContainer = findViewById(R.id.usersHolder);


        groupNameSet = GroupSingleton.getInstance().getGroupName();

        GroupHeadingName.setText(groupNameSet);

        getRequest();

        backToGrpChat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(GroupInformation.this, MessageActivity.class);
                startActivity(intent);
            }
        });

        optionsGrpBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                optionsDialog(GroupInformation.this);
            }
        });

    }


    private void deleteRequest()
    {
        String groupLeave = groupNameSet;
        username = UsernameSingleton.getInstance().getUserName();
        Log.e("grp name","group name " + groupNameSet);
        Log.e("user name","user name " + username);
        String url = "http://coms-309-016.class.las.iastate.edu:8080/groups/delete/" + groupLeave + "/" + username;
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


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                                JSONArray jsonArray = new JSONArray(response);
//                                users = "";
                                for(int i = 0; i < jsonArray.length(); i++)
                                {
                                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                                    user = jsonObj.getString("name");
//                                  users.add(user);
                                    HasRated = jsonObj.getBoolean("hasRated");

//                                  String groupRate = name + "\n" + "Group Rating: "+ rating;
//                                    users +=  user + "\n";
                                    Log.e("user","users " + user);
//                                    members.setText(users);
//                                GroupSingleton.getInstance().setGroupName(name);

////
                                CardView cardView = createCard(user);
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

    private void postRating()
    {
        username = UsernameSingleton.getInstance().getUserName();
        String url = "http://coms-309-016.class.las.iastate.edu:8080/rating/post/" + groupNameSet + "/" + username + "/" + rateNumber;
        Log.e("rating","rate"+ url);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(GroupInformation.this, response, Toast.LENGTH_SHORT).show();
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

    private void updateRatingRequest()
    {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/updateRating/" + groupNameSet + "/" + username + "/" + newrateNumber;
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




    private CardView createCard(String nameOfUser) {
        // Create a new CardView and set up its layout parameters
//        String groupRate = users;
//        Log.e("users", "user name: " + users);
        Log.e("name of user","users " + nameOfUser);

        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                convertDpToPixels(50, this)
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
        userNameView.setGravity(Gravity.CENTER_VERTICAL);

        cardContentLayout.addView(userNameView);
        cardView.addView(cardContentLayout);


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

    private int convertDpToPixels(float dp, Context context)
    {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

}