package com.isp.smarttrackapp.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.isp.smarttrackapp.model.repository.local.LocalStorage;
import com.isp.smarttrackapp.model.repository.remote.FMCService;

public class MainActivityViewModel extends AndroidViewModel {

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void initLocalStorage(Context context){
        LocalStorage.getInstance().init(context);
    }

    public void initFCMToken(){
        String token = FMCService.getFCMToken();
        LocalStorage.getInstance().setValue(token,"fcm_token");
    }
}
