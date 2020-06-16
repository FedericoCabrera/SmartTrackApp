package com.isp.smarttrackapp.model.repository.remote;

import com.isp.smarttrackapp.entities.Employee;
import com.isp.smarttrackapp.entities.Position;
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
import retrofit2.http.Path;

public interface IEmployeesApiService {
    @GET("Users")
    Call<ResponseModelWithData<List<Employee>>> getEmployees(@Header("Authorization") String token);

    @POST("Users")
    Call<ResponseModelWithData<Employee>> createEmployee(@Header("Authorization") String token, @Body Employee employee);

    @DELETE("Users" + "/{id}")
    Call<ResponseModel> removeEmployee(@Header("Authorization") String token, @Path("id") String id);

    @PUT("Users" + "/{id}")
    Call<ResponseModelWithData<Employee>> updateEmployee(@Header("Authorization") String token, @Path("id") String id, @Body Employee employee);

    @PUT("Users")
    Call<ResponseModel> updateLocation(@Header("Authorization") String token, @Body Position position);

    @GET("Users/location")
    Call<ResponseModelWithData<List<Employee>>> getLocation(@Header("Authorization") String token);
}
