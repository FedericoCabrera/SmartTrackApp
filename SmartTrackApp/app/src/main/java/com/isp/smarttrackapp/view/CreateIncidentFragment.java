package com.isp.smarttrackapp.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.isp.smarttrackapp.Config;
import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.entities.Incident;
import com.isp.smarttrackapp.entities.Position;
import com.isp.smarttrackapp.entities.ResponseModelWithData;
import com.isp.smarttrackapp.model.repository.local.LocalStorage;
import com.isp.smarttrackapp.utils.Utils;
import com.isp.smarttrackapp.viewmodel.CreateIncidentFragmentViewModel;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
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

    private String pathToFile;
    private String mCameraFileName;
    private int CAPTURE_IMAGE_REQUEST = 1;
    private File photoFile;
    private Uri photoUri;

    private String lat = "";
    private String lng = "";
    private String streetName = "";
    private String streetNumber = "";

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
        this.lat = LocalStorage.getInstance().getValue(Config.KEY_LAST_LATITUDE);
        this.lng = LocalStorage.getInstance().getValue(Config.KEY_LAST_LONGITUDE);
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
                //takePicture();
                //takeFullPicture();
                //cameraIntent();
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

    private void takePicture() {
        if(checkCameraHardware(thisContext)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,0);

        }else{
            Toast.makeText(thisContext,"Cámara no disponible.", Toast.LENGTH_SHORT).show();
        }

    }

    private void takeFullPicture() {
        if(checkCameraHardware(thisContext)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            File photoFile = createPhotoFile();

            if(photoFile!=null){
                this.pathToFile = photoFile.getAbsolutePath();
                Uri photoURI = Uri.fromFile(photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(intent,0);
            }else{
                Toast.makeText(thisContext,"Error al guardar la imagen.", Toast.LENGTH_SHORT).show();
            }

            //startActivityForResult(intent,0);

/*            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.example.android.fileprovider",
                    photoFile);

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);*/
        }else{
            Toast.makeText(thisContext,"Cámara no disponible.", Toast.LENGTH_SHORT).show();
        }
    }

    private void cameraIntent() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        Date date = new Date();
        DateFormat df = new SimpleDateFormat("-mm-ss");

        String newPicFile = df.format(date) + ".jpg";
        String outPath = "/sdcard/" + newPicFile;
        File outFile = new File(outPath);

        mCameraFileName = outFile.toString();
        Uri outuri = Uri.fromFile(outFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outuri);
        startActivityForResult(intent, CAPTURE_IMAGE_REQUEST);
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

            /*newIncident.setBase64Image(base64Image);
            newIncident.setDescription(description);
            newIncident.setAddress(address);*/

            createIncidentFragmentViewModel.assignIncidentToTraject(newIncident).observe(getViewLifecycleOwner(), new Observer<ResponseModelWithData<String>>() {
                @Override
                public void onChanged(ResponseModelWithData<String> stringResponseModelWithData) {
                    if(stringResponseModelWithData.isResponseOK()){
                        Toast.makeText(thisContext,"Incidente agregado correctamente.",Toast.LENGTH_SHORT).show();
                        btnAddIncident.setClickable(true);
                        getActivity().onBackPressed();
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
        // FUNCIONANDO
        /*if(resultCode == RESULT_OK && data!=null){
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");

            imgViewPhoto.setImageBitmap(bitmap);
        }*/
        /*if(resultCode == RESULT_OK && data!=null){
            //Bitmap bitmap = (Bitmap)data.getExtras().get("data");

            Bitmap fullBitmap = BitmapFactory.decodeFile(pathToFile);
            imgViewPhoto.setImageBitmap(fullBitmap);
        }*/

        //--------------------------------------------
/*        if (requestCode == CAPTURE_IMAGE_REQUEST) {
            if (data != null) {
                Uri image = data.getData();
                imgViewPhoto.setImageURI(image);
                imgViewPhoto.setVisibility(View.VISIBLE);
            }
            if (imgViewPhoto == null && mCameraFileName != null) {
                Uri image = Uri.fromFile(new File(mCameraFileName));
                imgViewPhoto.setImageURI(image);
                imgViewPhoto.setVisibility(View.VISIBLE);
            }
            File file = new File(mCameraFileName);
            if (!file.exists()) {
                file.mkdir();
            }
        }*/
        //-----------------------------------------------
        if (requestCode == CAPTURE_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            imgViewPhoto.setImageBitmap(myBitmap);
        } else {
            Toast.makeText(thisContext, "Error al guardar la fotografía.", Toast.LENGTH_SHORT);
        }

    }

    private void dispatchPictureTakerAction(){
        Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        try{
            //photoFile = createPhotoFile();
        }catch (Exception e){

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
            Toast.makeText(thisContext,"Cámara no disponible.", Toast.LENGTH_SHORT);
        }
        return image;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        thisContext = context;
    }
}
