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
        googleMap2 = googleMap;
        LatLng place = new LatLng(-34, -57);
        googleMap2.addMarker(new MarkerOptions()
                .position(place)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_truck_red))
                .title("s"));
        employeeList = new ArrayList<Employee>();
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(1);
        adminMapViewModel.getLocation().observe(getViewLifecycleOwner(), new Observer<ResponseModelWithData<List<Employee>>>() {
            @Override
                public void onChanged(ResponseModelWithData<List<Employee>> employees) {
                    ArrayAdapter<Employee> arrayAdapter = new ArrayAdapter<Employee>(thisContext, android.R.layout.simple_list_item_1, employees.getData() );
                    employeeList = employees.getData();

                       LatLng place = new LatLng(-39, -58);
                       googleMap2.addMarker(new MarkerOptions()
                               .position(place)
                               .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_truck_red))
                               .title("s"));



                CameraUpdate zoom = CameraUpdateFactory.zoomTo(1);
                googleMap2.addMarker(new MarkerOptions()
                        .position(place)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_truck_red))
                        .title("dd"));
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
}