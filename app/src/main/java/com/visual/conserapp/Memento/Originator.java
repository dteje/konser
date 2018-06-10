package com.visual.conserapp.Memento;

import android.util.Log;

import com.visual.conserapp.Model.Ingredient;

import java.util.ArrayList;

public class Originator {

    private ArrayList<Ingredient> listIngredient;

    public void setListIngredient(ArrayList<Ingredient> listIngredient) {
        Log.d("", "ORIGINATOR> Saving new state: " + listIngredient.toString());
        this.listIngredient = listIngredient;
    }

    public Memento storeInMemento() {
        Log.d("", "ORIGINATOR> Saving to memento");
        return new Memento(listIngredient);
    }

    public ArrayList<Ingredient> restoreFromMemento(Memento memento) {

        Log.d("", "ORIGINATOR> restoring from memento: " + listIngredient.toString());
        listIngredient = memento.getSavedListIngredient();

        return listIngredient;
    }

}
