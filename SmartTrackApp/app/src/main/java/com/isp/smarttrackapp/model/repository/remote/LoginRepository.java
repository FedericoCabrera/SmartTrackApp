package com.isp.smarttrackapp.model.repository.remote;

import androidx.lifecycle.MutableLiveData;

import com.isp.smarttrackapp.Config;
import com.isp.smarttrackapp.entities.Login;
import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.entities.Session;
import com.isp.smarttrackapp.model.repository.local.LocalStorage;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginRepository {
    private ILoginApiService loginService;
    private static LoginRepository instance;

    private LoginRepository(){
        Retrofit retrofit = RetrofitHelper.getInstance().getBuilder();
        loginService = retrofit.create(ILoginApiService.class);
    }

    public static LoginRepository getInstance(){
        if(instance == null)
            instance = new LoginRepository();

        return instance;
    }

    public MutableLiveData<ResponseModelWithData<Session>> login(Login login) {
        final MutableLiveData<ResponseModelWithData<Session>> data = new MutableLiveData<>();

        Call<ResponseModelWithData<Session>> call = loginService.login(login);

        call.enqueue(new Callback<ResponseModelWithData<Session>>() {
            @Override
            public void onResponse(Call<ResponseModelWithData<Session>> call, Response<ResponseModelWithData<Session>> response) {
                if(response.isSuccessful()){

                    if(response.body().isResponseOK()) {
                        data.setValue(response.body());

                        //Save token in local storage
                        String token = response.body().getData().getToken();
                        LocalStorage.getInstance().setValue(token, Config.KEY_USER_TOKEN);
                    }else{
                        ResponseModelWithData<Session> errorResponse = new ResponseModelWithData<>();
                        errorResponse.setResponseOK(false);
                        errorResponse.setErrorMessage(response.body().getErrorMessage());
                        data.setValue(errorResponse);
                    }

                }else{
                    String error;
                    ResponseModelWithData<Session> errorResponse;

                    try {
                        error = response.errorBody().string();
                        errorResponse = new ResponseModelWithData(false, error);
                        data.setValue(errorResponse);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseModelWithData<Session>> call, Throwable t){
            }
        });

        return data;
    }

    public MutableLiveData<ResponseModel> logout() {
        final MutableLiveData<ResponseModel> data = new MutableLiveData<>();
        String token = LocalStorage.getInstance().getValue("token");
        Call<ResponseModel> call = loginService.logout(token);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.isSuccessful()){

                    if(response.body().isResponseOK()) {
                        data.setValue(response.body());
                    }else{
                        ResponseModel errorResponse = new ResponseModel();
                        errorResponse.setResponseOK(false);
                        errorResponse.setErrorMessage(response.body().getErrorMessage());
                        data.setValue(errorResponse);
                    }
                }else{
                    String error;
                    ResponseModel errorResponse;
                    try {
                        error = response.errorBody().string();
                        errorResponse = new ResponseModel(false, error);
                        data.setValue(errorResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t){
            }
        });

        return data;
    }
}
