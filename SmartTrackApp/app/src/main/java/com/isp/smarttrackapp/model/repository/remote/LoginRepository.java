package com.isp.smarttrackapp.model.repository.remote;

import androidx.lifecycle.MutableLiveData;

import com.isp.smarttrackapp.entities.Login;
import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.entities.Session;

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

    public MutableLiveData<ResponseModel<Session>> login(Login login) {
        final MutableLiveData<ResponseModel<Session>> data = new MutableLiveData<>();

        Call<ResponseModel<Session>> call = loginService.login(login);

        call.enqueue(new Callback<ResponseModel<Session>>() {
            @Override
            public void onResponse(Call<ResponseModel<Session>> call, Response<ResponseModel<Session>> response) {
                if(response.isSuccessful()){

                    data.setValue(response.body());

                }else{
                    String error;
                    ResponseModel<Session> errorResponse;

                    try {
                        error = response.errorBody().string();
                        errorResponse = new ResponseModel<>(false, error);
                        data.setValue(errorResponse);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseModel<Session>> call, Throwable t){
                //throw new UnhandledRepositoryException(t.getMessage(), t);
            }
        });

        return data;
    }
}
