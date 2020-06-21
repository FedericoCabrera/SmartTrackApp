package com.isp.smarttrackapp.model.repository.remote;

import com.isp.smarttrackapp.entities.Login;
import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.entities.Session;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ILoginApiService {
    @POST("Login")
    Call<ResponseModelWithData<Session>> login(@Body Login login);

    @PUT("Login/logout")
    Call<ResponseModel> logout(@Header("Authorization") String token);
}
