package com.visual.conserapp.Views.CRUD.Update;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.visual.conserapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by daniel on 06/06/2018.
 */

public abstract class CrudEdit extends AppCompatActivity {

    protected Toolbar toolbar;
    protected FirebaseDatabase database;
    protected DatabaseReference table;
    protected String id;
    protected Button btn_update, btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getIntent().getExtras().getString("id");

        map = new HashMap<>();
        //map.clear();

        setViewLayout(); //Set XML layout
        setToolbar();
        getItemsById();

        database = FirebaseDatabase.getInstance();
        table = getReferenceForDatabase();

        retrieveDataFromFirebase();


    }



    private void retrieveDataFromFirebase() {
        table.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                collectData(dataSnapshot);
                displayDataOnScreen();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setToolbar() {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(getToolbarTitle());
        setSupportActionBar(toolbar);
    }




    public void onDelete(View view){
        table.child(id).removeValue();
        finish();
    }

    public void onUpdate(View view){
        updateObject();
        updateFirebase();
        finish();
    }

    private void updateFirebase() {
        table.updateChildren(map);
    }

    protected Map<String, Object> map;
    protected abstract void updateObject();
    protected abstract void setViewLayout();
    protected abstract String getToolbarTitle();
    protected abstract void getItemsById();
    protected abstract DatabaseReference getReferenceForDatabase();
    protected abstract void collectData(DataSnapshot dataSnapshot);
    protected abstract void displayDataOnScreen();



}
