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


/*

        employeeObservable = EmployeesRepository.getInstance().createEmployee(employeeObj);

        return employeeObservable;

        ResponseModelWithData<java.lang.String> t = new ResponseModelWithData<>();
        t.setData("891AD943-B35F-437C-BF62-05BDA8EF1548");
        t.setResponseOK(true);

        MutableLiveData<ResponseModelWithData<java.lang.String>> newLiveData = new MutableLiveData<>();
        newLiveData.setValue(t);

        LocalStorage.getInstance().setValue("891AD943-B35F-437C-BF62-05BDA8EF1548", Config.KEY_ACTUAL_TRAJECT_ID);

        return newLiveData;
        */
    }









    public LiveData<ResponseModelWithData<java.lang.String>> getActualTrajectId(){
        return trajectCreatedId;
    }
}
