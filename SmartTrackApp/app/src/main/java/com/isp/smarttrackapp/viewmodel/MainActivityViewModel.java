package com.isp.smarttrackapp.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.isp.smarttrackapp.model.repository.local.LocalStorage;

public class MainActivityViewModel extends AndroidViewModel {

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void initLocalStorage(Context context){
        LocalStorage.getInstance().init(context);
    }

}
