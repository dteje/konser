package com.visual.conserapp.Views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.visual.conserapp.Model.Food;
import com.visual.conserapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by daniel on 08/06/2018.
 */

public class AdminDailyEdit extends AppCompatActivity{

    Map<String, Object> map = new HashMap<>();
    List<String> previousdays, foodnames;
    List<Food> foods;
    List<Spinner> spinners = new ArrayList<>();
    Spinner monday, tuesday, wednesday, thursday, friday;
    Toolbar toolbar;
    Button btn_update;

    FirebaseDatabase database;
    DatabaseReference table_dailysandwich, table_foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_ofertas_semana);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Ofertas por día");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        table_dailysandwich = database.getReference("Config/dailysandwich");
        table_foods = database.getReference("Food");

        monday = (Spinner) findViewById(R.id.smon);
        spinners.add(monday);
        tuesday = (Spinner) findViewById(R.id.stue);
        spinners.add(tuesday);
        wednesday = (Spinner) findViewById(R.id.swed);
        spinners.add(wednesday);
        thursday = (Spinner) findViewById(R.id.sthu);
        spinners.add(thursday);
        friday = (Spinner) findViewById(R.id.sfri);
        spinners.add(friday);


        btn_update = (Button) findViewById(R.id.btn_update);

        getFoodsFromFirebaseAndFillSpinners();
        getConfigFromDatabaseAndSetDefault();
    }

    private void getFoodsFromFirebaseAndFillSpinners() {
        foodnames = new ArrayList<>();
        foods = new ArrayList<>();
        table_foods.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Food food = d.getValue(Food.class);
                    foods.add(food);
                    foodnames.add(food.getName());
                }
                fillSpinners();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    private void fillSpinners() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, foodnames.toArray(new String[0]));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (Spinner s : spinners) {
            s.setAdapter(adapter);
        }
    }

    private void getConfigFromDatabaseAndSetDefault() {
        previousdays = new ArrayList<>();
        table_dailysandwich.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               for(DataSnapshot d : dataSnapshot.getChildren()){
                   previousdays.add(d.getValue(String.class));
               }
               setDefaultItems();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setDefaultItems() {
        for(int i=0 ; i<previousdays.size() ; i++){
            String id = previousdays.get(i);
            int pos = searchId(id);
            spinners.get(i).setSelection(pos);
        }
    }

    int searchId(String id){
        int pos = 0;
        for(Food f : foods){
            if(f.getID().equals(id)) return pos;
            else pos++;
        }
        return pos;
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

    public void onUpdate(View view) {
        map.put("1monday",foods.get(monday.getSelectedItemPosition()).getID());
        map.put("2tuesday",foods.get(tuesday.getSelectedItemPosition()).getID());
        map.put("3wednesday",foods.get(wednesday.getSelectedItemPosition()).getID());
        map.put("4thursday",foods.get(thursday.getSelectedItemPosition()).getID());
        map.put("5friday",foods.get(friday.getSelectedItemPosition()).getID());
        table_dailysandwich.updateChildren(map);
        finish();
    }

}
