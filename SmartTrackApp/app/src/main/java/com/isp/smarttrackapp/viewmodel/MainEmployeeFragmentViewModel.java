package com.isp.smarttrackapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isp.smarttrackapp.entities.Employee;
import com.isp.smarttrackapp.entities.Position;
import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.model.repository.remote.EmployeesRepository;

public class MainEmployeeFragmentViewModel extends AndroidViewModel {

    private MutableLiveData<ResponseModel> positionObservable;

    public MainEmployeeFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ResponseModel> updateLocation(double latitude, double longitude) {

        Position positionObj = new Position();
        positionObj.setLatitude(latitude);
        positionObj.setLongitude(longitude);

        positionObservable = EmployeesRepository.getInstance().updateLocation(positionObj);

        return positionObservable;
    }
}
