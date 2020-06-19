package com.isp.smarttrackapp.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.isp.smarttrackapp.R;
import com.isp.smarttrackapp.viewmodel.LoginFragmentViewModel;
import com.isp.smarttrackapp.viewmodel.MainFragmentViewModel;

import static android.os.SystemClock.sleep;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    private TextView textView;
    private ImageView imgView;

    private NavController navController;

    private Context thisContext;

    private MainFragmentViewModel viewModel;
    View viewAux = null;
    Thread animator;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);

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

        this.viewAux = view;
        textView = view.findViewById(R.id.mf_txtView);
        imgView = view.findViewById(R.id.mf_logo);

        Animation myAnim = AnimationUtils.loadAnimation(thisContext, R.anim.fade_in_long_duration);
        textView.startAnimation(myAnim);
        imgView.startAnimation(myAnim);

        //If needed make use of animation time to load app info.

        myAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                viewModel.initFCMToken();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                navController = Navigation.findNavController(viewAux);
                navController.navigate(R.id.action_mainFragment_to_loginFragment);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //TODO: Delete this commented code
        /*animator = new Thread(new Runnable() {
            @Override
            public void run() {

                sleep(2000);
            }
            // logic to make animation happen
        });
        animator.start();
        try {
            animator.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        /*view.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewAux.setVisibility(View.GONE);

            }
        }, 2000);*/


        /*Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(getResources().getInteger(R.integer.long_anim_time));
                    navController = Navigation.findNavController(viewAux);
                    navController.navigate(R.id.action_mainFragment_to_loginFragment);
                } catch (InterruptedException e) {
                    Toast.makeText(thisContext,"Ha ocurrido un error inesperado.",Toast.LENGTH_LONG);
                }
            }
        };

        thread.start();*/

        //getActivity().runOnUiThread(this.run());

                /*navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_mainFragment_to_loginFragment);*/


    }

}
