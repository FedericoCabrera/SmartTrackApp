package com.isp.smarttrackapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.isp.smarttrackapp.model.repository.remote.FMCService;

public class MainFragmentViewModel extends AndroidViewModel {
    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public void initFCMToken(){
        FMCService.getInstance().initFCMToken();
    }
}
