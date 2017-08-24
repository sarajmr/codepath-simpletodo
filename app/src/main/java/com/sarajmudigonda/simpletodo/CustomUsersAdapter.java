package com.sarajmudigonda.simpletodo;

/**
 * Created by Saraj.Mudigonda on 8/16/2017.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static android.graphics.Color.rgb;


public class CustomUsersAdapter extends ArrayAdapter<MainActivity.User> {
    public CustomUsersAdapter(Context context, ArrayList<MainActivity.User> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }

        // Get the data item for this position
        MainActivity.User user = getItem(position);

        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        TextView tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);
        // Populate the data into the template view using the data object
        tvName.setText(user.getName());
        tvDate.setText(user.getDate());
        tvPriority.setText(user.getPriority());
        if (user.getPriority().equalsIgnoreCase("HIGH")) {
            tvPriority.setTextColor(Color.RED);
        } else if (user.getPriority().equalsIgnoreCase("MEDIUM")) {
            tvPriority.setTextColor(rgb(255, 140, 0));
        } else {
            tvPriority.setTextColor(rgb(0, 128, 0));
        }
        // Return the completed view to render on screen
        return convertView;
    }
}