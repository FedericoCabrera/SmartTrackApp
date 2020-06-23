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
import com.isp.smarttrackapp.Config;
import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.viewmodel.UpdatePasswordFragmentViewModel;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdatePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdatePasswordFragment extends Fragment {
    private TextView textView;
    private String username;
    private TextInputEditText txtInputUsername;
    private TextInputEditText txtInputPassword;
    private Button btnUpdatePassword;
    private Button btnCancel;

    private Context thisContext;
    private UpdatePasswordFragmentViewModel updatePasswordViewModel;

    private NavController navController;

    public UpdatePasswordFragment() {
        // Required empty public constructor
    }

    public static UpdatePasswordFragment newInstance(String param1, String param2) {
        UpdatePasswordFragment fragment = new UpdatePasswordFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        thisContext = getActivity();
        updatePasswordViewModel = new ViewModelProvider(this).get(UpdatePasswordFragmentViewModel.class);

        return inflater.inflate(R.layout.fragment_update_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        btnUpdatePassword = view.findViewById(R.id.up_btn_updatePassword);
        btnCancel = view.findViewById(R.id.up_btn_cancel);
        txtInputUsername = view.findViewById(R.id.up_txt_input_username);
        txtInputPassword = view.findViewById(R.id.up_txt_input_password);

        username = updatePasswordViewModel.getLocalStorage(Config.KEY_USER_USERNAME);
        txtInputUsername.setText(username);
        txtInputUsername.setEnabled(false);
        textView = view.findViewById(R.id.cu_title);

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {
                getActivity().onBackPressed();
            }
        });

        btnUpdatePassword.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(final View v) {
                String password = txtInputPassword.getText().toString();

                try{
                    updatePasswordViewModel.updatePassword(password).observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
                        @Override
                        public void onChanged(ResponseModel employee) {

                            if(employee.isResponseOK()){
                                Toast.makeText(thisContext, "El empleado " +  username + " ha modificado su contraseña", Toast.LENGTH_LONG).show();
                                getActivity().onBackPressed();
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
