package com.visual.conserapp.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.visual.conserapp.R;

public class Offers extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    public boolean onNavSuperior(MenuItem menuitem){
        View view = menuitem.getActionView();
        int id = menuitem.getItemId();
        Intent intent;
        if(id == R.id.cart_id)  intent = new Intent(this,Cart.class);
        else intent = new Intent(this,Offers.class);
        startActivity(intent);
        return true;
    }
}
