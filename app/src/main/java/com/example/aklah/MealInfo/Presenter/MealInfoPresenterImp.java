package com.example.aklah.MealInfo.Presenter;

import android.util.Log;

import com.example.aklah.MealInfo.View.MealInfoView;
import com.example.aklah.Model.Meal;
import com.example.aklah.Model.MealRepository;
import com.example.aklah.Network.MealNetworkCallback;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealInfoPresenterImp implements MealInfoPresenter, MealNetworkCallback {

    MealInfoView view;

    MealRepository repo;

    public MealInfoPresenterImp(MealInfoView view, MealRepository repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void getMeal(String id) {
        repo.getMealById(this,id);
    }

    @Override
    public Single<Meal> getMealbyid(String id) {
        return repo.getMealById(id);
    }

    @Override
    public Completable saveMeal(Meal meal) {
        return repo.insertMeal(meal);
    }

    @Override
    public void deleteMeal(Meal meal) {
        repo.deleteMeal(meal);
    }

    @Override
    public void onSuccessResult(ArrayList<Meal> meals) {
        Log.i("TAG", "onSuccessResult: "+meals.size());
        view.showMeal(meals.get(0));
    }

    @Override
    public void onFaliureResult(String errormsg) {
        view.showerroe(errormsg);
    }
}
