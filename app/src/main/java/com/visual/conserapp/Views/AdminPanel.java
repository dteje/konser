package com.visual.conserapp.Views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.visual.conserapp.R;
import com.visual.conserapp.Views.CRUD.List.FoodList;
import com.visual.conserapp.Views.CRUD.List.IngredientList;
import com.visual.conserapp.Views.CRUD.List.UserList;

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
        else if (id == R.id.admin_top_menu_item_pending_requests)
            intent = new Intent(this, AdminOrders.class);
        else intent = new Intent(this, AdminPanel.class);
        startActivity(intent);
        return true;
    }

    public void onRestaurante(View view) {
        Intent intent = new Intent(AdminPanel.this, IngredientList.class);
        int id = view.getId();
        if (id == R.id.btn_ingredients) {
            intent = new Intent(AdminPanel.this, IngredientList.class);
        } else if (id == R.id.btn_foods) {
            intent = new Intent(AdminPanel.this, FoodList.class);
        } else if (id == R.id.btn_clients || id == R.id.btn_admins) {
            intent = new Intent(AdminPanel.this, UserList.class);
        }
        startActivity(intent);
    }

    public void onOfertas(View view) {
        Intent intent = new Intent(AdminPanel.this, AdminDailyEdit.class);
        startActivity(intent);
    }

    public void onMenus(View view) {
        Intent intent = new Intent(AdminPanel.this, AdminMenuEdit.class);
        startActivity(intent);
    }
}
