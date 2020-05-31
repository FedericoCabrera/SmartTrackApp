package com.isp.smarttrackapp.model.repository.remote;

import com.isp.smarttrackapp.entities.Login;
import com.isp.smarttrackapp.entities.Session;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ILoginApiService {
    @POST("Login")
    Call<Session> login(@Body Login login);
}
