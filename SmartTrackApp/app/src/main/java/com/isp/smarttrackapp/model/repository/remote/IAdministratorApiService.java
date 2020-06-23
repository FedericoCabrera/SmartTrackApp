package com.isp.smarttrackapp.model.repository.remote;

import com.isp.smarttrackapp.entities.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IAdministratorApiService {

    @PUT("Users/FCMToken/{fcmToken}")
    Call<ResponseModel> updateFirebaseMessagingToken(@Header("Authorization") String token, @Path("fcmToken") String fcmToken);
}
