package com.isp.smarttrackapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isp.smarttrackapp.Config;
import com.isp.smarttrackapp.entities.Position;
import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.entities.Traject;
import com.isp.smarttrackapp.model.repository.local.LocalStorage;
import com.isp.smarttrackapp.model.repository.remote.EmployeesRepository;
import com.isp.smarttrackapp.model.repository.remote.TrajectsRepository;

public class EmployeeMapFragmentViewModel extends AndroidViewModel {

    private MutableLiveData<ResponseModelWithData<java.lang.String>> trajectCreatedId;
    private MutableLiveData<ResponseModel> locationObservable;


    public EmployeeMapFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ResponseModelWithData<java.lang.String>> createTraject(Position locationInitial){

        Traject newTraject = new Traject();
        newTraject.setLocationInitial(locationInitial);
        trajectCreatedId = TrajectsRepository.getInstance().createTraject(newTraject);
      //  LocalStorage.getInstance().setValue(trajectCreatedId.getValue().getData(), Config.KEY_ACTUAL_TRAJECT_ID);
        return trajectCreatedId;
    }

    public LiveData<ResponseModel> updateLocation(double latitude, double longitude) {

        Position positionObj = new Position();
        positionObj.setLatitude(latitude);
        positionObj.setLongitude(longitude);

        locationObservable = EmployeesRepository.getInstance().updateLocation(positionObj);

        return locationObservable;
    }

    public void endTraject(Traject traject){
        TrajectsRepository.getInstance().endTraject(traject);
    }

    public LiveData<ResponseModelWithData<java.lang.String>> getActualTrajectId(){
        return trajectCreatedId;
    }

    public String getLocalStorage(String key){
        return LocalStorage.getInstance().getValue(key);
    }

    public void setLocalStorage(String value, String key){
        LocalStorage.getInstance().setValue(value, key);
    }
}
