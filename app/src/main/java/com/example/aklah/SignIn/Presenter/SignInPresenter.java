package com.example.aklah.SignIn.Presenter;

import com.example.aklah.Model.Meal;

import io.reactivex.rxjava3.core.Completable;

public interface SignInPresenter {

    Completable insert(Meal meal);
}
