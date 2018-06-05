package com.visual.conserapp.Views.CRUD;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.visual.conserapp.Model.Food;
import com.visual.conserapp.R;
import com.visual.conserapp.ViewHolders.RequestAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by daniel on 29/05/2018.
 */

public class Foods extends Crud {

    String toolbarTitle = "Platos";
    List<Food> foods;

    @Override
    protected void onCreateChild() {
        this.foods = new ArrayList<>();
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
        crudAdapter = new CrudFoodAdapter(listasobject, this); //S
        recyclerView.setAdapter(crudAdapter);
    }


    @Override
    protected void collectDataCrud(DataSnapshot dataSnapshot) {
        for (DataSnapshot d : dataSnapshot.getChildren()) {
            Food f = d.getValue(Food.class);
            foods.add(f);
            listasobject.add((Object) f);
        }
        displayData();
    }

    @Override
    String getToolbarTitle() {
        return toolbarTitle;
    }


}
