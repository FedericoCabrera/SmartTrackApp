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
import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.entities.Session;
import com.isp.smarttrackapp.entities.Value;
import com.isp.smarttrackapp.model.repository.local.LocalStorage;
import com.isp.smarttrackapp.viewmodel.LoginFragmentViewModel;
import com.isp.smarttrackapp.viewmodel.ValuesViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private TextView textView;
    private TextInputEditText txtInputUser;
    private TextInputEditText txtInputPassword;
    private Button btnLogin;
    private Button btnCancelar;

    private Context thisContext;
    private ValuesViewModel valuesViewModel;
    private LoginFragmentViewModel loginViewModel;

    private NavController navController;

    public LoginFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        thisContext = getActivity();
        //valuesViewModel = new ViewModelProvider(this).get(ValuesViewModel.class);
        loginViewModel = new ViewModelProvider(this).get(LoginFragmentViewModel.class);


        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        btnLogin = view.findViewById(R.id.li_btn_login);
        btnCancelar = view.findViewById(R.id.li_btn_cancel);
        txtInputUser = view.findViewById(R.id.li_txt_input_username);
        txtInputPassword = view.findViewById(R.id.li_txt_input_password);

        textView = view.findViewById(R.id.li_title);

        //this.observeViewModel(this.valuesViewModel);

        btnLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(final View v) {
                String userName = txtInputUser.getText().toString();
                String password = txtInputPassword.getText().toString();

                try{
                    loginViewModel.login(userName, password).observe(getViewLifecycleOwner(), new Observer<ResponseModelWithData<Session>>() {
                        @Override
                        public void onChanged(ResponseModelWithData<Session> session) {

                            if(session.isResponseOK()){
                                //Toast.makeText(thisContext, session.getData().getToken() + " Admin: " + session.getData().getIsAdmin(), Toast.LENGTH_LONG).show();

                                String userName = session.getData().getUsername();
                                String token = session.getData().getToken();
                                String userRealName = session.getData().getName();

                                loginViewModel.updateUserLocalData(userRealName, userName, token);

                                if (session.getData().getIsAdmin()) {
                                    navController.navigate(R.id.action_loginFragment_to_mainAdminFragment);
                                } else {
                                    navController.navigate(R.id.action_loginFragment_to_mainEmployeeFragment);
                                    //navController.navigate(R.id.action_loginFragment_to_mapsActivity);
                                }

                            }else{
                                Toast.makeText(thisContext, session.getErrorMessage(), Toast.LENGTH_LONG).show();
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

    // TODO: Delete
    private void observeViewModel(ValuesViewModel valuesViewModel) {

        valuesViewModel.getValuesListObservable().observe(getViewLifecycleOwner(), new Observer<List<Value>>() {
            @Override
            public void onChanged(List<Value> values) {
                if (values != null) {
                    for (Value s : values) {
                        Toast.makeText(thisContext, s.getV1() + " " + s.getV2(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

}
