package com.isp.smarttrackapp.model.repository.remote;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.isp.smarttrackapp.Config;
import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.model.repository.local.LocalStorage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdministratorRepository {
    private IAdministratorApiService adminService;
    private static AdministratorRepository instance;

    private AdministratorRepository(){
        Retrofit retrofit = RetrofitHelper.getInstance().getBuilder();
        adminService = retrofit.create(IAdministratorApiService.class);
    }

    public static AdministratorRepository getInstance(){
        if(instance == null)
            instance = new AdministratorRepository();

        return instance;
    }

    public void updateFirebaseMessagingToken(){
        String token = LocalStorage.getInstance().getValue(Config.KEY_USER_TOKEN);
        String fcmToken = LocalStorage.getInstance().getValue(Config.KEY_FCM_TOKEN);
        Call<ResponseModel> call = adminService.updateFirebaseMessagingToken(token, fcmToken);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.isSuccessful()){
                    Log.e("FCMToken","Token guardado correctamente");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
            }
        });
    }
}
