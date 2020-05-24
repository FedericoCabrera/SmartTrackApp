package com.isp.smarttrackapp.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.model.entities.Employee;
import com.isp.smarttrackapp.model.entities.Session;
import com.isp.smarttrackapp.model.repository.local.LocalStorage;
import com.isp.smarttrackapp.viewmodel.EmployeeListFragmentViewModel;
import com.isp.smarttrackapp.viewmodel.LoginFragmentViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeesListFragment extends Fragment {

    private Context thisContext;
    private TextView textView;

    private EmployeeListFragmentViewModel employeesViewModel;
    public EmployeesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        employeesViewModel = new ViewModelProvider(this).get(EmployeeListFragmentViewModel.class);
        return inflater.inflate(R.layout.fragment_employees_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.el_employeesList);
        try{
            employeesViewModel.getEmployees().observe(getViewLifecycleOwner(), new Observer<List<Employee>>() {
                @Override
                public void onChanged(List<Employee> employees) {

                   textView.setText(employees.get(0).getUserName());
                    //Toast.makeText(thisContext, , Toast.LENGTH_LONG).show();
                }
            });

        }catch(Exception ex){
            Toast.makeText(thisContext, ex.toString(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        thisContext=context;
    }
}
