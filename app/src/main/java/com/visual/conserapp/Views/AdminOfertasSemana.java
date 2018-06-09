package com.visual.conserapp.Views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import java.util.List;
import java.util.Set;

/**
 * Created by daniel on 08/06/2018.
 */

public class AdminOfertasSemana extends AppCompatActivity {

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
        table_dailysandwich = database.getReference("Config");
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

        getFoodsFromFirebase();
        getDaysFromFirebase();

        fillSpinners();

    }

    private void getDaysFromFirebase() {
    }

    List<Food> foods;
    List<String> foodnames;

    private void getFoodsFromFirebase(){
        foods = new ArrayList<>();
        foodnames = new ArrayList<>();
        table_foods.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    Food food = d.getValue(Food.class);
                    foods.add(food);
                    foodnames.add(food.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fillSpinners() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, foodnames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for(Spinner s : spinners) s.setAdapter(adapter);

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
        else if (id == R.id.admin_top_menu_item_pending_requests) intent = new Intent(this, AdminHome.class);
        else intent = new Intent(this, AdminPanel.class);
        startActivity(intent);
        return true;
    }

    public void onUpdate(View view){
        System.out.println("ONCLICK");

    }


}
