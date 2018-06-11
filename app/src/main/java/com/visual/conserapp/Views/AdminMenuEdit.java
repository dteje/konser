package com.visual.conserapp.Views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.visual.conserapp.Model.Food;
import com.visual.conserapp.Model.MenuDia;
import com.visual.conserapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by daniel on 11/06/2018.
 */

public class AdminMenuEdit extends AppCompatActivity {

    private Toolbar toolbar;
    FirebaseDatabase database;
    DatabaseReference table_dailymenu, table_foods, table_menus;
    List<String> menusnames, foodnames, menusconfig;
    List<Food> foods;
    List<MenuDia> menus;
    Spinner smenu, sp1, sp2;
    Map<String, Object> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_menu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menú del día");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        table_dailymenu = database.getReference("Config/dailymenu");
        table_foods = database.getReference("Food");
        table_menus = database.getReference("Menu");

        menus = new ArrayList<>();
        menusnames = new ArrayList<>();

        smenu = (Spinner) findViewById(R.id.smenu);
        sp1 = (Spinner) findViewById(R.id.sp1);
        sp2 = (Spinner) findViewById(R.id.sp2);

        getConfigFromDatabase();
        getMenusFromDatabase();
        getFoodsFromFirebaseAndFillSpinners();

        smenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                setDefaultValuesOnSpinners();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
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
        ArrayAdapter<String> adapterFood = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, foodnames.toArray(new String[0]));
        adapterFood.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapterFood);
        sp2.setAdapter(adapterFood);
        ArrayAdapter<String> adapterMenu = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, menusnames.toArray(new String[0]));
        adapterMenu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smenu.setAdapter(adapterMenu);
        setDefaultValuesOnSpinners();

    }

    private void setDefaultValuesOnSpinners() {
        MenuDia menuDia = menus.get(smenu.getSelectedItemPosition());
        int pos1 = searchId(menuDia.getListaPlatos().get(0).getID());
        int pos2 = searchId(menuDia.getListaPlatos().get(1).getID());
        sp1.setSelection(pos1);
        sp2.setSelection(pos2);

    }

    private void getConfigFromDatabase() {
        menusconfig = new ArrayList<>();
        table_dailymenu.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    menusconfig.add(d.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void getMenusFromDatabase() {
        menusnames = new ArrayList<>();
        menus = new ArrayList<>();
        table_menus.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    MenuDia md = d.getValue(MenuDia.class);
                    menus.add(md);
                    menusnames.add(md.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
        map.put("0",foods.get(sp1.getSelectedItemPosition()));
        map.put("1",foods.get(sp2.getSelectedItemPosition()));
        table_menus.child(smenu.getSelectedItem().toString()).child("listaPlatos").updateChildren(map);
        Toast.makeText(this, "Editado con éxito", Toast.LENGTH_SHORT).show();
        finish();
    }
}
