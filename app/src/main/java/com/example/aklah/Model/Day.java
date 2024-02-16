package com.example.aklah.Model;

import java.util.ArrayList;

public class Day {
    private String name;
    private ArrayList<String> mealIDs;

    public Day(String name) {
        this.name = name;
        mealIDs=new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getMealIDs() {
        return mealIDs;
    }

    public void setMealIDs(ArrayList<String> mealIDs) {
        this.mealIDs = mealIDs;
    }
}
