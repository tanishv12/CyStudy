package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JoinStudyGroupCourses extends AppCompatActivity
{

    private LinearLayout courseholder;



    private String user;
    //ArrayList<String> users = new ArrayList<String>();
//    private String users;


//    private TextView members;
    private TextView courseHeader;
    private String courseHead;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_study_group_courses);
        courseHead = CourseNameSingleton.getInstance().getCourseName();

        courseHeader = findViewById(R.id.CourseNameInfo);
        courseHeader.setText(courseHead);
    }

    private void getRequest()
    {
        String url = "http://coms-309-016.class.las.iastate.edu:8080/groups/all/users/" ;


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

//                                  String groupRate = name + "\n" + "Group Rating: "+ rating;
//                                    users +=  user + "\n";
                                Log.e("user","users " + user);
//                                    members.setText(users);
//                                GroupSingleton.getInstance().setGroupName(name);

////
                                CardView cardView = createCard(user);
////                                GroupSingleton.getInstance().setGroupName(groupName);
                                courseholder.addView(cardView);
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

    private CardView createCard(String nameOfUser) {
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
        userNameView.setGravity(Gravity.CENTER_VERTICAL);

        cardContentLayout.addView(userNameView);
        cardView.addView(cardContentLayout);
        return cardView;
    }

    private int convertDpToPixels(float dp, Context context)
    {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

}



//
//package com.example.androidexample;
//
//        import androidx.appcompat.app.AppCompatActivity;
//        import androidx.appcompat.widget.AppCompatImageView;
//        import androidx.cardview.widget.CardView;
//
//        import android.app.AlertDialog;
//        import android.content.Context;
//        import android.content.DialogInterface;
//        import android.content.Intent;
//        import android.os.Bundle;
//        import android.util.Log;
//        import android.util.TypedValue;
//        import android.view.Gravity;
//        import android.view.LayoutInflater;
//        import android.view.View;
//        import android.view.ViewGroup;
//        import android.widget.Button;
//        import android.widget.LinearLayout;
//        import android.widget.TextView;
//
//        import com.android.volley.AuthFailureError;
//        import com.android.volley.Request;
//        import com.android.volley.Response;
//        import com.android.volley.VolleyError;
//        import com.android.volley.toolbox.StringRequest;
//        import com.google.android.material.bottomsheet.BottomSheetDialog;
//
//        import org.json.JSONArray;
//        import org.json.JSONException;
//        import org.json.JSONObject;
//        import org.w3c.dom.Text;
//
//        import java.util.ArrayList;
//        import java.util.HashMap;
//        import java.util.Map;
//
//public class GroupInformation extends AppCompatActivity {
//
//    private AppCompatImageView backToGrpChat;
//
//    private AppCompatImageView optionsGrpBtn;
//
//    private LinearLayout userContainer;
//
//    private String groupNameSet;
//
//    private String user;
//    //    ArrayList<String> users = new ArrayList<String>();
//    private String users;
//    private TextView GroupHeadingName;
//
//    private Button editGroupBtn;
//
//    private Button leaveGroupBtn;
//    private Button doneButton;
//
//    private TextView members;
//
//
//    private void optionsDialog(Context c)
//    {
//        LayoutInflater inflater = LayoutInflater.from(c);
//        View dialogView = inflater.inflate(R.layout.groupoptions, null);
////        BottomSheetDialog dialog = new AlertDialog.Builder(c)
////                .setTitle("Group Options")
////                .setView(dialogView)
////                .setNegativeButton("Cancel", null)
////                .create();
////
////        buttonUpd = dialogView.findViewById(R.id.buttonUpdate);
////        buttonDel = dialogView.findViewById(R.id.buttonDelete);
////        buttonRat = dialogView.findViewById(R.id.buttonRating);
////        updateGrpName = dialogView.findViewById(R.id.updatedGroupName);
////        updateGrpName.setText(groupname);
//
//        BottomSheetDialog dialog = new BottomSheetDialog(c);
//        dialog.setTitle("Options");
//        dialog.setContentView(dialogView); // Use setContentView instead of setView
////        dialog.setNegativeButton("Done", null);
//        dialog.create();
//
//        leaveGroupBtn = dialogView.findViewById(R.id.leaveGroup);
//        editGroupBtn = dialogView.findViewById(R.id.editGrpName);
//        doneButton = dialogView.findViewById(R.id.doneBtn);
//
//        leaveGroupBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                dialog.dismiss();
//            }
//        });
//
//        editGroupBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                dialog.dismiss();
//            }
//        });
//
//        doneButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                dialog.dismiss(); // Close the dialog if needed
//            }
//        });
//
//        dialog.show();
//    }
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_group_information);
//        GroupHeadingName = findViewById(R.id.GroupHeadInfo);
//        backToGrpChat = findViewById(R.id.imageBackToGroup);
//        optionsGrpBtn = findViewById(R.id.options);
////        members = findViewById(R.id.allGrpMembers);
//        userContainer = findViewById(R.id.usersHolder);
//
//
//        groupNameSet = GroupSingleton.getInstance().getGroupName();
//
//        GroupHeadingName.setText(groupNameSet);
//
//        getRequest();
//
//        backToGrpChat.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(GroupInformation.this, MessageActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        optionsGrpBtn.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                optionsDialog(GroupInformation.this);
//            }
//        });
//
//    }
//
//    private void getRequest()
//    {
//        String url = "http://coms-309-016.class.las.iastate.edu:8080/groups/all/users/" + groupNameSet;
//
//
//        // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response)
//                    {
//                        try {
//                            JSONArray jsonArray = new JSONArray(response);
////                                users = "";
//                            for(int i = 0; i < jsonArray.length(); i++)
//                            {
//                                JSONObject jsonObj = jsonArray.getJSONObject(i);
//                                user = jsonObj.getString("name");
////                                  users.add(user);
//
////                                  String groupRate = name + "\n" + "Group Rating: "+ rating;
////                                    users +=  user + "\n";
//                                Log.e("user","users " + user);
////                                    members.setText(users);
////                                GroupSingleton.getInstance().setGroupName(name);
//
//////
//                                CardView cardView = createCard(user);
//////                                GroupSingleton.getInstance().setGroupName(groupName);
//                                userContainer.addView(cardView);
//                            }
//                        }
//                        catch (JSONException err)
//                        {
//                            Log.d("Error", err.toString());
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error)
//                    {
//
//                    }
//                }){
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                //                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
//                //                headers.put("Content-Type", "application/json");
//                return headers;
//            }
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                //                params.put("param1", "value1");
//                //                params.put("param2", "value2");
//                return params;
//            }
//        };
//
//        // Adding request to request queue
//        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
//    }
//
//    private CardView createCard(String nameOfUser) {
//        // Create a new CardView and set up its layout parameters
////        String groupRate = users;
////        Log.e("users", "user name: " + users);
//        Log.e("name of user","users " + nameOfUser);
//
//        CardView cardView = new CardView(this);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        );
//        layoutParams.setMargins(
//                convertDpToPixels(16, this),
//                convertDpToPixels(8, this),
//                convertDpToPixels(16, this),
//                convertDpToPixels(8, this)
//        );
//        cardView.setLayoutParams(layoutParams);
//
//        // Set the CardView's appearance
//        cardView.setCardElevation(convertDpToPixels(4, this));
//        cardView.setRadius(convertDpToPixels(4, this));
//
//
//        LinearLayout cardContentLayout = new LinearLayout(this);
//        cardContentLayout.setOrientation(LinearLayout.HORIZONTAL);
//        cardContentLayout.setLayoutParams(new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//        ));
//
//        // Create a TextView for the group name
//        TextView userNameView = new TextView(this);
//        userNameView.setLayoutParams(new LinearLayout.LayoutParams(
//                0,
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                1f
//        ));
//        userNameView.setText(nameOfUser);
//        userNameView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//        userNameView.setGravity(Gravity.CENTER_VERTICAL);
//
//        cardContentLayout.addView(userNameView);
//        cardView.addView(cardContentLayout);
//
//
////        cardView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view)
////            {
////                Intent intent = new Intent(StudyGroupFragment.this, MessageActivity.class);
////                GroupSingleton.getInstance().setGroupName(name);
////                startActivity(intent);
////            }
////        });
//
//
////        // Set a long-click listener on the CardView
////        cardView.setOnLongClickListener(new View.OnLongClickListener() {
////            @Override
////            public boolean onLongClick(View v) {
////                // Handle the long click event
//////                showForgotDialog(StudyGroupFragment.this);
////
////                // Return true to indicate that you have handled the long click event
////                return true;
////            }
////        });
//
//        return cardView;
//    }
//
//    private int convertDpToPixels(float dp, Context context)
//    {
//        return (int) (dp * getResources().getDisplayMetrics().density);
//    }
//
//}