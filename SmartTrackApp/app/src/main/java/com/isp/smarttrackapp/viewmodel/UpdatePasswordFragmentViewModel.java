package com.isp.smarttrackapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isp.smarttrackapp.entities.Employee;
import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.model.repository.local.LocalStorage;
import com.isp.smarttrackapp.model.repository.remote.EmployeesRepository;

public class UpdatePasswordFragmentViewModel extends AndroidViewModel {

    private MutableLiveData<ResponseModel> employeeObservable;

    public UpdatePasswordFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ResponseModel> updatePassword(String password) {

        employeeObservable = EmployeesRepository.getInstance().updatePassword(password);

        return employeeObservable;
    }

    public String getLocalStorage(String key){
        return LocalStorage.getInstance().getValue(key);
    }
}
