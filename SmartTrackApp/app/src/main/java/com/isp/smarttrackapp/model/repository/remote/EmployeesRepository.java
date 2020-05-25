package com.isp.smarttrackapp.model.repository.remote;

import androidx.lifecycle.MutableLiveData;

import com.isp.smarttrackapp.model.entities.Employee;
import com.isp.smarttrackapp.model.entities.Login;
import com.isp.smarttrackapp.model.entities.Session;
import com.isp.smarttrackapp.model.repository.local.LocalStorage;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EmployeesRepository {
    private IEmployeesApiService employeesApiService;
    private static EmployeesRepository instance;

    private EmployeesRepository() {
        Retrofit retrofit = RetrofitHelper.getInstance().getBuilder();
        employeesApiService = retrofit.create(IEmployeesApiService.class);
    }

    public static EmployeesRepository getInstance() {
        if (instance == null)
            instance = new EmployeesRepository();

        return instance;
    }

    public MutableLiveData<List<Employee>> getEmployees() {
        final MutableLiveData<List<Employee>> data = new MutableLiveData<>();

        String token = LocalStorage.getInstance().getValue("token");
        Call<List<Employee>> call = employeesApiService.getEmployees(token);

        call.enqueue(new Callback<List<Employee>>() {

            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if(response.isSuccessful()){
                    data.setValue(response.body());
                }else{
                    try {
                        String error = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {

            }
        });

        return data;

    }
}