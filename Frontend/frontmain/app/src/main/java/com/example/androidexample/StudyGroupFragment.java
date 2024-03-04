package com.example.androidexample;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudyGroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudyGroupFragment extends AppCompatActivity {

    private String url = "http://coms-309-016.class.las.iastate.edu:8080/groups/all";

    //group_id   course_id

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StudyGroupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudyGroupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudyGroupFragment newInstance(String param1, String param2) {
        StudyGroupFragment fragment = new StudyGroupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_study_group);

        Button studyGroupsToClasses = (Button) findViewById(R.id.backbtn2);
        studyGroupsToClasses.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(StudyGroupFragment.this, MainActivity.class);
                startActivity(intent);
            }
        });






//        etUrl = findViewById(R.id.etUrl);
//        etRequest = findViewById(R.id.etRequest);
//        tvResponse = findViewById(R.id.tvResponse);
//        btnSend = findViewById(R.id.sendBtn);

    }



//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }


    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_study_group, container, false);
//    }
