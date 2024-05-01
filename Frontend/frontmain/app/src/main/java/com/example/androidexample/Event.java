package com.example.androidexample;

import android.util.Log;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event
{
    public static ArrayList<Event> eventsList = new ArrayList<>();

    public static ArrayList<Event> eventsForDate(LocalDate date)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventsList)
        {
            if(event.getDate().equals(date))
                events.add(event);
        }

        return events;
    }


    private String name;
    private LocalDate date;
    private LocalTime time;
    private int duration; // New field for duration

    public Event(String name, LocalDate date, LocalTime time, int duration)
    {
        Log.e("date: ", date.toString());
        this.name = name;
        this.date = date;
        this.time = time;
        this.duration = duration;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public LocalTime getTime()
    {
        return time;
    }

    public void setTime(LocalTime time)
    {
        this.time = time;
    }

    public Integer getDuration() {return duration;}

    public void setDuration(int duration) {this.duration = duration;}
}