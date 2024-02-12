package com.example.aklah;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.aklah.Home.Presenter.HomePresenter;
import com.example.aklah.Home.Presenter.HomePresenterImp;
import com.example.aklah.Home.View.HomeView;
import com.example.aklah.HomeFragmentDirections;
import com.example.aklah.Model.Database.MealLocalDataSourceImp;
import com.example.aklah.Model.Meal;
import com.example.aklah.Model.MealRepositoryImp;
import com.example.aklah.Network.MealNetworkCallback;
import com.example.aklah.Network.MealRemoteDataSourceImp;
import com.example.aklah.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements HomeView {


ImageView mealOfTheDayImageView;
TextView mealOfTheDayNameTextView;
CardView cardView;
HomePresenter homePresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mealOfTheDayImageView= view.findViewById(R.id.imageView);
        mealOfTheDayNameTextView=view.findViewById(R.id.mealOfDayNameText);
        cardView=view.findViewById(R.id.cardView);
        homePresenter= new HomePresenterImp(this,MealRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(getActivity())));
        homePresenter.getMealOfTheDay();
    }

    @Override
    public void showMeal(Meal meal) {
        Glide.with(getActivity()).load(meal.getStrMealThumb()).apply(new RequestOptions().override(500,500)).into(mealOfTheDayImageView);
        mealOfTheDayNameTextView.setText(meal.getStrMeal());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeFragmentDirections.ActionHomeFragmentToMealInfoFragment action = HomeFragmentDirections.actionHomeFragmentToMealInfoFragment();
                action.setMealData(meal);
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
}