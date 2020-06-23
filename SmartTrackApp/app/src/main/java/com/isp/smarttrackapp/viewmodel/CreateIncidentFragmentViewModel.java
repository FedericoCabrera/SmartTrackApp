package com.isp.smarttrackapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isp.smarttrackapp.Config;
import com.isp.smarttrackapp.entities.Incident;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.model.repository.local.LocalStorage;
import com.isp.smarttrackapp.model.repository.remote.TrajectsRepository;

public class CreateIncidentFragmentViewModel extends AndroidViewModel {


    private MutableLiveData<ResponseModelWithData<String>> incidentCreatedId;

    public CreateIncidentFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ResponseModelWithData<String>> assignIncidentToTraject(Incident incident){

        incidentCreatedId = TrajectsRepository.getInstance().assignIncidentToTraject(incident);

        return incidentCreatedId;
    }

    public String getLastLatitudeValue(){
        return LocalStorage.getInstance().getValue(Config.KEY_LAST_LATITUDE);
    }

    public String getLastLongitudeValue(){
        return LocalStorage.getInstance().getValue(Config.KEY_LAST_LONGITUDE);
    }
}
