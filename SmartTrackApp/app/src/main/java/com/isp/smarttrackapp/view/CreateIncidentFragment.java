package com.isp.smarttrackapp.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.entities.Incident;
import com.isp.smarttrackapp.entities.Position;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.utils.Utils;
import com.isp.smarttrackapp.viewmodel.CreateIncidentFragmentViewModel;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static android.os.Environment.DIRECTORY_PICTURES;

public class CreateIncidentFragment  extends Fragment {

    private Context thisContext;
    private CreateIncidentFragmentViewModel createIncidentFragmentViewModel;

    private Button btnAddIncident;
    private Button btnCancel;
    private ImageButton btnCamera;
    private TextInputEditText txtInputLocation;
    private TextInputEditText txtInputDescription;
    private ImageView imgViewPhoto;

    private int CAPTURE_IMAGE_REQUEST = 1;
    private File photoFile;
    private Uri photoUri;

    private String lat = "";
    private String lng = "";
    private String streetName = "";
    private String streetNumber = "";

    private NavController navController;

    public CreateIncidentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        createIncidentFragmentViewModel = new ViewModelProvider(this).get(CreateIncidentFragmentViewModel.class);

        return inflater.inflate(R.layout.fragment_create_incident, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        btnAddIncident = view.findViewById(R.id.ci_btn_addIncident);
        btnCancel = view.findViewById(R.id.ci_btn_cancel);
        btnCamera = view.findViewById(R.id.ci_img_btn);
        txtInputDescription = view.findViewById(R.id.ci_txt_input_description);
        txtInputLocation = view.findViewById(R.id.ci_txt_input_location);
        imgViewPhoto = view.findViewById(R.id.ci_img_view);

        setPosition();

        loadClickListeners();
    }

    private void setPosition(){
        this.lat = createIncidentFragmentViewModel.getLastLatitudeValue();
        this.lng = createIncidentFragmentViewModel.getLastLongitudeValue();
        this.streetName = "";
        this.streetNumber = "";
        Geocoder geocoder = new Geocoder(thisContext, Locale.getDefault());
        List<Address> addresses;
        try{
            addresses = geocoder.getFromLocation(Double.parseDouble(lat),Double.parseDouble(lng),10);
            for(Address adr: addresses){
                if(adr.getLocality() != null && adr.getLocality().length() > 0){
                    streetName = adr.getThoroughfare();
                    streetNumber = adr.getSubThoroughfare();
                    break;
                }
            }
        }catch(Exception e) {
            txtInputLocation.setText(lat + " " + lng);
        }
        txtInputLocation.setText(streetName + " "+ streetNumber);
    }

    private void loadClickListeners(){

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });

        btnAddIncident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewIncident();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void captureImage(){
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        try{
            photoFile = createPhotoFile();
            if(photoFile!=null){
                photoUri = FileProvider.getUriForFile(thisContext,"com.isp.smarttrackapp.fileprovider",photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(intent, CAPTURE_IMAGE_REQUEST);
            }
        }catch (Exception ex){
            Toast.makeText(thisContext,"Cámara no disponible.", Toast.LENGTH_SHORT).show();
        }
    }

    private void addNewIncident(){

        try{
            this.btnAddIncident.setClickable(false);

            String address = this.txtInputLocation.getText().toString();
            String description = this.txtInputDescription.getText().toString();

            String base64Image = "";
            if(photoFile!=null)
                base64Image = Utils.encodeFileToBase64Binary(this.photoFile);

            Position location = new Position();
            location.setLatitude(Double.parseDouble(this.lat));
            location.setLongitude(Double.parseDouble(this.lng));
            location.setAddress(address);

            Incident newIncident = new Incident(base64Image,description,location);

            createIncidentFragmentViewModel.assignIncidentToTraject(newIncident).observe(getViewLifecycleOwner(), new Observer<ResponseModelWithData<String>>() {
                @Override
                public void onChanged(ResponseModelWithData<String> stringResponseModelWithData) {
                    if(stringResponseModelWithData.isResponseOK()){
                        Toast.makeText(thisContext,"Incidente agregado correctamente.",Toast.LENGTH_SHORT).show();
                        btnAddIncident.setClickable(true);
                        //getActivity().onBackPressed();
                        navController.navigate(R.id.action_createIncidentFragment_to_employeeMapFragment);
                    }else{
                        Toast.makeText(thisContext,"Error al agregar incidente.",Toast.LENGTH_SHORT).show();
                        btnAddIncident.setClickable(true);
                    }
                }
            });

        }catch(Exception ex){

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            imgViewPhoto.setImageBitmap(myBitmap);
        } else {
            Toast.makeText(thisContext, "Error al guardar la fotografía.", Toast.LENGTH_SHORT).show();
        }

    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
            return true;
        } else {
            return false;
        }
    }

    private File createPhotoFile(){
        String name = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        File storageDir = thisContext.getExternalFilesDir(DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(name,"jpg",storageDir);
        } catch (IOException e) {
            Toast.makeText(thisContext,"Cámara no disponible.", Toast.LENGTH_SHORT).show();
        }
        return image;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        thisContext = context;
    }
}
