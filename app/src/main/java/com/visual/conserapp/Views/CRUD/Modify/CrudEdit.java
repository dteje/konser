package com.visual.conserapp.Views.CRUD.Modify;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebStorage;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.visual.conserapp.Memento.CareTaker;
import com.visual.conserapp.Memento.Memento;
import com.visual.conserapp.Memento.Originator;
import com.visual.conserapp.R;

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
    protected TextView txt_id;
    protected String newid;
    protected Map<String, Object> map;
    protected CareTaker careTaker;
    protected Originator originator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getIntent().getExtras().getString("id");
        map = new HashMap<>();

        setViewLayout(); //Set XML layout
        setToolbar();
        getItemsById();

        database = FirebaseDatabase.getInstance();
        table = getReferenceForDatabase();

        txt_id = (TextView) findViewById(R.id.crud_txt_id);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        //TODO Delete findviewsbyids de los de arriba in subclasses
        if (!id.equals("new")) {
            retrieveDataFromFirebase();
        } else { //Solo entra cuando es añadir
            modifyDisplayToNew();
        }
    }

    protected abstract void modifyDisplayToNew();

    protected abstract void createNewObject();


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


    public void onDelete(View view) {
        table.child(id).removeValue();
        finish();
    }

    public void onUpdate(View view) {
        updateObject();
        updateFirebase();
        finish();
    }

    private void updateFirebase() {

        table.updateChildren(map);
    }


    protected abstract void updateObject();

    protected abstract void setViewLayout();

    protected abstract String getToolbarTitle();

    protected abstract void getItemsById();

    protected abstract DatabaseReference getReferenceForDatabase();

    protected abstract void collectData(DataSnapshot dataSnapshot);

    protected abstract void displayDataOnScreen();


}
