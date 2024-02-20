package com.example.aklah.Schedule.Presenter;

import com.example.aklah.Model.Meal;
import com.example.aklah.Schedule.ScheduleView;

public interface SchedulePresenter {
    void getSatdayMeals();
    void getSundayMeals();
    void getMondayMeals();
    void getTuesdayMeals();
    void getWednesdayMeals();
    void getThursdayMeals();
    void getFridayMeals();

   // void deletesavedmeal(String idmeal,int day);
    void deleteMeal(Meal meal);
}
