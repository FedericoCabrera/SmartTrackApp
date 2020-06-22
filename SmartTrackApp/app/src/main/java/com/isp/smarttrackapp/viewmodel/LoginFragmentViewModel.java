package com.isp.smarttrackapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isp.smarttrackapp.Config;
import com.isp.smarttrackapp.entities.Login;
import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.entities.Session;
import com.isp.smarttrackapp.model.repository.local.LocalStorage;
import com.isp.smarttrackapp.model.repository.remote.AdministratorRepository;
import com.isp.smarttrackapp.model.repository.remote.LoginRepository;

public class LoginFragmentViewModel extends AndroidViewModel {

    private MutableLiveData<ResponseModelWithData<Session>> sessionObservable;

    public LoginFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ResponseModelWithData<Session>> login(String userName, String password) {

        Login loginObj = new Login();
        loginObj.setPassword(password);
        loginObj.setUserName(userName);

        sessionObservable = LoginRepository.getInstance().login(loginObj);

        return sessionObservable;
    }

    public void updateUserLocalData(String name, String userName, String token, String id){
        LocalStorage.getInstance().setValue(Config.KEY_USER_TOKEN, token);
        LocalStorage.getInstance().setValue(Config.KEY_USER_REALNAME, name);
        LocalStorage.getInstance().setValue(Config.KEY_USER_USERNAME, userName);
        LocalStorage.getInstance().setValue(Config.KEY_USER_ID, id);
    }

    public void updateFCMToken(){
        AdministratorRepository.getInstance().updateFirebaseMessagingToken();
    }

    public String getLocalStorage(String key){
        return LocalStorage.getInstance().getValue(key);
    }

    public void setLocalStorage(String value, String key){
        LocalStorage.getInstance().setValue(value, key);
    }
}
