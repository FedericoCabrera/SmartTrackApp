package com.isp.smarttrackapp.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.viewmodel.CreateTrajectFragmentViewModel;
import com.isp.smarttrackapp.viewmodel.LoginFragmentViewModel;

public class CreateTrajectFragment extends Fragment{

    private Context thisContext;
    private CreateTrajectFragmentViewModel createTrajectFragmentViewModel;
    public CreateTrajectFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        thisContext = getActivity();
        return inflater.inflate(R.layout.fragment_create_traject, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        thisContext = context;
    }
}
