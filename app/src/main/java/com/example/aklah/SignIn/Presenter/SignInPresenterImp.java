package com.example.aklah.SignIn.Presenter;

import com.example.aklah.Model.Meal;
import com.example.aklah.Model.MealRepository;

import io.reactivex.rxjava3.core.Completable;

public class SignInPresenterImp implements SignInPresenter {
    MealRepository repo;

    public SignInPresenterImp(MealRepository repo) {
        this.repo = repo;
    }

    @Override
    public Completable insert(Meal meal) {
        return repo.insertMeal(meal);
    }
}
