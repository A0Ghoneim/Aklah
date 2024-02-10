package com.example.aklah.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PojoIng {
    @SerializedName("meals")
    private ArrayList<IngredientPojo> ingredients;


    public PojoIng(ArrayList<IngredientPojo> ingredients) {
        this.ingredients = ingredients;
    }


    public ArrayList<IngredientPojo> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<IngredientPojo> ingredients) {
        this.ingredients = ingredients;
    }
}
