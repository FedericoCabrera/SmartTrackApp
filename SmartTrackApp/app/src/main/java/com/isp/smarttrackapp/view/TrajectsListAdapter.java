package com.isp.smarttrackapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.entities.TrajectReportLine;

import java.util.ArrayList;

public class TrajectsListAdapter extends ArrayAdapter<TrajectReportLine> {
    private static final String TAG = "TrajectsListAdapter";
    private Context thisContext;
    private int thisResource;

    public TrajectsListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TrajectReportLine> objects) {
        super(context, resource, objects);
        thisContext = context;
        thisResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String startDate = getItem(position).getStartDate();
        double distance = getItem(position).getDistance();
        double duration = getItem(position).getDuration();
        String username = getItem(position).getUserName();

        LayoutInflater inflater = LayoutInflater.from(thisContext);
        convertView = inflater.inflate(thisResource, parent, false);

        TextView txtUsername = convertView.findViewById(R.id.rt_ad_txt_username);
        TextView txtDate = convertView.findViewById(R.id.rt_ad_txt_date);
        TextView txtDuration = convertView.findViewById(R.id.rt_ad_txt_duration);
        TextView txtDistance = convertView.findViewById(R.id.rt_ad_txt_distance);

        txtDuration.setText("Duración:  "+ duration+" s");
        txtDistance.setText("Distancia: " + distance+" m");
        txtDate.setText(startDate);
        txtUsername.setText(username);

        return convertView;
    }
}
