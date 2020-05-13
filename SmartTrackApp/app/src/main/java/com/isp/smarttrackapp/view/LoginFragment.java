package com.isp.smarttrackapp.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.model.entities.Value;
import com.isp.smarttrackapp.model.repository.IValuesApiService;
import com.isp.smarttrackapp.Config;
import com.isp.smarttrackapp.model.repository.RetrofitHelper;
import com.isp.smarttrackapp.viewmodel.ValuesViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private TextView textView;
    private Context thisContext;
    private ValuesViewModel valuesViewModel;

    public LoginFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        thisContext = getActivity();
        valuesViewModel = new ViewModelProvider(this).get(ValuesViewModel.class);

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = view.findViewById(R.id.login_title);
        observeViewModel(this.valuesViewModel);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        thisContext = context;
    }

    private void observeViewModel(ValuesViewModel valuesViewModel) {

        valuesViewModel.getValuesListObservable().observe(getViewLifecycleOwner(), new Observer<List<Value>>() {
            @Override
            public void onChanged(List<Value> values) {
                if (values != null) {
                    for (Value s : values) {
                        Toast.makeText(thisContext, s.getV1() + " " + s.getV2(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

}
