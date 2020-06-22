package com.isp.smarttrackapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.model.repository.remote.AdministratorRepository;
import com.isp.smarttrackapp.model.repository.remote.LoginRepository;

public class MainAdminFragmentViewModel extends AndroidViewModel {
    private MutableLiveData<ResponseModel> loginObservable;

    public MainAdminFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public void updateFCMToken(){
        AdministratorRepository.getInstance().updateFirebaseMessagingToken();
    }

    public LiveData<ResponseModel> logout() {
        loginObservable = LoginRepository.getInstance().logout();
        return loginObservable;
    }
}
