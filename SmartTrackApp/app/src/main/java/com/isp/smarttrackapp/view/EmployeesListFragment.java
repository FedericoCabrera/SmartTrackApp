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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.entities.Employee;
import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.viewmodel.EmployeeListFragmentViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeesListFragment extends Fragment {

    private Context thisContext;
    private NavController navController;
    private ListView listView;
    private Employee employeeSelected;
    private Button btnAddEmployee;
    private Button btnRemoveEmployee;
    private Button btnUpdateEmployee;

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
        employeeSelected = null;
        navController = Navigation.findNavController(view);
        btnAddEmployee = view.findViewById(R.id.el_btn_addEmployee);
        btnRemoveEmployee = view.findViewById(R.id.el_btn_removeEmployee);
        btnUpdateEmployee = view.findViewById(R.id.el_btn_editEmployee);
        try{
            employeesViewModel.getEmployees().observe(getViewLifecycleOwner(), new Observer<ResponseModelWithData<List<Employee>>>() {
                @Override
                public void onChanged(ResponseModelWithData<List<Employee>> employees) {
                    ArrayAdapter<Employee> arrayAdapter = new ArrayAdapter<Employee>(thisContext, android.R.layout.simple_list_item_1, employees.getData() );
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                employeeSelected = (Employee)(listView.getItemAtPosition(myItemInt));
            }
        });

        btnRemoveEmployee.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(final View v) {
                try{
                    if(employeeSelected != null){
                        employeesViewModel.removeEmployee(employeeSelected).observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
                            @Override
                            public void onChanged(ResponseModel res) {

                                if(res.isResponseOK()){
                                    Toast.makeText(thisContext, "El empleado " + employeeSelected.getName() + " ha sido eliminado", Toast.LENGTH_LONG).show();
                                    employeesViewModel.getEmployees().observe(getViewLifecycleOwner(), new Observer<ResponseModelWithData<List<Employee>>>() {
                                        @Override
                                        public void onChanged(ResponseModelWithData<List<Employee>> employees) {
                                            ArrayAdapter<Employee> arrayAdapter = new ArrayAdapter<Employee>(thisContext, android.R.layout.simple_list_item_1, employees.getData() );
                                            listView.setAdapter(arrayAdapter);
                                        }
                                    });
                                }else{
                                    Toast.makeText(thisContext, res.getErrorMessage(), Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                    }
                }catch(Exception ex){
                    Toast.makeText(thisContext, ex.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });

        btnUpdateEmployee.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {
                try{
                    if(employeeSelected != null){
                        employeesViewModel.setLocalStorage(employeeSelected.getId(),"idEmployeeForUpdate");
                        employeesViewModel.setLocalStorage(employeeSelected.getIdentityNumber(),"identityNumberEmployeeForUpdate");
                        employeesViewModel.setLocalStorage(employeeSelected.getLastName(),"lastnameEmployeeForUpdate");
                        employeesViewModel.setLocalStorage(employeeSelected.getName(),"nameEmployeeForUpdate");
                        employeesViewModel.setLocalStorage(employeeSelected.getPassword(),"passwordEmployeeForUpdate");
                        employeesViewModel.setLocalStorage(employeeSelected.getUserName(),"usernameEmployeeForUpdate");

                        navController.navigate(R.id.action_employeesListFragment_to_updateEmployeeFragment);
                    }
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
