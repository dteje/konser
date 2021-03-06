package com.visual.conserapp.Views.CRUD.List;

import android.content.Intent;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.visual.conserapp.Model.Food;
import com.visual.conserapp.Adapter.CRUDList.CrudFoodAdapter;
import com.visual.conserapp.Views.CRUD.Modify.CrudEditFood;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 29/05/2018.
 */

public class FoodList extends CrudList {

    String toolbarTitle = "Platos";

    List<Food> foods, foodsfiltered;


    @Override
    protected void onCreateChild() {
        this.foods = new ArrayList<>();
        this.foodsfiltered = new ArrayList<>();
    }

    @Override
    protected DatabaseReference getTableFromSubclass() {
        return database.getReference("Food");
    }

    @Override
    protected void setFABOnClick() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CrudEditFood.class);
                intent.putExtra("id", "new");
                intent.putExtra("newid", newId + "");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void displayData() {
        crudAdapter = new CrudFoodAdapter(objects, this);
        recyclerView.setAdapter(crudAdapter);
    }

    @Override
    protected void collectDataCrud(DataSnapshot dataSnapshot) {
        clearData(true);
        for (DataSnapshot d : dataSnapshot.getChildren()) {
            Food f = d.getValue(Food.class);
            foods.add(f);
            objects.add((Object) f);
            newId = Integer.parseInt(f.getID());
        }
        newId++;
        objectsfiltered = objects;
        displayData();
    }

    @Override
    protected String getToolbarTitleFromSubclass() {
        return toolbarTitle;
    }

    void clearData(boolean all) {
        foodsfiltered.clear();
        objectsfiltered.clear();
        objects.clear();
        if(all)foods.clear();
    }

    @Override
    protected boolean search(String s) {
        clearData(false);
        s = s.toLowerCase();
        boolean flag = false;
        for (Food f : foods) {
            if (f.getName().toLowerCase().contains(s) || f.getPrice().contains(s) || f.getID().contains(s)) {
                foodsfiltered.add(f);
                objectsfiltered.add((Object) f);
                flag = true;
            }
        }
        displayData();
        return flag;
    }

}
