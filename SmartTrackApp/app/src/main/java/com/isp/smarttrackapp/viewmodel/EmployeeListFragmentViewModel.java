package com.isp.smarttrackapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isp.smarttrackapp.entities.Employee;
import com.isp.smarttrackapp.model.repository.remote.EmployeesRepository;

import java.util.List;

public class EmployeeListFragmentViewModel extends AndroidViewModel {
    public EmployeeListFragmentViewModel(@NonNull Application application) {
        super(application);
    }
    private MutableLiveData<List<Employee>> employeeObservable;


    public LiveData<List<Employee>> getEmployees() {

        employeeObservable = EmployeesRepository.getInstance().getEmployees();

        return employeeObservable;
    }

}
