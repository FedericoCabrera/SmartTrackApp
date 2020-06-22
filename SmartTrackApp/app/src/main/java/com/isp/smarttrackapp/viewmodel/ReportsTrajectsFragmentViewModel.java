package com.isp.smarttrackapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.isp.smarttrackapp.entities.DatesFilter;
import com.isp.smarttrackapp.entities.IncidentReport;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.entities.TrajectReport;
import com.isp.smarttrackapp.model.repository.remote.TrajectsRepository;

import java.util.List;



public class ReportsTrajectsFragmentViewModel extends AndroidViewModel {

    private LiveData<ResponseModelWithData<TrajectReport>> trajectReport;

    public ReportsTrajectsFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ResponseModelWithData<TrajectReport>> getTrajectsReport(DatesFilter datesFilter) {
        trajectReport = TrajectsRepository.getInstance().getTrajectsReport(datesFilter);
        return trajectReport;
    }
}
