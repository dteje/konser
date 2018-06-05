package com.visual.conserapp.Views.CRUD.Retrieve;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.visual.conserapp.Model.Ingredient;
import com.visual.conserapp.ViewHolders.CRUDList.CrudIngredientsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 29/05/2018.
 */

public class Ingredients extends Crud {

    String toolbarTitle = "Ingredientes";

    List<Ingredient> ingredients;
    List<Ingredient> ingredientsfiltered;

    @Override
    protected void onCreateChild() {
        this.ingredients = new ArrayList<>();
        this.ingredientsfiltered = new ArrayList<>();
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
        crudAdapter = new CrudIngredientsAdapter(objects, this);
        recyclerView.setAdapter(crudAdapter);
    }

    @Override
    protected void collectDataCrud(DataSnapshot dataSnapshot) {
        for (DataSnapshot d : dataSnapshot.getChildren()) {
            Ingredient ingredient = d.getValue(Ingredient.class);
            ingredients.add(ingredient);
            objects.add((Object) ingredient);
        }
        objectsfiltered = objects;
        displayData();
    }

    @Override
    String getToolbarTitle() {
        return toolbarTitle;
    }

    @Override
    protected boolean search(String s) {
        clearData();
        boolean flag = false;
        for(Ingredient i : ingredients){
            if(i.getName().contains(s) || i.getType().contains(s) ){
                ingredientsfiltered.add(i);
                objectsfiltered.add((Object) i);
                flag = true;
            }
        }
        displayData();
        return flag;
    }

    void clearData(){
        ingredients.clear();
        objectsfiltered.clear();
    }




}
