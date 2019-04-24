package com.visual.conserapp.Views.CRUD.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.visual.conserapp.R;
import com.visual.conserapp.Adapter.CRUDList.CrudAdapter;
import com.visual.conserapp.Views.AdminOrders;
import com.visual.conserapp.Views.AdminPanel;
import com.visual.conserapp.Views.Home;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 29/05/2018.
 */

public abstract class CrudList extends AppCompatActivity {

    protected CrudAdapter crudAdapter;
    protected List<Object> objects, objectsfiltered;
    protected String name, id;
    protected FirebaseDatabase database;
    protected DatabaseReference table;
    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    protected SearchView searchView;
    protected Toolbar toolbar;
    protected FloatingActionButton fab;
    protected Context context;
    protected int newId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);
        context = this;

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(getToolbarTitleFromSubclass());
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        table = getTableFromSubclass();

        fab = findViewById(R.id.crud_fab);
        searchView = (SearchView) findViewById(R.id.searchview);
        recyclerView = (RecyclerView) findViewById(R.id.crud_recyclerview);

        this.objects = new ArrayList<>();
        this.objectsfiltered = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                search(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return search(s);

            }
        });

        onCreateChild();
        retrieveDataFromFirebase();
        setFABOnClick();
    }

    protected abstract void setFABOnClick();


    void retrieveDataFromFirebase() {
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
            intent = new Intent(this, AdminOrders.class);
        else intent = new Intent(this, AdminPanel.class);
        startActivity(intent);
        return true;
    }

    protected abstract void displayData();

    protected abstract void collectDataCrud(DataSnapshot dataSnapshot);

    protected abstract String getToolbarTitleFromSubclass();

    protected abstract DatabaseReference getTableFromSubclass();

    protected abstract boolean search(String query);

    protected abstract void onCreateChild();


}
