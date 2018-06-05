package com.visual.conserapp.Views.CRUD.Retrieve;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.visual.conserapp.Model.User;
import com.visual.conserapp.ViewHolders.CRUDList.CrudUsersAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 29/05/2018.
 */

public class Users extends Crud {

    String toolbarTitle = "Usuarios";

    List<User> users;
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
        crudAdapter = new CrudUsersAdapter(objectsfiltered, this); //S
        recyclerView.setAdapter(crudAdapter);
    }

    @Override
    protected void collectDataCrud(DataSnapshot dataSnapshot) {
        for (DataSnapshot d : dataSnapshot.getChildren()) {
            User user = d.getValue(User.class);
            users.add(user);
            objects.add((Object) user);

        }
        objectsfiltered = objects;
        displayData();
    }

    @Override
    String getToolbarTitle() {
        return toolbarTitle;
    }

    void clearData(){
        usersfiltered.clear();
        objectsfiltered.clear();
    }

    @Override
    protected boolean search(String s) {
        clearData();
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
