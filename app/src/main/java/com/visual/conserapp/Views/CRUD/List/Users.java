package com.visual.conserapp.Views.CRUD.List;

import android.content.Intent;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.visual.conserapp.Model.User;
import com.visual.conserapp.ViewHolders.CRUDList.CrudUsersAdapter;
import com.visual.conserapp.Views.CRUD.Modify.CrudEditIngredients;
import com.visual.conserapp.Views.CRUD.Modify.CrudEditUsers;

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
    protected void onCreateChild() {
        this.users = new ArrayList<>();
        this.usersfiltered = new ArrayList<>();
    }

    @Override
    Object getDataFromSnapshot(DataSnapshot dataSnapshot) {
        return dataSnapshot.getValue(User.class);
    }

    @Override
    DatabaseReference createTable() {
        return database.getReference("User");
    }

    @Override
    protected void setFABOnClick() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CrudEditUsers.class);
                intent.putExtra("id","new");
                intent.putExtra("newid",newId+"");
                startActivity(intent);
            }
        });
    }

    @Override
    void displayData() {
        crudAdapter = new CrudUsersAdapter(objects, this); //S
        recyclerView.setAdapter(crudAdapter);
    }

    @Override
    protected void collectDataCrud(DataSnapshot dataSnapshot) {
        clearData(true);
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

    void clearData(boolean all) {
        usersfiltered.clear();
        objectsfiltered.clear();
        objects.clear();
        if(all)users.clear();
    }

    @Override
    protected boolean search(String s) {
        clearData(false);
        s = s.toLowerCase();
        boolean flag = false;
        for (User u : users) {
            if (u.getName().toLowerCase().contains(s) || u.getEmail().toLowerCase().contains(s)) {
                usersfiltered.add(u);
                objectsfiltered.add((Object) u);
                flag = true;
            }
        }
        displayData();
        return flag;
    }
}
