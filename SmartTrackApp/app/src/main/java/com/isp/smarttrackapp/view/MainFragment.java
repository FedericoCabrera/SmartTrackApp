package com.isp.smarttrackapp.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.isp.smarttrackapp.R;

import static android.os.SystemClock.sleep;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    private TextView textView;
    private ImageView imgView;

    private NavController navController;

    private Context thisContext;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        thisContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final View viewAux = view;
        textView = view.findViewById(R.id.mf_txtView);
        imgView = view.findViewById(R.id.mf_logo);

        Animation myAnim = AnimationUtils.loadAnimation(thisContext, R.anim.fade_in_long_duration);
        textView.startAnimation(myAnim);
        imgView.startAnimation(myAnim);

        Thread thread = new Thread(){
            public void run(){
                try {
                    //If needed make use of animation time to load app info.
                    //Otherwise keep this value for animation purposes
                    sleep(getResources().getInteger(R.integer.long_anim_time));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    navController = Navigation.findNavController(viewAux);
                    navController.navigate(R.id.action_mainFragment_to_loginFragment);
                }
            }
        };

        thread.start();
        /*navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_mainFragment_to_loginFragment);*/

    }
}
