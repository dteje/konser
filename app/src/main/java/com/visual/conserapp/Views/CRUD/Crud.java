package com.visual.conserapp.Views.CRUD;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.visual.conserapp.R;
import com.visual.conserapp.Views.AdminHome;
import com.visual.conserapp.Views.AdminPanel;
import com.visual.conserapp.Views.Home;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by daniel on 29/05/2018.
 */

public abstract class Crud extends AppCompatActivity {

    protected CrudAdapter crudAdapter;
    protected List<Object> listasobject;
    protected String name;
    protected String id;
    protected FirebaseDatabase database;
    protected DatabaseReference table;
    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;


    Toolbar toolbar;
    int instance; //1 foods, 2 ingr., 3 cats, 4 users


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(getToolbarTitle());
        setSupportActionBar(toolbar);

        this.listasobject = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.crud_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        onCreateChild();

        table = createTable();
        retrieveData();
    }

    protected abstract void onCreateChild();

    abstract void displayData();


    void retrieveData() {
        table.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                collectDataCrud(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    abstract void collectDataCrud(DataSnapshot dataSnapshot);

    abstract String getToolbarTitle();

    abstract Object getDataFromSnapshot(DataSnapshot dataSnapshot);

    abstract DatabaseReference createTable();



    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_top_menu, menu);
        return true;
    }

    public boolean onNavSuperior(MenuItem menuitem) {
        View view = menuitem.getActionView();
        int id = menuitem.getItemId();
        Intent intent;
        if (id == R.id.admin_top_menu_item_client_view) intent = new Intent(this, Home.class);
        else if (id == R.id.admin_top_menu_item_pending_requests)
            intent = new Intent(this, AdminHome.class);
        else intent = new Intent(this, AdminPanel.class);
        startActivity(intent);
        return true;
    }


}
