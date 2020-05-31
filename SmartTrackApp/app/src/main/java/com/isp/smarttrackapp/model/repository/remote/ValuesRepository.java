package com.isp.smarttrackapp.model.repository.remote;

import androidx.lifecycle.MutableLiveData;

import com.isp.smarttrackapp.entities.Value;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ValuesRepository {
    private IValuesApiService valuesService;

    private static ValuesRepository instance = null;

    private ValuesRepository() {
        Retrofit retrofit = RetrofitHelper.getInstance().getBuilder();
        valuesService = retrofit.create(IValuesApiService.class);
    }


    public static ValuesRepository getInstance() {
        if (instance == null)
            instance = new ValuesRepository();

        return instance;
    }

    public MutableLiveData<List<Value>> getValues() {

        final MutableLiveData<List<Value>> data = new MutableLiveData<>();

        Call<List<Value>> call = valuesService.getValues();

        call.enqueue(new Callback<List<Value>>() {
            @Override
            public void onResponse(Call<List<Value>> call, Response<List<Value>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Value>> call, Throwable t) {
            }
        });

        return data;
    }
}
