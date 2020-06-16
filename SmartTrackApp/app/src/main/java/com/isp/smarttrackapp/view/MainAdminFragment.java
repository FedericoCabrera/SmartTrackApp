package com.isp.smarttrackapp.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.isp.smarttrackapp.Config;
import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.model.repository.local.LocalStorage;
import com.isp.smarttrackapp.viewmodel.MainAdminFragmentViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainAdminFragment extends Fragment {

    private Context thisContext;
    private Button btnEmployees;
    private Button btnTracking;

    private MainAdminFragmentViewModel adminViewModel;
    private NavController navController;


    public MainAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adminViewModel = new ViewModelProvider(this).get(MainAdminFragmentViewModel.class);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_admin, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        thisContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        String token = LocalStorage.getInstance().getValue(Config.KEY_USER_TOKEN);

        Toast.makeText(thisContext, "Admin" + LocalStorage.getInstance().getValue(Config.KEY_USER_USERNAME) +" ingresado con Token: " + token, Toast.LENGTH_LONG).show();

        adminViewModel.updateFCMToken();

        btnEmployees = view.findViewById(R.id.am_employees_btn);
        btnEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_mainAdminFragment_to_employeesListFragment);
            }
        });

        btnTracking = view.findViewById(R.id.am_tracking);
        btnTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_mainAdminFragment_to_adminMapFragment);
            }
        });
    }
}
