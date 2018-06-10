package com.visual.conserapp.Memento;

import com.visual.conserapp.Model.Ingredient;

import java.util.ArrayList;

public class Memento {

    private ArrayList<Ingredient> listIngredient;

    public Memento(ArrayList<Ingredient> listIngredient) {
        this.listIngredient = listIngredient;
    }

    public ArrayList<Ingredient> getSavedListIngredient() {
        return listIngredient;
    }
}
