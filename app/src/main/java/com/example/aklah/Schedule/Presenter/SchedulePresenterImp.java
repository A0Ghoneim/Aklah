package com.example.aklah.Schedule.Presenter;

import com.example.aklah.Model.Meal;
import com.example.aklah.Model.MealRepository;
import com.example.aklah.Schedule.ScheduleView;

public class SchedulePresenterImp implements SchedulePresenter{

    ScheduleView view;
    MealRepository repo;

    public SchedulePresenterImp(ScheduleView view, MealRepository repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void getSatdayMeals() {
        view.setSaturdayFlowable(repo.getMealByDay(1));
    }

    @Override
    public void getSundayMeals() {
        view.setSundayFlowable(repo.getMealByDay(2));
    }

    @Override
    public void getMondayMeals() {
        view.setMondayFlowable(repo.getMealByDay(3));
    }

    @Override
    public void getTuesdayMeals() {
        view.setTuesdayFlowable(repo.getMealByDay(4));
    }

    @Override
    public void getWednesdayMeals() {
        view.setWednesdayFlowable(repo.getMealByDay(5));
    }

    @Override
    public void getThursdayMeals() {
        view.setThursdayFlowable(repo.getMealByDay(6));
    }

    @Override
    public void getFridayMeals() {
        view.setFridayFlowable(repo.getMealByDay(7));
    }

    @Override
    public void deleteMeal(Meal meal) {
        repo.deleteMeal(meal);
    }

  /*  @Override
    public void deletesavedmeal(String idmeal, int day) {
        repo.deletesavedmeal(idmeal,day);
    }*/
}
