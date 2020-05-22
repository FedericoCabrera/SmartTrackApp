package com.isp.smarttrackapp.model.repository.remote;

import androidx.lifecycle.MutableLiveData;

import com.isp.smarttrackapp.exceptions.UnhandledRepositoryException;
import com.isp.smarttrackapp.model.entities.Login;
import com.isp.smarttrackapp.model.entities.Session;

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

    public MutableLiveData<Session> login(Login login) {
        final MutableLiveData<Session> data = new MutableLiveData<>();

        Call<Session> call = loginService.login(login);

        call.enqueue(new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                if(response.isSuccessful()){
                    data.setValue(response.body());
                }else{
                    try {
                        String error = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<Session> call, Throwable t){
                //throw new UnhandledRepositoryException(t.getMessage(), t);
            }
        });

        return data;
    }
}
