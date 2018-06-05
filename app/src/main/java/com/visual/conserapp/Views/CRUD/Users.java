package com.visual.conserapp.Views.CRUD;

import android.graphics.Color;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.visual.conserapp.Model.Food;
import com.visual.conserapp.Model.User;
import com.visual.conserapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by daniel on 29/05/2018.
 */

public class Users extends Crud{

    String toolbarTitle = "Usuarios";
    List<User> users;

    @Override
    Object getDataFromSnapshot(DataSnapshot dataSnapshot) {
        return dataSnapshot.getValue(User.class);
    }

    @Override
    DatabaseReference createTable(){
        return database.getReference("User");
    }



    @Override
    protected void onCreateChild() {
        this.users = new ArrayList<>();

    }

    @Override
    void displayData() {
        crudAdapter = new CrudUsersAdapter(listasobject, this); //S
        recyclerView.setAdapter(crudAdapter);
    }

    @Override
    protected void collectDataCrud(DataSnapshot dataSnapshot) {
        for (DataSnapshot d : dataSnapshot.getChildren()) {
            User user = d.getValue(User.class);
            users.add(user);
            listasobject.add((Object) user);
        }
        displayData();
    }

    @Override
    String getToolbarTitle(){
        return toolbarTitle;
    }

}
