package com.isp.smarttrackapp.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.isp.smarttrackapp.Config;
import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.entities.Position;
import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.model.repository.local.LocalStorage;
import com.isp.smarttrackapp.viewmodel.CreateTrajectFragmentViewModel;
import com.isp.smarttrackapp.viewmodel.UpdateEmployeeFragmentViewModel;

public class EmployeeMapFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    private Context thisContext;
    private View mainView;
    private GoogleMap googleMap;
    private MapView mapView;
    private Button btnNewIncident;
    private Button btnStartTraject;
    private NavController navController;
    private CreateTrajectFragmentViewModel createTrajectFragmentViewModel;
    private boolean onAtrip;
    private Chronometer simpleChronometer;

    public EmployeeMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        thisContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_employee_map, container, false);
        createTrajectFragmentViewModel = new ViewModelProvider(this).get(CreateTrajectFragmentViewModel.class);
        return mainView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LocalStorage localStorage = LocalStorage.getInstance();
        double latitude = Double.parseDouble(localStorage.getValue(Config.KEY_LAST_LATITUDE));
        double longitude = Double.parseDouble(localStorage.getValue(Config.KEY_LAST_LONGITUDE));
        LatLng place = new LatLng(latitude, longitude);
        this.googleMap.addMarker(new MarkerOptions().position(place).title(localStorage.getValue(localStorage.getValue(Config.KEY_USER_USERNAME))));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(place));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 15));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
    }

    private void finishTraject(){

        simpleChronometer.stop();
        btnStartTraject.setText("Comenzar!");
        btnNewIncident.setVisibility(View.INVISIBLE);


    }

    private void startTraject(){
        try {
            LocalStorage localStorage = LocalStorage.getInstance();
            double latitude = Double.parseDouble(localStorage.getValue(Config.KEY_LAST_LATITUDE));
            double longitude = Double.parseDouble(localStorage.getValue(Config.KEY_LAST_LONGITUDE));
            Position p= new Position();
            p.setLatitude(latitude);
            p.setLongitude(longitude);
            createTrajectFragmentViewModel.createTraject(p);
            onAtrip = true;
            btnStartTraject.setText("Terminar");
            simpleChronometer.setBase(SystemClock.elapsedRealtime());
            simpleChronometer.setFormat("Tiempo viaje - %s"); // set the format for a chronometer
            simpleChronometer.start();
            btnNewIncident.setVisibility(View.VISIBLE);

        } catch (Exception ex) {
            Toast.makeText(thisContext, ex.toString(), Toast.LENGTH_LONG).show();
            Log.println(Log.ERROR, "error", ex.getMessage() + "   " + ex.toString());
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        mapView = (MapView) mainView.findViewById(R.id.emain_map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
        btnNewIncident = view.findViewById(R.id.em_new_incident_btn);
        btnStartTraject = view.findViewById(R.id.em_start_traject_btn);
        simpleChronometer =view.findViewById(R.id.simpleChronometer);

        btnNewIncident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                try {
                    navController.navigate(R.id.action_employeeMapFragment_to_createIncidentFragment);
                } catch (Exception ex) {
                    Toast.makeText(thisContext, ex.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnStartTraject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(onAtrip){
                    finishTraject();
                }else{
                    startTraject();
                }
            }
        });

    }
}

