package com.visual.conserapp.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.visual.conserapp.Database.Database;
import com.visual.conserapp.Model.Food;
import com.visual.conserapp.Model.Order;
import com.visual.conserapp.Model.Request;
import com.visual.conserapp.R;
import com.visual.conserapp.ViewHolders.CartAdapter;
import com.visual.conserapp.ViewHolders.RequestAdapter;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdminHome extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests_table;

    List<Request> requests;
    RequestAdapter reqAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Administrador");
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        requests_table = database.getReference("Requests");


        recyclerView = (RecyclerView) findViewById(R.id.admin_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadRequests();


    }

    private void loadRequests() {

        requests_table.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Request req = dataSnapshot.getValue(Request.class);
                requests.add(req);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        reqAdapter = new RequestAdapter(requests, this);
        recyclerView.setAdapter(reqAdapter);
    }


}
