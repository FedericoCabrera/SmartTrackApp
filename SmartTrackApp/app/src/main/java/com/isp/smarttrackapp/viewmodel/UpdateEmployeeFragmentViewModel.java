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

public class UpdateEmployeeFragmentViewModel extends AndroidViewModel {

    private MutableLiveData<ResponseModel> employeeObservable;

    public UpdateEmployeeFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ResponseModel> updateEmployee(String name, String lastname, String identityNumber, String userName, String password, String id) {

        Employee employeeObj = new Employee();
        employeeObj.setId(id);
        employeeObj.setName(name);
        employeeObj.setLastName(lastname);
        employeeObj.setIdentityNumber(identityNumber);
        employeeObj.setPassword(password);
        employeeObj.setUserName(userName);

        employeeObservable = EmployeesRepository.getInstance().updateEmployee(employeeObj);

        return employeeObservable;
    }

    public String getLocalStorage(String key){
        return LocalStorage.getInstance().getValue(key);
    }

    public void setLocalStorage(String value, String key){
        LocalStorage.getInstance().setValue(value, key);
    }
}
