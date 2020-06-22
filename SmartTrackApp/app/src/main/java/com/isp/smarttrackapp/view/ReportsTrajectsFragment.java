package com.isp.smarttrackapp.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.isp.smarttrackapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportsTrajectsFragment extends Fragment {

    public ReportsTrajectsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reports_trajects, container, false);
    }
}
