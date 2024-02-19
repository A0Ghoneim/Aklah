package com.example.aklah.Home.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.aklah.Home.Presenter.HomePresenter;
import com.example.aklah.Home.Presenter.HomePresenterImp;
import com.example.aklah.Home.View.HomeView;
import com.example.aklah.Home.View.HomeFragmentDirections;
import com.example.aklah.InternetCheckTask;
import com.example.aklah.MainActivity;
import com.example.aklah.Model.Database.MealLocalDataSourceImp;
import com.example.aklah.Model.Meal;
import com.example.aklah.Model.MealRepositoryImp;
import com.example.aklah.Network.MealNetworkCallback;
import com.example.aklah.Network.MealRemoteDataSourceImp;
import com.example.aklah.R;
import com.example.aklah.SignIn.View.Signin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Objects;


public class HomeFragment extends Fragment implements HomeView {

    ImageView sticker;
    TextView network;
ImageView mealOfTheDayImageView;
TextView mealOfTheDayNameTextView;
CardView cardView;

CardView profileCard;
ImageView profileImage;
TextView profileNameText;
Button signOutBtn;
HomePresenter homePresenter;
int guest;
    SharedPreferences sp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getActivity().getSharedPreferences("myapp",0);
        guest = sp.getInt("guest",0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileCard=view.findViewById(R.id.profile_card_view);
        if (guest==1){
            profileCard.setVisibility(View.GONE);
        }
        sticker=view.findViewById(R.id.imageView3);
        network=view.findViewById(R.id.textView5);

        mealOfTheDayImageView= view.findViewById(R.id.imageView);
        mealOfTheDayNameTextView=view.findViewById(R.id.mealOfDayNameText);
        cardView=view.findViewById(R.id.cardView);
        InternetCheckTask internetCheckTask = new InternetCheckTask(new InternetCheckTask.InternetCheckListener() {
            @Override
            public void onInternetCheckResult(boolean isInternetAvailable) {
                if (!isInternetAvailable){
                    profileCard.setVisibility(View.GONE);
                    cardView.setVisibility(View.GONE);
                    TextView t = view.findViewById(R.id.mealOfTheDayText);
                    t.setVisibility(View.GONE);
                    sticker.setVisibility(View.VISIBLE);
                    network.setVisibility(View.VISIBLE);
                }

            }
        });

        internetCheckTask.execute();

//        if (!connectionCheck()){
//            profileCard.setVisibility(View.GONE);
//            cardView.setVisibility(View.GONE);
//            TextView t = view.findViewById(R.id.mealOfTheDayText);
//            t.setVisibility(View.GONE);
//            sticker.setVisibility(View.VISIBLE);
//            network.setVisibility(View.VISIBLE);
//
//        }
        profileImage=view.findViewById(R.id.profile_img);
        profileNameText=view.findViewById(R.id.profile_name);
        signOutBtn=view.findViewById(R.id.sign_out);





        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            Log.i("Profile", "onViewCreated: ");
            if (!user.getDisplayName().equals("")){
                Log.i("Profile", "onViewCreated: "+user.getEmail());
                profileNameText.setText(user.getDisplayName());
            }else {
                Log.i("Profile", "onViewCreated: "+user.getEmail());
                profileNameText.setText(user.getEmail());
               // profileNameText.setVisibility(View.GONE);
            }
            if (user.getPhotoUrl()!=null){
                Glide.with(getActivity()).load(user.getPhotoUrl()).apply(new RequestOptions().override(1000,1000)).into(profileImage);
            }
            signOutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    homePresenter.clearEverything();
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getActivity(), Signin.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
        }
        homePresenter= new HomePresenterImp(this,MealRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(getActivity())));

        long hideAdUntil = sp.getLong("limit", 0);
        if (hideAdUntil==0){
            homePresenter.getMealOfTheDay();
            sp.edit().putLong("limit", System.currentTimeMillis() + 86400000).apply();
        }
        if(System.currentTimeMillis() > hideAdUntil) {
            homePresenter.getMealOfTheDay();
            sp.edit().putLong("limit", System.currentTimeMillis() + 86400000).apply();
        }
        else {
           String mealid = sp.getString("MealID","0");
           if (!mealid.equals("0")){
               homePresenter.getMealByID(mealid);
           }

        }
    }

    @Override
    public void showRandomMeal(Meal meal) {
        sp.edit().putString("MealID", meal.getIdMeal()).apply();
        Glide.with(requireActivity()).load(meal.getStrMealThumb()).apply(new RequestOptions().override(500,500)).into(mealOfTheDayImageView);
        mealOfTheDayNameTextView.setText(meal.getStrMeal());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragmentDirections.ActionHomeFragmentToMealInfoFragment action = HomeFragmentDirections.actionHomeFragmentToMealInfoFragment();
                action.setMealID(meal.getIdMeal());
                Navigation.findNavController(view).navigate(action);
               /* FragmentADirections.ActionFragmentAToFragmentB actionFragmentAToFragmentB = FragmentADirections.actionFragmentAToFragmentB();
                actionFragmentAToFragmentB.setData(d);
                Navigation.findNavController(view).navigate(actionFragmentAToFragmentB);*/
            }
        });
    }

    @Override
    public void showStoredMeal(Meal meal) {
        Glide.with(requireActivity()).load(meal.getStrMealThumb()).apply(new RequestOptions().override(500,500)).into(mealOfTheDayImageView);
        mealOfTheDayNameTextView.setText(meal.getStrMeal());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeFragmentDirections.ActionHomeFragmentToMealInfoFragment action = HomeFragmentDirections.actionHomeFragmentToMealInfoFragment();
                action.setMealID(meal.getIdMeal());
                Navigation.findNavController(view).navigate(action);
               /* FragmentADirections.ActionFragmentAToFragmentB actionFragmentAToFragmentB = FragmentADirections.actionFragmentAToFragmentB();
                actionFragmentAToFragmentB.setData(d);
                Navigation.findNavController(view).navigate(actionFragmentAToFragmentB);*/
            }
        });
    }

    @Override
    public void showErrorMsg(String error) {

    }
//    boolean connectionCheck(){
//        if (NetworkIsConnected()&&InternetIsConnected()){
//            return true;
//        }
//        return false;
//    }
    private boolean NetworkIsConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    private boolean InternetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }
}