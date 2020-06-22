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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.isp.smarttrackapp.Config;
import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.viewmodel.MainAdminFragmentViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainAdminFragment extends Fragment {

    private Context thisContext;
    private Button btnEmployees;
    private Button btnTracking;
    private Button btnIncidetsReport;
    private Button btnTrajectsReport;
    private Button btnLogout;

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

        btnIncidetsReport = view.findViewById(R.id.am_incidents_report_btn);
        btnIncidetsReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_mainAdminFragment_to_reportsIncidentsFragment);
            }
        });

        btnTrajectsReport = view.findViewById(R.id.am_trajects_report_btn);
        btnTrajectsReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_mainAdminFragment_to_reportsTrajectsFragment);
            }
        });

        btnLogout = view.findViewById(R.id.am_logout_btn);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    adminViewModel.logout().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
                        @Override
                        public void onChanged(ResponseModel res) {
                            if(res.isResponseOK()){
                                getActivity().onBackPressed();
                            }else{
                                Toast.makeText(thisContext, res.getErrorMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }catch(Exception ex){
                    Toast.makeText(thisContext, ex.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
