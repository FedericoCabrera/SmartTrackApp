package com.isp.smarttrackapp.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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

import java.util.List;

public class AdminMapFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    private Context thisContext;
    private View mainView;
    private GoogleMap googleMap;
    private MapView mapView;
    private AdminMapFragmentViewModel adminMapViewModel;

    public AdminMapFragment() {
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
        adminMapViewModel = new ViewModelProvider(this).get(AdminMapFragmentViewModel.class);
        return mainView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
   //     this.googleMap = googleMap;
     //  List<Employee> es = adminMapViewModel.getLocation().getValue().getData();
        //for (Employee e : es) {
       //     LatLng place = new LatLng(Math.random() * -34 , Math.random() * -56);
      //  this.googleMap.addMarker(new MarkerOptions().position(place).title(e.getName()));
     //  }
/*
        for (int i = 0 ; i< 10 ; i++){
            LatLng p = new LatLng( -34.875 + Math.random()/100*Math.pow(-1, i) , -56.19 + Math.random()/100*Math.pow(-1, i));
            if (i>4){
                this.googleMap.addMarker(new MarkerOptions().position(p).title("HOLA").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_truck)));

            }
            else{
                this.googleMap.addMarker(new MarkerOptions().position(p).title("HOLA").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_truck_red)));
            }

        }
        */
        LatLng place2 = new LatLng(-34.875341, -56.199409);
        googleMap.addMarker(new MarkerOptions().position(place2).title("HOLA").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_truck_red)));




        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        MarkerOptions marker = new MarkerOptions()
                .position(place2)
                .title("Pedro Rodriguez")
                .snippet("info adicional pa poner")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_truck));
        googleMap.addMarker(marker);


       // this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(place2));
        //this.googleMap.animateCamera(zoom);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) mainView.findViewById(R.id.emain_map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }
}