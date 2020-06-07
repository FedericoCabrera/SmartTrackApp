package com.isp.smarttrackapp.model.repository.remote;

import com.isp.smarttrackapp.entities.Employee;
import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.entities.ResponseModelWithData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;

public interface IEmployeesApiService {
    @GET("Users")
    Call<ResponseModelWithData<List<Employee>>> getEmployees(@Header("Authorization") String token);

    @POST("Users")
    Call<ResponseModelWithData<Employee>> createEmployee(@Header("Authorization") String token, @Body Employee employee);

    @DELETE("Users")
    Call<ResponseModel> removeEmployee(@Header("Authorization") String token, @Body Employee employee);

    @PUT("Users")
    Call<ResponseModelWithData<Employee>> updateEmployee(@Header("Authorization") String token, @Body Employee employee);
}
