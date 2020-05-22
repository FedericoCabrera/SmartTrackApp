package com.isp.smarttrackapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isp.smarttrackapp.model.entities.Value;
import com.isp.smarttrackapp.model.repository.remote.ValuesRepository;

import java.util.List;

public class ValuesViewModel extends AndroidViewModel {

    private MutableLiveData<List<Value>> valuesListObservable;

    public ValuesViewModel(@NonNull Application application) {
        super(application);

        valuesListObservable = new MutableLiveData<>();
    }

    public LiveData<List<Value>> getValuesListObservable() {
        valuesListObservable = ValuesRepository.getInstance().getValues();
        return valuesListObservable;
    }

}
