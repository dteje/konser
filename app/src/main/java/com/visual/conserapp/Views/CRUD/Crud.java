package com.visual.conserapp.Views.CRUD;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.visual.conserapp.R;
import com.visual.conserapp.Views.AdminHome;
import com.visual.conserapp.Views.AdminPanel;
import com.visual.conserapp.Views.Home;

/**
 * Created by daniel on 29/05/2018.
 */

public abstract class Crud extends AppCompatActivity{

    protected String name;
    protected String id;

    private Toolbar toolbar;

    public void retrieveDataFromFB(){
        System.out.println("AAA");
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


}
