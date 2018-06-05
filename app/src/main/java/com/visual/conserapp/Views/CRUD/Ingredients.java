package com.visual.conserapp.Views.CRUD;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.visual.conserapp.Model.Food;
import com.visual.conserapp.Model.Ingredient;
import com.visual.conserapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 29/05/2018.
 */

public class Ingredients extends Crud {

    String toolbarTitle = "Ingredientes";
    List<Ingredient> ingredients;

    @Override
    protected void onCreateChild() {
        this.ingredients = new ArrayList<>();
    }

    @Override
    Object getDataFromSnapshot(DataSnapshot dataSnapshot) {
        return dataSnapshot.getValue(Ingredient.class);
    }

    @Override
    DatabaseReference createTable() {
        return database.getReference("Ingredient");
    }

    @Override
    void displayData() {
        crudAdapter = new CrudIngredientsAdapter(listasobject, this);
        recyclerView.setAdapter(crudAdapter);
    }

    @Override
    protected void collectDataCrud(DataSnapshot dataSnapshot) {
        for (DataSnapshot d : dataSnapshot.getChildren()) {
            Ingredient ingredient = d.getValue(Ingredient.class);
            ingredients.add(ingredient);
            listasobject.add((Object) ingredient);
        }
        displayData();
    }

    @Override
    String getToolbarTitle() {
        return toolbarTitle;
    }


}
