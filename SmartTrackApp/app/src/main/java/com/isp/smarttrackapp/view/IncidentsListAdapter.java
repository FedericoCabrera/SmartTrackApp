package com.isp.smarttrackapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.entities.IncidentReport;
import com.isp.smarttrackapp.utils.Utils;

import java.util.ArrayList;

public class IncidentsListAdapter extends ArrayAdapter<IncidentReport> {
    private static final String TAG = "IncidentsListAdapter";
    private Context thisContext;
    private int thisResource;


    public IncidentsListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<IncidentReport> objects) {
        super(context, resource, objects);
        thisContext = context;
        thisResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String base64Image = getItem(position).getBase64Image();
        String description = getItem(position).getDescription();
        String address = getItem(position).getAddress();
        String date = getItem(position).getDate();
        String username = getItem(position).getUserName();

        LayoutInflater inflater = LayoutInflater.from(thisContext);
        convertView = inflater.inflate(thisResource, parent, false);

        ImageView imgImage = convertView.findViewById(R.id.ri_ad_img_image);
        TextView txtUsername = convertView.findViewById(R.id.ri_ad_txt_username);
        TextView txtAddress = convertView.findViewById(R.id.ri_ad_txt_address);
        TextView txtDate = convertView.findViewById(R.id.ri_ad_txt_date);
        TextView txtDescription = convertView.findViewById(R.id.ri_ad_txt_description);

        if(!base64Image.equals("")){
            try{
                Bitmap img = Utils.decodeBase64ToBitmap(base64Image);
                imgImage.setImageBitmap(img);
            }catch (Exception e){
                Drawable drawable = getContext().getResources().getDrawable(R.drawable.ic_crop_free_24px);
                imgImage.setImageDrawable(drawable);
            }
        }else{
            Drawable drawable = getContext().getResources().getDrawable(R.drawable.ic_crop_free_24px);
            imgImage.setImageDrawable(drawable);
        }

        txtDescription.setText(description);
        txtAddress.setText(address);
        txtDate.setText(date);
        txtUsername.setText(username);

        return convertView;
    }
}
