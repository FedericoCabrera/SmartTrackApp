package com.isp.smarttrackapp.model.repository.remote;

import com.isp.smarttrackapp.entities.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IEmployeesApiService {
    @GET("Users")
    Call<List<Employee>> getEmployees(@Header("Authorization") String token);
}
