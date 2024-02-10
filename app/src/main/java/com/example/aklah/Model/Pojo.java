package com.example.aklah.Model;

import java.util.ArrayList;

public class Pojo {

    private ArrayList<MealPojo> meals;


    public Pojo(ArrayList<MealPojo> products) {
        this.meals = products;
    }


    public ArrayList<MealPojo> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<MealPojo> meals) {
        this.meals = meals;
    }
}
