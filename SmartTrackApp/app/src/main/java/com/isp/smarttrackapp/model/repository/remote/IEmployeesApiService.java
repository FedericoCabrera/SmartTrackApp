package com.isp.smarttrackapp.model.repository.remote;

import com.isp.smarttrackapp.model.entities.Employee;
import com.isp.smarttrackapp.model.entities.Login;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IEmployeesApiService {
    @GET("Users")
    Call<List<Employee>> getEmployees(@Header("Authorization") String token);
}
