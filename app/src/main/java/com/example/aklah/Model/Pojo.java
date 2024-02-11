package com.example.aklah.Model;

import java.util.ArrayList;

public class Pojo {

    private ArrayList<Meal> meals;


    public Pojo(ArrayList<Meal> products) {
        this.meals = products;
    }


    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }
}
