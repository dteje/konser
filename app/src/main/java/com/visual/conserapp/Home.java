package com.visual.conserapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.visual.conserapp.Adapter.WheelImageAdapter;
import com.visual.conserapp.Data.ImageData;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import github.hellocsl.cursorwheel.CursorWheelLayout;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CursorWheelLayout.OnMenuSelectedListener{

    CursorWheelLayout wheel_text,wheel_image;
    List<ImageData> lstImage;
    WheelImageAdapter imgAdapter;
    TextView textoCentro;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Inicio");
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();

        Paper.init(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Inicializar carrusel

        initViews();

        loadData();

        wheel_image.setOnMenuSelectedListener(this);

        //Coger la fuente para el texto del carrusel

        AssetManager am = getApplicationContext().getAssets();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Log.d("test_access menu", "hey im here!!");

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Log.d("test_access camera", "hey im camera!!");

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.logout){
            Paper.book().destroy();
            Intent intent = new Intent(Home.this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean onNavSuperior(MenuItem menuitem) {
        View view = menuitem.getActionView();
        int id = menuitem.getItemId();
        Intent intent;
        if(id == R.id.cart_id)  intent = new Intent(this,Cart.class);
        else if (id == R.id.sandwitchCreator_id) intent = new Intent(this, SandwitchCreator.class);
        else intent = new Intent(this,Offers.class);
        startActivity(intent);
        return true;
    }

    private void loadData() {
        lstImage = new ArrayList<>();
        lstImage.add(new ImageData(R.drawable.patatas1_mini, "Bocadillos"));
        lstImage.add(new ImageData(R.drawable.patatas2_mini, "Bebidas"));
        lstImage.add(new ImageData(R.drawable.patatas3_mini, "Aperitivos"));
        lstImage.add(new ImageData(R.drawable.patatas4_mini, "Ofertas"));
        lstImage.add(new ImageData(R.drawable.patatas4_mini, "Menu"));
        imgAdapter = new WheelImageAdapter(getBaseContext(),lstImage);
        wheel_image.setAdapter(imgAdapter);

    }

    private void initViews() {
        wheel_image = (CursorWheelLayout) findViewById(R.id.wheel_image);

    }

    @Override
    public void onItemSelected(CursorWheelLayout parent, View view, int pos) {
            textoCentro = (TextView) findViewById(R.id.id_wheel_menu_center_item);
            Typeface lato_font = Typeface.createFromAsset(getAssets(), "fonts/Lobster-Regular.ttf");
            textoCentro.setTypeface(lato_font);
            textoCentro.setText(lstImage.get(pos).imageDescription);

        }




}
