package com.visual.conserapp.Views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.visual.conserapp.Model.Order;
import com.visual.conserapp.Model.Request;
import com.visual.conserapp.R;
import com.visual.conserapp.ViewHolders.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class RequestDetails extends AppCompatActivity {

    private ViewPagerAdapter viewPagerAdapter;
    private Request request;
    private ViewPager viewPager;
    private FirebaseDatabase database;
    private DatabaseReference requests_table, foods_table;
    private TextView tv_id, tv_callid, tv_total, tv_name, tv_pickup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //todo: update requests_table en request.id para cambiar done y payed a true

            }
        });

        viewPager = (ViewPager) findViewById(R.id.request_slider);
        tv_id = (TextView) findViewById(R.id.request_details_tv_id);
        tv_callid = (TextView) findViewById(R.id.request_details_tv_callid);
        tv_total = (TextView) findViewById(R.id.request_details_tv_total);
        tv_name = (TextView) findViewById(R.id.request_details_tv_name);
        tv_pickup = (TextView) findViewById(R.id.request_details_tv_pickup);

        database = FirebaseDatabase.getInstance();
        requests_table = database.getReference("Requests");
        foods_table = database.getReference("Food");


        String gson = getIntent().getStringExtra("request_id");
        request = (new Gson()).fromJson(gson,Request.class);

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        displayData();
        displayFoods();


    }

    private void displayData() {
        tv_id.setText(request.getId());
        tv_callid.setText(request.getId().substring(request.getId().length()-3,request.getId().length()));
        tv_name.setText(request.getClientname());
        tv_pickup.setText(request.getPickupHour());
        tv_total.setText(request.getTotal());

    }

    private void displayFoods() {
        List<Order> foods = request.getFoods();
        final List<String> foodimages = new ArrayList<>();
        final List<ImageView> images = new ArrayList<>();
        for(Order o : foods){
           DatabaseReference imageurl  = foods_table.child(o.getProductID()).child("Image");
           imageurl.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   String url = dataSnapshot.getValue(String.class);
                   viewPagerAdapter.addImage(url);
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {
               }
           });
        }
    }
}
