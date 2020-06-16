package com.isp.smarttrackapp.model.repository.remote;

import com.isp.smarttrackapp.entities.Incident;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.entities.Traject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ITrajectApiService {
    @POST("Trajects")
    Call<ResponseModelWithData<String>> createTraject(@Header("Authorization") String token, @Body Traject traject);

    @POST("Trajects/Incidents/{trajectId}")
    Call<ResponseModelWithData<String>> assignIncidentToTraject(@Header("Authorization") String token, @Path("trajectId") String trajectId, @Body Incident incident);

}
