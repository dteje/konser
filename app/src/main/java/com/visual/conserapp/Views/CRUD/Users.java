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

public class Users extends Crud {

    String toolbarTitle = "Usuarios";

    List<User> users;

    List<String> names;
    List<String> emails;
    List<String> admins;

    List<User> usersfiltered;

    @Override
    Object getDataFromSnapshot(DataSnapshot dataSnapshot) {
        return dataSnapshot.getValue(User.class);
    }

    @Override
    DatabaseReference createTable() {
        return database.getReference("User");
    }


    @Override
    protected void onCreateChild() {
        this.users = new ArrayList<>();
        this.usersfiltered = new ArrayList<>();

    }

    @Override
    void displayData() {
        recyclerView.setAdapter(null);
        crudAdapter = new CrudUsersAdapter(objectsfiltered, this); //S
        recyclerView.setAdapter(crudAdapter);
    }

    @Override
    protected void collectDataCrud(DataSnapshot dataSnapshot) {
        for (DataSnapshot d : dataSnapshot.getChildren()) {
            User user = d.getValue(User.class);
            users.add(user);
            listasobject.add((Object) user);

        }
        objectsfiltered = listasobject;
        displayData();
    }

    @Override
    String getToolbarTitle() {
        return toolbarTitle;
    }

    @Override
    protected boolean search(String s) {
        boolean flag = false;
        for(User u : users){
            if(u.getName().contains(s) || u.getEmail().contains(s)){
                usersfiltered.add(u);
                objectsfiltered.add((Object) u);
                flag = true;
            }
        }
        displayData();
        return flag;
    }


}
