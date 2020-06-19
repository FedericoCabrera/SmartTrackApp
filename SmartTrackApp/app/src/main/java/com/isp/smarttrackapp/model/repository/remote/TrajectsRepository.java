package com.isp.smarttrackapp.model.repository.remote;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.isp.smarttrackapp.Config;
import com.isp.smarttrackapp.entities.DatesFilter;
import com.isp.smarttrackapp.entities.Incident;
import com.isp.smarttrackapp.entities.IncidentReport;
import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.entities.Traject;
import com.isp.smarttrackapp.model.repository.local.LocalStorage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TrajectsRepository {
    private ITrajectApiService trajectService;
    private static TrajectsRepository instance;

    private TrajectsRepository(){
        Retrofit retrofit = RetrofitHelper.getInstance().getBuilder();
        trajectService = retrofit.create(ITrajectApiService.class);
    }

    public static TrajectsRepository getInstance(){
        if(instance == null)
            instance = new TrajectsRepository();

        return  instance;
    }

    public MutableLiveData<ResponseModelWithData<String>> createTraject(Traject traject){
        final MutableLiveData<ResponseModelWithData<String>> data = new MutableLiveData<>();

        String token = LocalStorage.getInstance().getValue(Config.KEY_USER_TOKEN);
        Call<ResponseModelWithData<String>> call = trajectService.createTraject(token,traject);

        call.enqueue(new Callback<ResponseModelWithData<String>>() {
            @Override
            public void onResponse(Call<ResponseModelWithData<String>> call, Response<ResponseModelWithData<String>> response) {
                if(response.isSuccessful()){
                    data.setValue(response.body());
                    String actualTrajectId = response.body().getData();
                    LocalStorage.getInstance().setValue(actualTrajectId, Config.KEY_ACTUAL_TRAJECT_ID);

                }else{
                    String error;
                    ResponseModelWithData<String> errorResponse;

                    try {
                        error = response.errorBody().string();
                        errorResponse = new ResponseModelWithData(false, error);
                        data.setValue(errorResponse);

                    } catch (IOException e) {
                        error = Config.UNEXPECTED_ERROR_MSG;
                        errorResponse = new ResponseModelWithData(false, error);
                        data.setValue(errorResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModelWithData<String>> call, Throwable t) {

            }
        });

        return data;
    }

    public MutableLiveData<ResponseModelWithData<String>> assignIncidentToTraject(Incident incident){
        final MutableLiveData<ResponseModelWithData<String>> data = new MutableLiveData<>();

        String token = LocalStorage.getInstance().getValue(Config.KEY_USER_TOKEN);
        //String trajectId = LocalStorage.getInstance().getValue(Config.KEY_ACTUAL_TRAJECT_ID);
        String trajectId = "891AD943-B35F-437C-BF62-05BDA8EF1548";

        Call<ResponseModelWithData<String>> call = trajectService.assignIncidentToTraject(token, trajectId, incident);

        call.enqueue(new Callback<ResponseModelWithData<String>>() {
            @Override
            public void onResponse(Call<ResponseModelWithData<String>> call, Response<ResponseModelWithData<String>> response) {
                if(response.isSuccessful()){
                    data.setValue(response.body());

                }else{
                    String error = "";
                    ResponseModelWithData<String> errorResponse;

                    try {
                        error = response.errorBody().string();
                        errorResponse = new ResponseModelWithData(false, error);
                        data.setValue(errorResponse);

                    } catch (Exception e) {
                        error = Config.UNEXPECTED_ERROR_MSG;
                        errorResponse = new ResponseModelWithData(false, error);
                        data.setValue(errorResponse);

                        Log.e("assignIncidentToTraject","error: "+e.toString());

                    }

                    Log.e("assignIncidentToTraject","error: "+error);
                }
            }

            @Override
            public void onFailure(Call<ResponseModelWithData<String>> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });

        return data;
    }

    public MutableLiveData<ResponseModelWithData<List<IncidentReport>>> getIncidentsReport(DatesFilter filter){
        final MutableLiveData<ResponseModelWithData<List<IncidentReport>>> data = new MutableLiveData<>();
        String token = LocalStorage.getInstance().getValue(Config.KEY_USER_TOKEN);

        Call<ResponseModelWithData<List<IncidentReport>>> call = trajectService.getIncidentsReport(token, filter);

        call.enqueue(new Callback<ResponseModelWithData<List<IncidentReport>>>() {
            @Override
            public void onResponse(Call<ResponseModelWithData<List<IncidentReport>>> call, Response<ResponseModelWithData<List<IncidentReport>>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());

                } else {
                    String error = "";
                    ResponseModelWithData<List<IncidentReport>> errorResponse = new ResponseModelWithData<>();

                    try {
                        error = response.errorBody().string();
                        errorResponse = new ResponseModelWithData(false, error);
                        data.setValue(errorResponse);

                    } catch (Exception e) {
                        error = Config.UNEXPECTED_ERROR_MSG;
                        errorResponse = new ResponseModelWithData(false, error);
                        data.setValue(errorResponse);

                        Log.e("onResponse", "error: " + e.toString());

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModelWithData<List<IncidentReport>>> call, Throwable t) {
                Log.e("getIncidentsReport", t.toString());
            }
        });

        return data;
    }

}
