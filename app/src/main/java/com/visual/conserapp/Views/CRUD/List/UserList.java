package com.visual.conserapp.Views.CRUD.List;

import android.content.Intent;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.visual.conserapp.Model.User;
import com.visual.conserapp.Adapter.CRUDList.CrudUsersAdapter;
import com.visual.conserapp.Views.CRUD.Modify.CrudEditUsers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 29/05/2018.
 */

public class UserList extends CrudList {

    String toolbarTitle = "Usuarios";

    List<User> users, usersfiltered;

    @Override
    protected void onCreateChild() {
        this.users = new ArrayList<>();
        this.usersfiltered = new ArrayList<>();
    }

    @Override
    protected DatabaseReference getTableFromSubclass() {
        return database.getReference("User");
    }

    @Override
    protected void setFABOnClick() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CrudEditUsers.class);
                intent.putExtra("id","new");
                intent.putExtra("newid","0");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void displayData() {
        crudAdapter = new CrudUsersAdapter(objects, this);
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
    protected String getToolbarTitleFromSubclass() {
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
