package com.isp.smarttrackapp.view;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.entities.DatesFilter;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.entities.TrajectReport;
import com.isp.smarttrackapp.entities.TrajectReportLine;
import com.isp.smarttrackapp.utils.Utils;
import com.isp.smarttrackapp.viewmodel.ReportsTrajectsFragmentViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static androidx.core.content.ContextCompat.checkSelfPermission;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportsTrajectsFragment extends Fragment {

    private static final long MAX_DAYS_DIFF = 90;
    private final int STORAGE_CODE = 1000;

    private Context thisContext;
    private ReportsTrajectsFragmentViewModel reportsTrajectsFragmentViewModel;

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
    private TextView txtTotalDuration;
    private TextView txtTotalDistance;

    private int fromYear, fromMonth, fromDay,toYear, toMonth, toDay;

    private DatePickerDialog.OnDateSetListener fromDateListener,toDateListener;

    TrajectReport reportObtained;

    public ReportsTrajectsFragment() {
        // Required empty public constructor
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

        reportsTrajectsFragmentViewModel = new ViewModelProvider(this).get(ReportsTrajectsFragmentViewModel.class);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trajects_reports, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnDateFrom = view.findViewById(R.id.rt_btn_date_from);
        btnDateTo = view.findViewById(R.id.rt_btn_date_to);
        btnAccept = view.findViewById(R.id.rt_btn_accept);
        btnCancel = view.findViewById(R.id.rt_btn_cancel);
        btnExportPDF = view.findViewById(R.id.rt_btn_export);
        txtDateFrom = view.findViewById(R.id.rt_txt_date_from);
        txtDateTo = view.findViewById(R.id.rt_txt_date_to);
        listView = view.findViewById(R.id.rt_list_view);
        txtTotalDistance = view.findViewById(R.id.rt_total_distance);
        txtTotalDuration = view.findViewById(R.id.rt_total_duration);

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
        if(reportObtained != null){
            if(reportObtained.lines.size() > 0){
                List<String> lines = new ArrayList<>();

                for(TrajectReportLine t : reportObtained.lines){
                    String newLine =
                            t.getUserName() + " " +
                            t.getStartDate() + " " +
                            "Distancia: " + t.getDistance() + " m " +
                            "Duración: " + t.getDuration() + " s "
                            ;
                    lines.add(newLine);
                }

                String stringPath = Utils.writePDF("Reporte de trayectos terminados período " +
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

        final TrajectReport[] trajectReport = {new TrajectReport()};

        reportsTrajectsFragmentViewModel.getTrajectsReport(datesFilter).observe(this, new Observer<ResponseModelWithData<TrajectReport>>() {
            @Override
            public void onChanged(ResponseModelWithData<TrajectReport> trajectReportResponseModelWithData) {
                if(trajectReportResponseModelWithData.isResponseOK()){
                    trajectReport[0] = (TrajectReport) trajectReportResponseModelWithData.getData();
                    reportObtained = trajectReportResponseModelWithData.getData();
                    if(reportObtained!=null){

                        ArrayList<TrajectReportLine>lines = (ArrayList<TrajectReportLine>) trajectReport[0].lines;

                        TrajectsListAdapter adapter = new TrajectsListAdapter(thisContext,R.layout.rt_adapter_view_layout, lines);
                        listView.setAdapter(adapter);

                        txtTotalDistance.setText("Distancia total: "+reportObtained.totalDistance +" m");
                        txtTotalDuration.setText("Duración total: "+reportObtained.totalDuration +" s");
                    }

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
