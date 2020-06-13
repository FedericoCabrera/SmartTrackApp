package com.isp.smarttrackapp.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.entities.Employee;
import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.model.repository.local.LocalStorage;
import com.isp.smarttrackapp.viewmodel.UpdateEmployeeFragmentViewModel;
import com.isp.smarttrackapp.viewmodel.ValuesViewModel;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateEmployeeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateEmployeeFragment extends Fragment {
    private TextView textView;
    private String employeeId;
    private TextInputEditText txtInputName;
    private TextInputEditText txtInputLastname;
    private TextInputEditText txtInputIdentityNumber;
    private TextInputEditText txtInputUsername;
    private TextInputEditText txtInputPassword;
    private Button btnUpdateEmployee;
    private Button btnCancel;

    private Context thisContext;
    private ValuesViewModel valuesViewModel;
    private UpdateEmployeeFragmentViewModel updateEmployeeViewModel;

    private NavController navController;

    public UpdateEmployeeFragment() {
        // Required empty public constructor
    }

    public static UpdateEmployeeFragment newInstance(String param1, String param2) {
        UpdateEmployeeFragment fragment = new UpdateEmployeeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        thisContext = getActivity();
        updateEmployeeViewModel = new ViewModelProvider(this).get(UpdateEmployeeFragmentViewModel.class);

        return inflater.inflate(R.layout.fragment_update_employee, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        btnUpdateEmployee = view.findViewById(R.id.uu_btn_updateEmployee);
        btnCancel = view.findViewById(R.id.uu_btn_cancel);
        txtInputName = view.findViewById(R.id.uu_txt_input_name);
        txtInputLastname = view.findViewById(R.id.uu_txt_input_lastname);
        txtInputIdentityNumber = view.findViewById(R.id.uu_txt_input_identityNumber);
        txtInputUsername = view.findViewById(R.id.uu_txt_input_username);
        txtInputPassword = view.findViewById(R.id.uu_txt_input_password);

        LocalStorage localStorage = LocalStorage.getInstance();
        txtInputName.setText(localStorage.getValue("nameEmployeeForUpdate"));
        txtInputLastname.setText(localStorage.getValue("lastnameEmployeeForUpdate"));
        txtInputUsername.setText(localStorage.getValue("usernameEmployeeForUpdate"));
        txtInputPassword.setText(localStorage.getValue("passwordEmployeeForUpdate"));
        txtInputIdentityNumber.setText(localStorage.getValue("identityNumberEmployeeForUpdate"));
        employeeId = localStorage.getValue("idEmployeeForUpdate");
        textView = view.findViewById(R.id.cu_title);

        btnUpdateEmployee.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(final View v) {
                String name = txtInputName.getText().toString();
                String lastname = txtInputLastname.getText().toString();
                String identityNumber = txtInputIdentityNumber.getText().toString();
                String userName = txtInputUsername.getText().toString();
                String password = txtInputPassword.getText().toString();

                try{
                    updateEmployeeViewModel.updateEmployee(name, lastname, identityNumber, userName, password, employeeId).observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
                        @Override
                        public void onChanged(ResponseModel employee) {

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
