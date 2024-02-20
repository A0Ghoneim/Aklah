package com.example.aklah.MealInfo.Presenter;

import com.example.aklah.Model.Meal;
import com.example.aklah.Network.MealNetworkCallback;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealInfoPresenter {

    void getMeal(String id);

    Single<Meal> getMealbyid(String id);

    Completable saveMeal(Meal meal);

    void deleteMeal(Meal meal);

}
