package com.isp.smarttrackapp.model.repository.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.isp.smarttrackapp.Config;

import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static RetrofitHelper instance = null;
    private static Retrofit retrofitInstance = null;

    private RetrofitHelper(){

        retrofitInstance = new Retrofit.Builder()
                .baseUrl(Config.BASE_API_URL)
                //.client(UnsafeOkHttpClient.getUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitHelper getInstance(){
        if(instance == null)
            instance = new RetrofitHelper();

        return  instance;
    }

    public Retrofit getBuilder(){
        return retrofitInstance;
    }
}
