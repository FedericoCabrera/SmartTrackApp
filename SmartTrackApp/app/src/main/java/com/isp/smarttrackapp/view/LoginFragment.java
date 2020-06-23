package com.isp.smarttrackapp.view;

import android.Manifest;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import androidx.biometric.BiometricPrompt;

import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.isp.smarttrackapp.Config;
import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.entities.Session;
import com.isp.smarttrackapp.entities.Value;
import com.isp.smarttrackapp.viewmodel.LoginFragmentViewModel;
import com.isp.smarttrackapp.viewmodel.ValuesViewModel;

import java.util.List;
import java.util.concurrent.Executor;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private TextView textView;
    private TextInputEditText txtInputUser;
    private TextInputEditText txtInputPassword;
    private Button btnLogin;
    private Context thisContext;
    private LoginFragmentViewModel loginViewModel;
    private CheckBox cbAuthFingerprint;
    private NavController navController;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    public LoginFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        thisContext = getActivity();
        loginViewModel = new ViewModelProvider(this).get(LoginFragmentViewModel.class);

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        btnLogin = view.findViewById(R.id.li_btn_login);
        txtInputUser = view.findViewById(R.id.li_txt_input_username);
        txtInputPassword = view.findViewById(R.id.li_txt_input_password);
        cbAuthFingerprint = view.findViewById(R.id.li_cb_auth_fingerprint);

        textView = view.findViewById(R.id.li_title);

        btnLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(final View v) {
                String userName = txtInputUser.getText().toString();
                String password = txtInputPassword.getText().toString();
                loginViewModel.setLocalStorage(userName, Config.KEY_USER_USERNAME);

                if(cbAuthFingerprint.isChecked()) {
                    loginViewModel.setLocalStorage(userName, Config.KEY_AUTH_USERNAME);
                    loginViewModel.setLocalStorage(password, Config.KEY_AUTH_PASSWORD);
                    loginViewModel.setLocalStorage("true", Config.KEY_AUTH_FINGERPRINT);
                }else{
                    loginViewModel.setLocalStorage("false", Config.KEY_AUTH_FINGERPRINT);
                }
                try{
                    loginViewModel.login(userName, password).observe(getViewLifecycleOwner(), new Observer<ResponseModelWithData<Session>>() {
                        @Override
                        public void onChanged(ResponseModelWithData<Session> session) {

                            if(session.isResponseOK()){
                                String userName = session.getData().getUsername();
                                String token = session.getData().getToken();
                                String userRealName = session.getData().getName();
                                String userId = session.getData().getUserId();

                                loginViewModel.updateUserLocalData(userRealName, userName, token, userId);

                                if (session.getData().getIsAdmin()) {
                                    navController.navigate(R.id.action_loginFragment_to_mainAdminFragment);
                                } else {
                                    navController.navigate(R.id.action_loginFragment_to_mainEmployeeFragment);
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

        executor = ContextCompat.getMainExecutor(thisContext);
        biometricPrompt = new BiometricPrompt((FragmentActivity) thisContext, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,@NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(thisContext,"Error al autenticar: " + errString, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                String authUsername = loginViewModel.getLocalStorage(Config.KEY_AUTH_USERNAME);
                String authPassword = loginViewModel.getLocalStorage(Config.KEY_AUTH_PASSWORD);

                try{
                    loginViewModel.login(authUsername, authPassword).observe(getViewLifecycleOwner(), new Observer<ResponseModelWithData<Session>>() {
                        @Override
                        public void onChanged(ResponseModelWithData<Session> session) {

                            if(session.isResponseOK()){
                                String userName = session.getData().getUsername();
                                String token = session.getData().getToken();
                                String userRealName = session.getData().getName();
                                String userId = session.getData().getUserId();

                                loginViewModel.updateUserLocalData(userRealName, userName, token, userId);

                                if (session.getData().getIsAdmin()) {
                                    navController.navigate(R.id.action_loginFragment_to_mainAdminFragment);
                                } else {
                                    navController.navigate(R.id.action_loginFragment_to_mainEmployeeFragment);
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
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(thisContext, "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login por Autenticación con Huella")
                .setSubtitle("Credenciales Dactilares")
                .setNegativeButtonText("Usar Usuario y Contraseña")
                .build();

        if(loginViewModel.getLocalStorage(Config.KEY_AUTH_FINGERPRINT) != null && loginViewModel.getLocalStorage(Config.KEY_AUTH_FINGERPRINT).equals("true")){
            biometricPrompt.authenticate(promptInfo);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        thisContext = context;
    }

    private boolean authenticateFingerprint() {
        if(statusFingerprint()) {
            if (ActivityCompat.checkSelfPermission(thisContext, Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this.getActivity(), new String[]{
                        Manifest.permission.USE_BIOMETRIC
                }, 1000);
                if (ActivityCompat.checkSelfPermission(thisContext, Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(thisContext, "Asignar permisos para uso de huella.", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }
        }
        Toast.makeText(thisContext, "La autenticación por huella esta apagada.", Toast.LENGTH_LONG).show();
        return false;
    }
    private boolean statusFingerprint() {
        KeyguardManager km = (KeyguardManager) thisContext.getSystemService(Context.KEYGUARD_SERVICE);
        if (!km.isKeyguardSecure()){
            return false;
        }
        PackageManager packageManager = thisContext.getPackageManager();
        if(!packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            Toast.makeText(thisContext, "El dispositivo no tiene huella digital.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
