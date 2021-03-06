package com.isp.smarttrackapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isp.smarttrackapp.entities.Employee;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.model.repository.remote.EmployeesRepository;

import java.util.List;

public class AdminMapFragmentViewModel  extends AndroidViewModel {


    public AdminMapFragmentViewModel(@NonNull Application application) {
        super(application);
    }
    private MutableLiveData<ResponseModelWithData<List<Employee>>> employeeObservable;

    public LiveData<ResponseModelWithData<List<Employee>>> getLocation() {

        employeeObservable = EmployeesRepository.getInstance().getLocation();

        return employeeObservable;
    }
}



