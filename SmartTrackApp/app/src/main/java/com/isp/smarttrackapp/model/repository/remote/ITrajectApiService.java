package com.isp.smarttrackapp.model.repository.remote;

import com.isp.smarttrackapp.entities.DatesFilter;
import com.isp.smarttrackapp.entities.Incident;
import com.isp.smarttrackapp.entities.IncidentReport;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.entities.Traject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ITrajectApiService {
    @POST("Trajects")
    Call<ResponseModelWithData<String>> createTraject(@Header("Authorization") String token, @Body Traject traject);

    @POST("Trajects/Incidents/{trajectId}")
    Call<ResponseModelWithData<String>> assignIncidentToTraject(@Header("Authorization") String token, @Path("trajectId") String trajectId, @Body Incident incident);

    @PUT("Trajects/Incidents")
    Call<ResponseModelWithData<List<IncidentReport>>> getIncidentsReport(@Header("Authorization") String token, @Body DatesFilter filter);

}
