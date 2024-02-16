package com.example.aklah.Schedule;

import com.example.aklah.Model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface ScheduleView {
    void setSaturdayFlowable(Flowable<List<Meal>> flowable);
    void setSundayFlowable(Flowable<List<Meal>> flowable);
    void setMondayFlowable(Flowable<List<Meal>> flowable);
    void setTuesdayFlowable(Flowable<List<Meal>> flowable);
    void setWednesdayFlowable(Flowable<List<Meal>> flowable);
    void setThursdayFlowable(Flowable<List<Meal>> flowable);
    void setFridayFlowable(Flowable<List<Meal>> flowable);

}
