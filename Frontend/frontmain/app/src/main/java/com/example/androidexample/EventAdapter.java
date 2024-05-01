package com.example.androidexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EventAdapter extends ArrayAdapter<Event>
{
    public EventAdapter() {
        super(null, 0, new ArrayList<>());
    }
    public EventAdapter(@NonNull Context context, List<Event> events)
    {
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Event event = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);

        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);

        String startTime = CalendarUtils.formattedTime(event.getTime());
        String endTime = CalendarUtils.formattedTime(event.getTime().plusHours(event.getDuration()));

        String eventTitle = event.getName() + " " + startTime + "-" + endTime;
        eventCellTV.setText(eventTitle);
        return convertView;
    }

    public static String formattedTime(LocalTime time) {
        // Format the time without seconds and leading zeros
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
        return time.format(formatter);
    }
}