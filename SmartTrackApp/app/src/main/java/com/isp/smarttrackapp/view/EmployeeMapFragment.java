package com.isp.smarttrackapp.view;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.isp.smarttrackapp.Config;
import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.entities.Position;
import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.entities.Traject;
import com.isp.smarttrackapp.viewmodel.EmployeeMapFragmentViewModel;

import java.text.DecimalFormat;

import static android.content.Context.LOCATION_SERVICE;

public class EmployeeMapFragment extends Fragment implements OnMapReadyCallback, LocationListener {
    private Context thisContext;
    private View mainView;

    private GoogleMap googleMap;
    private MapView mapView;
    private Marker marker;
    private CameraPosition camera;

    private double trajectDistance;

    private LocationManager locationManager;
    private Location currentLocation;

    private Button btnNewIncident;
    private Button btnStartTraject;
    private NavController navController;
    private EmployeeMapFragmentViewModel employeeMapFragmentViewModel;
    private boolean onAtrip;
    private Chronometer simpleChronometer;
    private TextView distanceView;

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
        trajectDistance = 0;
        //onAtrip = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_employee_map, container, false);
        employeeMapFragmentViewModel = new ViewModelProvider(this).get(EmployeeMapFragmentViewModel.class);
        locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

        return mainView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        locationManager.removeUpdates(this);
        marker = null;
    }

    private boolean isGPSEnabled() {
        try {
            int gpsSignal = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);

            if (gpsSignal == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showInfoAlert() {
        new AlertDialog.Builder(getContext())
                .setTitle("Señal de GPS")
                .setMessage("Usted no tiene señal de GPS habilitada. ¿Desea habilitarla ahora?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void createOrUpdateMarkerByLocation(Location location) {
        if (marker == null) {

            marker = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(location.getLatitude(), location.getLongitude()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_truck_red)));
        } else {
            marker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
        }
    }

    private void zoomToLocation(Location location) {
        camera = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                .zoom(16)                           // limit -> 21
                .bearing(location.getBearing())     // 0 - 365º
                .tilt(45)                           // limit -> 90
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera), 2000, null);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (!isGPSEnabled()) {
            showInfoAlert();
        } else {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(thisContext, "Asigne permiso para utilizar localización por GPS.", Toast.LENGTH_LONG).show();
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Config.UPDATE_LOCATION_TIME, 0, this);

                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location == null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
                currentLocation = location;

            }
        }

        if (currentLocation != null) {
            createOrUpdateMarkerByLocation(currentLocation);
            zoomToLocation(currentLocation);
        }
    }

    private void setLastKnownLocationLocally(){
        employeeMapFragmentViewModel.setLocalStorage(currentLocation.getLatitude()+"", Config.KEY_LAST_LATITUDE);
        employeeMapFragmentViewModel.setLocalStorage(currentLocation.getLongitude()+"", Config.KEY_LAST_LONGITUDE);
    }


    private void finishTraject(){
        String trajectId = employeeMapFragmentViewModel.getLocalStorage(Config.KEY_ACTUAL_TRAJECT_ID);
        Position position = getPosition();
        Traject traject = new Traject();
        traject.setTrajectId(trajectId);
        traject.setDistance(trajectDistance);
        traject.setLocationFinal(position);
        employeeMapFragmentViewModel.endTraject(traject);
        simpleChronometer.stop();
        btnStartTraject.setText("Comenzar!");
        btnNewIncident.setVisibility(View.INVISIBLE);
        onAtrip = false;

    }

    private Position getPosition()
    {
        double latitude = Double.parseDouble(employeeMapFragmentViewModel.getLocalStorage(Config.KEY_LAST_LATITUDE));
        double longitude = Double.parseDouble(employeeMapFragmentViewModel.getLocalStorage(Config.KEY_LAST_LONGITUDE));
        Position p= new Position();
        p.setLatitude(latitude);
        p.setLongitude(longitude);
        return p;
    }

    private void startTraject(){
        try {
            employeeMapFragmentViewModel.createTraject(getPosition()).observe(getViewLifecycleOwner(), new Observer<ResponseModelWithData<String>>() {
                @Override
                public void onChanged(ResponseModelWithData<String> stringResponseModelWithData) {
            }
            });
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
        distanceView = mainView.findViewById((R.id.em_distance));
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

    @Override
    public void onLocationChanged(final Location location) {
        try{
            employeeMapFragmentViewModel.updateLocation(location.getLatitude(), location.getLongitude()).observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
                @Override
                public void onChanged(ResponseModel response) {
                    if(response.isResponseOK()){
                        Log.println(Log.INFO, "Ubicacion", "Latitud : " + location.getLatitude() + " Longitud" + location.getLongitude() );

                    }else{
                        Toast.makeText(thisContext, response.getErrorMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            });

        }catch(Exception ex){
            Toast.makeText(thisContext, ex.toString(), Toast.LENGTH_LONG).show();
        }
        if(onAtrip){
            trajectDistance += currentLocation.distanceTo(location);
            DecimalFormat df = new DecimalFormat("#");
            String d = (df.format(trajectDistance));
            distanceView.setText("Distancia - " + d + "m");

        }else{
            trajectDistance = 0;
        }

        currentLocation = location;
        setLastKnownLocationLocally();
        createOrUpdateMarkerByLocation(location);
        zoomToLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

