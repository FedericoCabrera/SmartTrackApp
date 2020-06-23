package com.isp.smarttrackapp.model.repository.local;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStorage {
    private static LocalStorage instance;
    private Context context;
    private SharedPreferences preferences;

    //Constants
    final String  APP_SHARED_PREFERENCES = "com.isp.smarttrack";

    private LocalStorage(){
    }

    public void init(Context mainContext){
        this.context = mainContext;
        this.preferences = context.getSharedPreferences(APP_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static LocalStorage getInstance(){
        if(instance==null){
            instance = new LocalStorage();
        }
        return instance;
    }

    public String getValue(String key){
        return preferences.getString(key,null);
    }

    public boolean setValue(String value, String key){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        return editor.commit();
    }

    public void setValueAsync(String value, String key){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

}
