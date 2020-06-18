package com.isp.smarttrackapp.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.entities.ResponseModel;
import com.isp.smarttrackapp.viewmodel.EmployeeListFragmentViewModel;
import com.isp.smarttrackapp.viewmodel.MainEmployeeFragmentViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainEmployeeFragment extends Fragment {

    private Context thisContext;
    private View mainView;
    private NavController navController;
    private Button btnNewTraject;

    private LocationManager ubicacion;
    private MainEmployeeFragmentViewModel mainEmployeeFragmentViewModel;
    public MainEmployeeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        thisContext = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainEmployeeFragmentViewModel = new ViewModelProvider(this).get(MainEmployeeFragmentViewModel.class);
        mainView = inflater.inflate(R.layout.fragment_main_employee, container, false);
        return mainView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateLocation();
        navController = Navigation.findNavController(view);

        btnNewTraject = view.findViewById(R.id.em_btn_new_traject);

        btnNewTraject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navController.navigate(R.id.action_mainEmployeeFragment_to_employeeMapFragment);
                navController.navigate(R.id.action_mainEmployeeFragment_to_createIncidentFragment);
            }
        });
    }

    private void listProviders() {
        ubicacion = (LocationManager) thisContext.getSystemService(Context.LOCATION_SERVICE);
        List<String> listProvider = ubicacion.getAllProviders();

        String bestProvider = ubicacion.getBestProvider(bestCritiria(), false);

        LocationProvider provider = ubicacion.getProvider(listProvider.get(0));
    }

    private Criteria bestCritiria() {
        Criteria request = new Criteria();
        request.setAccuracy(Criteria.ACCURACY_FINE);
        request.setAltitudeRequired(true);
        return request;
    }

    private boolean statusGPS() {
        ubicacion = (LocationManager) thisContext.getSystemService(Context.LOCATION_SERVICE);
        if (!ubicacion.isProviderEnabled(LocationManager.GPS_PROVIDER))
            return false;
        return true;
    }

    private void updateLocation() {
        if(statusGPS()) {
            ubicacion = (LocationManager) thisContext.getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(thisContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(thisContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this.getActivity(), new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                }, 1000);
                if (ActivityCompat.checkSelfPermission(thisContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(thisContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            ubicacion.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, new myLocationListener());
        }else{
            Toast.makeText(thisContext, "El GPS esta apagado!", Toast.LENGTH_LONG).show();
        }
    }

    private class myLocationListener implements LocationListener{

        @Override
        public void onLocationChanged(final Location location) {
            try{
                mainEmployeeFragmentViewModel.updateLocation(location.getLatitude(), location.getLongitude()).observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
}
