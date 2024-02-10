package com.example.aklah.Model;

public class IngredientPojo {
    public String idIngredient;
    public String strIngredient;

    public IngredientPojo() {
    }

    public IngredientPojo(String idIngredient, String strIngredient) {
        this.idIngredient = idIngredient;
        this.strIngredient = strIngredient;
    }

    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(String idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }

}
