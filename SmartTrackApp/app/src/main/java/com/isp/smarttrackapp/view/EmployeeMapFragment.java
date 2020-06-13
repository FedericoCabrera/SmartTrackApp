package com.isp.smarttrackapp.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.model.repository.local.LocalStorage;

public class EmployeeMapFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    private Context thisContext;
    private View mainView;
    private GoogleMap googleMap;
    private MapView mapView;

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
        return mainView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LocalStorage localStorage = LocalStorage.getInstance();
        LatLng place = new LatLng(-34.8856045, -56.1875722);

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        this.googleMap.addMarker(new MarkerOptions().position(place).title(localStorage.getValue("username")));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(place));
        this.googleMap.animateCamera(zoom);
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
