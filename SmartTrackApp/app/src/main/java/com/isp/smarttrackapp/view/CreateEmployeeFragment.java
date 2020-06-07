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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.entities.Employee;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.entities.Session;
import com.isp.smarttrackapp.model.repository.local.LocalStorage;
import com.isp.smarttrackapp.viewmodel.CreateEmployeeFragmentViewModel;
import com.isp.smarttrackapp.viewmodel.ValuesViewModel;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateEmployeeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEmployeeFragment extends Fragment {
    private TextView textView;
    private TextInputEditText txtInputName;
    private TextInputEditText txtInputLastname;
    private TextInputEditText txtInputIdentityNumber;
    private TextInputEditText txtInputUsername;
    private TextInputEditText txtInputPassword;
    private Button btnAddEmployee;
    private Button btnCancel;

    private Context thisContext;
    private ValuesViewModel valuesViewModel;
    private CreateEmployeeFragmentViewModel createEmployeeViewModel;

    private NavController navController;

    public CreateEmployeeFragment() {
        // Required empty public constructor
    }

    public static CreateEmployeeFragment newInstance(String param1, String param2) {
        CreateEmployeeFragment fragment = new CreateEmployeeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        thisContext = getActivity();
        createEmployeeViewModel = new ViewModelProvider(this).get(CreateEmployeeFragmentViewModel.class);

        return inflater.inflate(R.layout.fragment_create_employee, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        btnAddEmployee = view.findViewById(R.id.cu_btn_addEmployee);
        btnCancel = view.findViewById(R.id.cu_btn_cancel);
        txtInputName = view.findViewById(R.id.cu_txt_input_name);
        txtInputLastname = view.findViewById(R.id.cu_txt_input_lastname);
        txtInputIdentityNumber = view.findViewById(R.id.cu_txt_input_identityNumber);
        txtInputUsername = view.findViewById(R.id.cu_txt_input_username);
        txtInputPassword = view.findViewById(R.id.cu_txt_input_password);

        textView = view.findViewById(R.id.cu_title);

        btnAddEmployee.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(final View v) {
                String name = txtInputName.getText().toString();
                String lastname = txtInputLastname.getText().toString();
                String identityNumber = txtInputIdentityNumber.getText().toString();
                String userName = txtInputUsername.getText().toString();
                String password = txtInputPassword.getText().toString();

                try{
                    createEmployeeViewModel.createEmployee(name, lastname, identityNumber, userName, password).observe(getViewLifecycleOwner(), new Observer<ResponseModelWithData<Employee>>() {
                        @Override
                        public void onChanged(ResponseModelWithData<Employee> employee) {

                            if(employee.isResponseOK()){
                                //Toast.makeText(thisContext, session.getData().getToken() + " Admin: " + session.getData().getIsAdmin(), Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(thisContext, employee.getErrorMessage(), Toast.LENGTH_LONG).show();
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
    public void onAttach(Context context) {
        super.onAttach(context);
        thisContext = context;
    }
}
