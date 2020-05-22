package com.isp.smarttrackapp.model.repository.remote;

import com.isp.smarttrackapp.model.entities.Value;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IValuesApiService {
    @GET("values")
    Call<List<Value>> getValues();
}
