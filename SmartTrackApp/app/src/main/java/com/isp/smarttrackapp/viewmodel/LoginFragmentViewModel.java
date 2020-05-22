package com.isp.smarttrackapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isp.smarttrackapp.model.entities.Login;
import com.isp.smarttrackapp.model.entities.Session;
import com.isp.smarttrackapp.model.repository.remote.LoginRepository;

public class LoginFragmentViewModel extends AndroidViewModel {

    private MutableLiveData<Session> sessionObservable;

    public LoginFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Session> login(String userName, String password) {

        Login loginObj = new Login();
        loginObj.setPassword(password);
        loginObj.setUserName(userName);

        sessionObservable = LoginRepository.getInstance().login(loginObj);

        return sessionObservable;
    }
}
