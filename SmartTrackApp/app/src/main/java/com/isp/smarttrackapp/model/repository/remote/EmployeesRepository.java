package com.isp.smarttrackapp.model.repository.remote;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.isp.smarttrackapp.Config;
import com.isp.smarttrackapp.entities.Employee;
import com.isp.smarttrackapp.entities.Position;
import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
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

    public MutableLiveData<ResponseModelWithData<List<Employee>>> getEmployees() {
        final MutableLiveData<ResponseModelWithData<List<Employee>>> data = new MutableLiveData<>();
        String token = LocalStorage.getInstance().getValue(Config.KEY_USER_TOKEN);
        Call<ResponseModelWithData<List<Employee>>> call = employeesApiService.getEmployees(token);
        call.enqueue(new Callback<ResponseModelWithData<List<Employee>>>() {
            @Override
            public void onResponse(Call<ResponseModelWithData<List<Employee>>> call, Response<ResponseModelWithData<List<Employee>>> response) {
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
            public void onFailure(Call<ResponseModelWithData<List<Employee>>> call, Throwable t) {

            }

        });
        return data;
    }

    public MutableLiveData<ResponseModelWithData<List<Employee>>> getLocation() {
        final MutableLiveData<ResponseModelWithData<List<Employee>>> data = new MutableLiveData<>();
        String token = LocalStorage.getInstance().getValue("token");
        Call<ResponseModelWithData<List<Employee>>> call = employeesApiService.getLocation(token);
        call.enqueue(new Callback<ResponseModelWithData<List<Employee>>>() {
            @Override
            public void onResponse(Call<ResponseModelWithData<List<Employee>>> call, Response<ResponseModelWithData<List<Employee>>> response) {
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
            public void onFailure(Call<ResponseModelWithData<List<Employee>>> call, Throwable t) {

                Log.println(Log.ERROR,"error", t.getMessage());

            }

        });
        return data;
    }

    public MutableLiveData<ResponseModelWithData<Employee>> createEmployee(Employee employee) {
        final MutableLiveData<ResponseModelWithData<Employee>> data = new MutableLiveData<>();
        String token = LocalStorage.getInstance().getValue(Config.KEY_USER_TOKEN);
        Call<ResponseModelWithData<Employee>> call = employeesApiService.createEmployee(token, employee);
        call.enqueue(new Callback<ResponseModelWithData<Employee>>() {
            @Override
            public void onResponse(Call<ResponseModelWithData<Employee>> call, Response<ResponseModelWithData<Employee>> response) {
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
            public void onFailure(Call<ResponseModelWithData<Employee>> call, Throwable t) {
            }
        });
        return data;
    }

    public MutableLiveData<ResponseModel> removeEmployee(Employee employee) {
        final MutableLiveData<ResponseModel> data = new MutableLiveData<>();
        String token = LocalStorage.getInstance().getValue(Config.KEY_USER_TOKEN);
        Call<ResponseModel> call = employeesApiService.removeEmployee(token, employee.getId());
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
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
            public void onFailure(Call<ResponseModel> call, Throwable t) {
            }
        });
        return data;
    }

    public MutableLiveData<ResponseModel> updateEmployee(Employee employee) {
        final MutableLiveData<ResponseModel> data = new MutableLiveData<>();

        String token = LocalStorage.getInstance().getValue(Config.KEY_USER_TOKEN);
        Call<ResponseModelWithData<Employee>> call = employeesApiService.updateEmployee(token, employee.getId(), employee);
        call.enqueue(new Callback<ResponseModelWithData<Employee>>() {
            @Override
            public void onResponse(Call<ResponseModelWithData<Employee>> call, Response<ResponseModelWithData<Employee>> response) {
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
            public void onFailure(Call<ResponseModelWithData<Employee>> call, Throwable t) {
            }
        });
        return data;
    }

    public void cleanLastKnownLocation(){
        //Clean last known location
        LocalStorage.getInstance().setValue("", Config.KEY_LAST_LATITUDE);
        LocalStorage.getInstance().setValue("", Config.KEY_LAST_LONGITUDE);
    }

    public MutableLiveData<ResponseModel> updateLocation(Position position) {
        final MutableLiveData<ResponseModel> data = new MutableLiveData<>();

        //Update last known location
        LocalStorage.getInstance().setValue(position.getLatitude()+"", Config.KEY_LAST_LATITUDE);
        LocalStorage.getInstance().setValue(position.getLongitude()+"", Config.KEY_LAST_LONGITUDE);

        String token = LocalStorage.getInstance().getValue(Config.KEY_USER_TOKEN);
        Call<ResponseModel> call = employeesApiService.updateLocation(token, position);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
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
            public void onFailure(Call<ResponseModel> call, Throwable t) {
            }
        });
        return data;
    }
}