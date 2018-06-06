package com.visual.conserapp.Views.CRUD.Retrieve;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.visual.conserapp.Model.Food;
import com.visual.conserapp.ViewHolders.CRUDList.CrudFoodAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 29/05/2018.
 */

public class Foods extends Crud {

    String toolbarTitle = "Platos";

    List<Food> foods;
    List<Food> foodsfiltered;

    @Override
    protected void onCreateChild() {
        this.foods = new ArrayList<>();
        this.foodsfiltered = new ArrayList<>();
    }

    @Override
    Object getDataFromSnapshot(DataSnapshot dataSnapshot) {
        return dataSnapshot.getValue(Food.class);
    }

    @Override
    DatabaseReference createTable() {
        return database.getReference("Food");
    }

    @Override
    void displayData() {
        crudAdapter = new CrudFoodAdapter(objects, this); //S
        recyclerView.setAdapter(crudAdapter);
    }


    @Override
    protected void collectDataCrud(DataSnapshot dataSnapshot) {
        clearData();
        for (DataSnapshot d : dataSnapshot.getChildren()) {
            Food f = d.getValue(Food.class);
            foods.add(f);
            objects.add((Object) f);
        }
        objectsfiltered = objects;
        displayData();
    }

    @Override
    String getToolbarTitle() {
        return toolbarTitle;
    }

    void clearData(){
        foodsfiltered.clear();
        objectsfiltered.clear();
        objects.clear();
    }

    @Override
    protected boolean search(String s) {
        clearData();
        boolean flag = false;
        for(Food f : foods){
            if(f.getName().contains(s) || f.getPrice().contains(s) || f.getID().contains(s)){
                foodsfiltered.add(f);
                objectsfiltered.add((Object) f);
                flag = true;
            }
        }
        displayData();
        return flag;
    }
}
