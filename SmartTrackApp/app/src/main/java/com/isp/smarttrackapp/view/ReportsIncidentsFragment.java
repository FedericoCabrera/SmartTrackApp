package com.isp.smarttrackapp.view;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.entities.DatesFilter;
import com.isp.smarttrackapp.entities.Incident;
import com.isp.smarttrackapp.entities.IncidentReport;
import com.isp.smarttrackapp.entities.Position;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.utils.Utils;
import com.isp.smarttrackapp.viewmodel.ReportsIncidentsFragmentViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class ReportsIncidentsFragment extends Fragment {

    private static final long MAX_DAYS_DIFF = 90;
    private final int STORAGE_CODE = 1000;

    private Context thisContext;
    private ReportsIncidentsFragmentViewModel reportsIncidentsFragmentViewModel;

    private int fromYear, fromMonth, fromDay,toYear, toMonth, toDay;
    private DatePickerDialog.OnDateSetListener fromDateListener,toDateListener;

    private Button btnDateFrom;
    private Button btnDateTo;
    private Button btnAccept;
    private Button btnCancel;
    private ImageButton btnExportPDF;
    private DatePickerDialog datePickerFrom;
    private DatePickerDialog datePickerTo;
    private EditText txtDateFrom;
    private EditText txtDateTo;
    private ListView listView;

    private List<IncidentReport> obtainedList;

    public ReportsIncidentsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fromYear = Calendar.getInstance().get(Calendar.YEAR);
        fromMonth = Calendar.getInstance().get(Calendar.MONTH);
        fromDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        toYear = Calendar.getInstance().get(Calendar.YEAR);
        toMonth = Calendar.getInstance().get(Calendar.MONTH);
        toDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        reportsIncidentsFragmentViewModel = new ViewModelProvider(this).get(ReportsIncidentsFragmentViewModel.class);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_incidents_reports, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnDateFrom = view.findViewById(R.id.ri_btn_date_from);
        btnDateTo = view.findViewById(R.id.ri_btn_date_to);
        btnAccept = view.findViewById(R.id.ri_btn_details);
        btnCancel = view.findViewById(R.id.ri_btn_cancel);
        btnExportPDF = view.findViewById(R.id.ri_btn_export);
        txtDateFrom = view.findViewById(R.id.ri_txt_date_from);
        txtDateTo = view.findViewById(R.id.ri_txt_date_to);
        listView = view.findViewById(R.id.ri_list_view);


        setFromDateText(fromYear,fromMonth,fromDay);
        setToDateText(toYear,toMonth,toDay);

        btnDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerFrom = new DatePickerDialog(
                        thisContext,
                        fromDateListener,
                        fromYear,
                        fromMonth,
                        fromDay
                );
                datePickerFrom.show();
            }
        });

        btnDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerTo = new DatePickerDialog(
                        thisContext,
                        toDateListener,
                        toYear,
                        toMonth,
                        toDay
                );
                datePickerTo.show();
            }
        });

        fromDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fromDay = dayOfMonth;
                fromMonth = month;
                fromYear = year;

                setFromDateText(fromYear,fromMonth,fromDay);
            }
        };

        toDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                toDay = dayOfMonth;
                toMonth = month;
                toYear = year;

                setToDateText(toYear,toMonth,toDay);
            }
        };

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkDates()){
                    populateListView();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        btnExportPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionsAndExportPDF();
            }
        });

    }

    private void setFromDateText(int year, int month, int day){
        String date = day + "/" + (month+1) + "/" + year;
        txtDateFrom.setText(date);
    }

    private void setToDateText(int year, int month, int day){
        String date = day + "/" + (month+1) + "/" + year;
        txtDateTo.setText(date);
    }

    private Boolean checkDates(){
        boolean datesOk = true;

        Calendar calendarFrom = Calendar.getInstance();
        calendarFrom.set(fromYear,fromMonth,fromDay);

        Calendar calendarTo = Calendar.getInstance();
        calendarTo.set(toYear,toMonth,toDay);
        if(calendarFrom.after(calendarTo)){
            Toast.makeText(thisContext,"La fecha desde debe ser menor o igual a la fecha hasta.", Toast.LENGTH_SHORT).show();
            datesOk = false;
        }else{

            long daysDifference = TimeUnit.MILLISECONDS.toDays(
                    Math.abs(calendarTo.getTimeInMillis() - calendarFrom.getTimeInMillis()));

            if(daysDifference > MAX_DAYS_DIFF){
                Toast.makeText(thisContext,"El período seleccionado no puede superar los "+ MAX_DAYS_DIFF +" días.", Toast.LENGTH_SHORT).show();
                datesOk = false;
            }

        }
        return datesOk;
    }

    private void checkPermissionsAndExportPDF(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            if(checkSelfPermission(thisContext,(Manifest.permission.WRITE_EXTERNAL_STORAGE)) == PackageManager.PERMISSION_DENIED){
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions,STORAGE_CODE);
            }else{
                exportPDF();
            }
        }else{
            exportPDF();
        }
    }

    private void exportPDF() {
        if(obtainedList != null){
            if(obtainedList.size() > 0){
                List<String>lines = new ArrayList<>();

                for(IncidentReport i : obtainedList){
                    String newLine = i.getUserName() + " " + i.getAddress() + " " + i.getDate() + " "+ i.getDescription();
                    lines.add(newLine);
                }

                String stringPath = Utils.writePDF("Reporte de incidentes período " +
                        txtDateFrom.getText().toString() + " - " + txtDateTo.getText().toString(), lines);

                Toast.makeText(thisContext, "Archivo exportado " +stringPath, Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(thisContext, "No hay datos seleccionados para exportar. ", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(thisContext, "No hay datos seleccionados para exportar. ", Toast.LENGTH_LONG).show();
        }

    }

    private void populateListView(){
        DatesFilter datesFilter = new DatesFilter();
        datesFilter.setDayFrom(fromDay);
        datesFilter.setMonthFrom(fromMonth+1);
        datesFilter.setYearFrom(fromYear);
        datesFilter.setDayTo(toDay);
        datesFilter.setMonthTo(toMonth+1);
        datesFilter.setYearTo(toYear);

        final List<IncidentReport>[] list = new List[]{new ArrayList<>()};

        reportsIncidentsFragmentViewModel.getIncidentsReport(datesFilter).observe(this, new Observer<ResponseModelWithData<List<IncidentReport>>>() {
            @Override
            public void onChanged(ResponseModelWithData<List<IncidentReport>> incidentReportResponseModelWithData) {
                if(incidentReportResponseModelWithData.isResponseOK()){
                    list[0] = (List<IncidentReport>) incidentReportResponseModelWithData.getData();
                    obtainedList = incidentReportResponseModelWithData.getData();

                    IncidentsListAdapter adapter = new IncidentsListAdapter(thisContext,R.layout.ri_adapter_view_layout, (ArrayList<IncidentReport>) list[0]);
                    listView.setAdapter(adapter);
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case STORAGE_CODE: {
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    exportPDF();
                }else{
                    Toast.makeText(thisContext, "Permisos denegados.", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        thisContext = context;
    }

}
