package com.isp.smarttrackapp.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.entities.Employee;
import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.viewmodel.EmployeeListFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeesListFragment extends Fragment {

    private Context thisContext;
    private NavController navController;
    private ListView listView;
    private Button btnAddEmployee;
<<<<<<< HEAD
    private NavController navController;
=======
    private Button btnRemoveEmployee;
>>>>>>> 200c51ca69577ce6301b71e5590aac30997cf438

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
        navController = Navigation.findNavController(view);
        listView = view.findViewById(R.id.el_employeesList);
<<<<<<< HEAD
        navController = Navigation.findNavController(view);
        btnAddEmployee = view.findViewById(R.id.el_btn_addEmployee);
        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_employeesListFragment_to_createUserFragment);
            }
        });
=======
        btnAddEmployee = view.findViewById(R.id.el_btn_addEmployee);
        btnRemoveEmployee = view.findViewById(R.id.el_btn_removeEmployee);
>>>>>>> 200c51ca69577ce6301b71e5590aac30997cf438
        try{
            employeesViewModel.getEmployees().observe(getViewLifecycleOwner(), new Observer<ResponseModelWithData<List<Employee>>>() {
                @Override
                public void onChanged(ResponseModelWithData<List<Employee>> employees) {
                    List<String> users = new ArrayList<>();
                    for (Employee e: employees.getData()) {
                        users.add(e.getUserName());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(thisContext, android.R.layout.simple_list_item_1, users );
                    listView.setAdapter(arrayAdapter);
                }
            });

        }catch(Exception ex){
            Toast.makeText(thisContext, ex.toString(), Toast.LENGTH_LONG).show();
        }


        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_employeesListFragment_to_createEmployeeFragment);
            }
        });

        btnRemoveEmployee.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(final View v) {
                try{
                    Employee employee = ((Employee) listView.getSelectedItem());
                    employeesViewModel.removeEmployee(employee).observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
                        @Override
                        public void onChanged(ResponseModel res) {

                            if(res.isResponseOK()){
                                //Toast.makeText(thisContext, session.getData().getToken() + " Admin: " + session.getData().getIsAdmin(), Toast.LENGTH_LONG).show();

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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        thisContext=context;
    }
}
