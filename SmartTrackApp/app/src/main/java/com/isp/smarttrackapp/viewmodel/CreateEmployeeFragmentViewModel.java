package com.isp.smarttrackapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isp.smarttrackapp.entities.Employee;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.entities.Session;
import com.isp.smarttrackapp.model.repository.remote.EmployeesRepository;

public class CreateEmployeeFragmentViewModel extends AndroidViewModel {

    private MutableLiveData<ResponseModelWithData<Employee>> employeeObservable;

    public CreateEmployeeFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ResponseModelWithData<Employee>> createEmployee(String name, String lastname, String identityNumber, String userName, String password) {

        Employee employeeObj = new Employee();
        employeeObj.setName(name);
        employeeObj.setLastName(lastname);
        employeeObj.setIdentityNumber(identityNumber);
        employeeObj.setPassword(password);
        employeeObj.setUserName(userName);

        employeeObservable = EmployeesRepository.getInstance().createEmployee(employeeObj);

        return employeeObservable;
    }
}
