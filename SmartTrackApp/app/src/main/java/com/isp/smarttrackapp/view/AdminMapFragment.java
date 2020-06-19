package com.isp.smarttrackapp.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.entities.Employee;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.model.repository.local.LocalStorage;
import com.isp.smarttrackapp.model.repository.remote.EmployeesRepository;
import com.isp.smarttrackapp.viewmodel.AdminMapFragmentViewModel;
import com.isp.smarttrackapp.viewmodel.EmployeeListFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

public class AdminMapFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    private Context thisContext;
    private View mainView;
    private GoogleMap googleMap2;
    private MapView mapView;
    private AdminMapFragmentViewModel adminMapViewModel;
    private List<Employee> employeeList;
    private Fragment fragment;
    private ArrayList<MarkerOptions> markers;
    private Handler handler ;
    private Runnable runnable;


    public AdminMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        thisContext = context;
        fragment = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        markers = new ArrayList<MarkerOptions>();
      content();
    }

    public void content(){

        onMapReady(googleMap2);
        refresh(1000);
    }
    private void refresh(int miliseconds){
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                content();
            }
        };
        handler.postDelayed(runnable,miliseconds);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.fragment_admin_map, container, false);
        adminMapViewModel = new ViewModelProvider(this).get(AdminMapFragmentViewModel.class);


        return mainView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap2 = googleMap;
        if (googleMap2!=null) {
            googleMap2.clear();
           for (MarkerOptions m:  markers) {
                m.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_point));
                googleMap2.addMarker(m);
            }
            drawEmployees();
        }
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 15));
     // googleMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
    }

    private void drawEmployees(){
        employeeList = new ArrayList<Employee>();
        adminMapViewModel.getLocation().observe(getViewLifecycleOwner(), new Observer<ResponseModelWithData<List<Employee>>>() {
            @Override
            public void onChanged(ResponseModelWithData<List<Employee>> employees) {
                employeeList = employees.getData();
                for (Employee e: employeeList) {
                    LatLng place = new LatLng(e.getPosition().getLatitude(), e.getPosition().getLongitude());
                    if (e.getStatus().toString().equals(Employee.Status.CONNECTED.toString()))
                    {
                        MarkerOptions m = new MarkerOptions()
                                .position(place)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_truck))
                                .title(e.getName());
                        googleMap2.addMarker(m);
                        markers.add(m);

                    }else{
                        MarkerOptions m = new MarkerOptions()
                                .position(place)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_truck_red))
                                .title(e.getName());
                        googleMap2.addMarker(m);
                    }
                }
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) mainView.findViewById(R.id.emain_map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync((OnMapReadyCallback) fragment);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}