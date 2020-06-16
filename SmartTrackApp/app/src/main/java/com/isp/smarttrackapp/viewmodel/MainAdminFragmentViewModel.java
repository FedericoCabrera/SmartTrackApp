package com.isp.smarttrackapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.isp.smarttrackapp.model.repository.remote.AdministratorRepository;

public class MainAdminFragmentViewModel extends AndroidViewModel {
    public MainAdminFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public void updateFCMToken(){
        AdministratorRepository.getInstance().updateFirebaseMessagingToken();
    }
}
