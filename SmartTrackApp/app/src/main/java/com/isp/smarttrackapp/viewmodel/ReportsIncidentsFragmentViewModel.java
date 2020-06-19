package com.isp.smarttrackapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isp.smarttrackapp.entities.DatesFilter;
import com.isp.smarttrackapp.entities.IncidentReport;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.model.repository.remote.TrajectsRepository;

import java.util.List;

public class ReportsIncidentsFragmentViewModel extends AndroidViewModel {

    private MutableLiveData<ResponseModelWithData<List<IncidentReport>>> incidentsReport;

    public ReportsIncidentsFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ResponseModelWithData<List<IncidentReport>>> getIncidentsReport(DatesFilter datesFilter){
        incidentsReport = TrajectsRepository.getInstance().getIncidentsReport(datesFilter);

        return incidentsReport;
    }
}
