package com.visual.conserapp.Views.CRUD;

import android.graphics.Color;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.visual.conserapp.R;

/**
 * Created by daniel on 29/05/2018.
 */

public class Categories extends Crud {

    String toolbarTitle = "Categories";

    @Override
    Object getDataFromSnapshot(DataSnapshot dataSnapshot) {
        //return dataSnapshot.getValue(Category)
        return null;
    }


    @Override
    protected void onCreateChild() {

    }

    @Override
    void displayData() {

    }

    @Override
    protected void collectDataCrud(DataSnapshot dataSnapshot) {

    }

    @Override
    String getToolbarTitle() {
        return toolbarTitle;
    }

    @Override
    DatabaseReference createTable() {
        return database.getReference("Category");
    }


}
