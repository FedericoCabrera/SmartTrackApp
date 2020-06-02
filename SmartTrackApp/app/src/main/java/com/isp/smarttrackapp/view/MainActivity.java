package com.isp.smarttrackapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainActivityViewModel.initLocalStorage(this);
        //mainActivityViewModel.initFCMToken();
    }




}
