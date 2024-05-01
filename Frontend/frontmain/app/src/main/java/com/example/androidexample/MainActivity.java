package com.example.androidexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//import com.example.androidexample.databinding.ActivityMainBinding;
import static com.example.androidexample.CalendarUtils.daysInMonthArray;
import static com.example.androidexample.CalendarUtils.daysInWeekArray;
import static com.example.androidexample.CalendarUtils.monthYearFromDate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Home page for study group application.
 *
 * Redirects user to classes page, study group page, or back to homepage.
 */
public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private Button b1;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
//    ActivityMainBinding binding;

    /**
     * Creates home page UI
     * @param savedInstanceState Stores information needed to reload UI on system crashes
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalendarUtils.selectedDate = LocalDate.now();
        initWidgets();
        setWeekView();

        getRequest();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavbar);
        bottomNavigationView.setSelectedItemId(R.id.Home);

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
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    private void setWeekView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

        // Set event adapter for the ListView
        setEventAdapter();
    }

    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            setWeekView();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdapter();
    }

    /**
     * Sets the events for the day when clicked
     */
    private void setEventAdapter()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    /**
     * Starts
     * @param view
     */
    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }

    public void weeklyAction(View view)
    {
        startActivity(new Intent(this, WeekViewActivity.class));
    }

    private void getRequest() {
        String name = UsernameSingleton.getInstance().getUserName();
        String url = "http://coms-309-016.class.las.iastate.edu:8080/timings/user/" + name;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            Event.eventsList.clear(); // Clear the list before adding new events
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObj = jsonArray.getJSONObject(i);
                                String location = jsonObj.getString("location");
                                LocalDate date = LocalDate.parse(jsonObj.getString("date"));
                                LocalTime time = LocalTime.parse(jsonObj.getString("startTime"));
                                int duration = jsonObj.getInt("duration");
                                JSONObject groupObject = jsonObj.getJSONObject("group");
                                String groupName = groupObject.getString("groupName");

                                Event newEvent = new Event(location, groupName, date, time, duration);
                                Event.eventsList.add(newEvent);
                            }
                            setEventAdapter(); // Refresh the event adapter after updating the list
                        } catch (JSONException err) {
                            Log.e("Error", err.toString());
                            // Handle JSON parsing error
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                        // Handle Volley error (e.g., network failure)
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                // Add any required headers
                return headers;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


}