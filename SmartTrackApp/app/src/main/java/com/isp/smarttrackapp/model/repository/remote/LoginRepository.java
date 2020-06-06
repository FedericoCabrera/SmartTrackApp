package com.isp.smarttrackapp.model.repository.remote;

import androidx.lifecycle.MutableLiveData;

import com.isp.smarttrackapp.entities.Login;
import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
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

    public MutableLiveData<ResponseModelWithData<Session>> login(Login login) {
        final MutableLiveData<ResponseModelWithData<Session>> data = new MutableLiveData<>();

        Call<ResponseModelWithData<Session>> call = loginService.login(login);

        call.enqueue(new Callback<ResponseModelWithData<Session>>() {
            @Override
            public void onResponse(Call<ResponseModelWithData<Session>> call, Response<ResponseModelWithData<Session>> response) {
                if(response.isSuccessful()){

                    data.setValue(response.body());

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
                //throw new UnhandledRepositoryException(t.getMessage(), t);
            }
        });

        return data;
    }
}
