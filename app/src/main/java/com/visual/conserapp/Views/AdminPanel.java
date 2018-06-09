package com.visual.conserapp.Views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.visual.conserapp.R;
import com.visual.conserapp.Views.CRUD.List.Categories;
import com.visual.conserapp.Views.CRUD.List.Foods;
import com.visual.conserapp.Views.CRUD.List.Ingredients;
import com.visual.conserapp.Views.CRUD.List.Users;

/**
 * Created by daniel on 29/05/2018.
 */

public class AdminPanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Panel del administrador");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
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

    public void onRestaurante(View view){
        Intent intent = new Intent(AdminPanel.this,Ingredients.class);
        int id = view.getId();
        if (id == R.id.btn_ingredients) {
            intent = new Intent(AdminPanel.this, Ingredients.class);
        } else if (id == R.id.btn_categories) {
            intent = new Intent(AdminPanel.this, Categories.class);
        } else if (id == R.id.btn_foods) {
            intent = new Intent(AdminPanel.this, Foods.class);
        } else if (id == R.id.btn_clients || id == R.id.btn_admins){
            intent = new Intent(AdminPanel.this, Users.class);
        }
        startActivity(intent);
    }

    public void onOfertas(View view){
        Intent intent = new Intent(AdminPanel.this, AdminOfertasSemana.class);
        startActivity(intent);
    }
    }
