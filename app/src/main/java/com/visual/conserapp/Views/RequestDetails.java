package com.visual.conserapp.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.visual.conserapp.Model.Food;
import com.visual.conserapp.Model.Order;
import com.visual.conserapp.Model.Request;
import com.visual.conserapp.R;
import com.visual.conserapp.ViewHolders.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class RequestDetails extends AppCompatActivity {

    private ViewPager viewPager;
    private Request request;
    private FirebaseDatabase database;
    private DatabaseReference requests_table, foods_table;
    private TextView tv_id, tv_callid, tv_total, tv_name, tv_pickup;
   // private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String gson = getIntent().getStringExtra("request_id");
        request = (new Gson()).fromJson(gson, Request.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request.setPayed(true);
                request.setDone(true);
                //DatabaseReference table_requests = database.getReference("Requests").child(request.getId());
                //table_requests.addValueEventListener()
                database.getReference("Requests").child(request.getId()).setValue(request);
                Toast.makeText(getBaseContext(),"Pedido completado",Toast.LENGTH_SHORT);
                finish();





            }
        });

        //listView = (ListView) findViewById(R.id.request_details_lv_orders);
        tv_id = (TextView) findViewById(R.id.request_details_tv_id);
        tv_callid = (TextView) findViewById(R.id.request_details_tv_callid);
        tv_total = (TextView) findViewById(R.id.request_details_tv_total);
        tv_name = (TextView) findViewById(R.id.request_details_tv_name);
        tv_pickup = (TextView) findViewById(R.id.request_details_tv_pickup);

        database = FirebaseDatabase.getInstance();
        requests_table = database.getReference("Requests");
        foods_table = database.getReference("Food");




        viewPager = (ViewPager) findViewById(R.id.request_slider);

        displayData();
        displayFoods();
        //displayCBs();


    }

    private void displayData() {
        tv_id.setText(request.getId());
        tv_callid.setText(request.getId().substring(request.getId().length() - 3, request.getId().length()));
        tv_name.setText(request.getClientname());
        tv_pickup.setText(request.getPickupHour());
        tv_total.setText(request.getTotal());

    }

    private void displayFoods() {
        final Context context = this;
        final List<Order> orders = request.getOrders();
        final List<Food> foods = new ArrayList<>();
        for (final Order o : orders) {
            DatabaseReference food_reference = foods_table.child(o.getProductID());
            food_reference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Food f = dataSnapshot.getValue(Food.class);
                    foods.add(f);
                    if (foods.size() == orders.size()) {
                        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(context, foods);
                        viewPager.setAdapter(viewPagerAdapter);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });


        }
    }
}
