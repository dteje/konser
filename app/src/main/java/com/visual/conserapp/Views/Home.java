package com.visual.conserapp.Views;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.visual.conserapp.Adapter.WheelImageAdapter;
import com.visual.conserapp.Common.Common;
import com.visual.conserapp.Data.ImageData;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import github.hellocsl.cursorwheel.CursorWheelLayout;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.visual.conserapp.R;

import in.goodiebag.carouselpicker.CarouselPicker;
import io.paperdb.Paper;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CursorWheelLayout.OnMenuSelectedListener{


    CarouselPicker.CarouselViewAdapter imageAdapter;
    CarouselPicker carouselPicker;
    TextView textoCentro;
    RecyclerView homeRecycler;
    RecyclerView.LayoutManager layoutManager;
    HomeRecyclerAdapter adapter;
    private List<Button> listData = new ArrayList<>();

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

        //Inicializar texto carrusel

        textoCentro = (TextView) findViewById(R.id.carruselText);
        textoCentro.setText("Hoy");
        Typeface lobster = Typeface.createFromAsset(getAssets(), "fonts/Lobster-Regular.ttf");
        textoCentro.setTypeface(lobster);

        //Inicializar carrusel

        carouselPicker = (CarouselPicker) findViewById(R.id.carouselPicker);
        List<CarouselPicker.PickerItem> itemsImages = new ArrayList<>();
        itemsImages.add(new CarouselPicker.DrawableItem(R.mipmap.bocata_icon));
        itemsImages.add(new CarouselPicker.DrawableItem(R.mipmap.menu_icon));
        itemsImages.add(new CarouselPicker.DrawableItem(R.mipmap.hoy_icon));
        itemsImages.add(new CarouselPicker.DrawableItem(R.mipmap.complementos_icon));
        itemsImages.add(new CarouselPicker.DrawableItem(R.mipmap.favoritos_icon));
        CarouselPicker.CarouselViewAdapter imageAdapter = new CarouselPicker.CarouselViewAdapter(this, itemsImages, 0);
        carouselPicker.setAdapter(imageAdapter);
        carouselPicker.setCurrentItem(2);


        //Iniciar el listener del carrusel

        carouselPicker.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                carruselListener(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //Coger la fuente para el texto del carrusel

        AssetManager am = getApplicationContext().getAssets();

        //Organizar el recycler

        setupList();

        homeRecycler = (RecyclerView) findViewById(R.id.rec1);
        homeRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        homeRecycler.setLayoutManager(layoutManager);
        adapter = new HomeRecyclerAdapter(listData, this);
        homeRecycler.setAdapter(adapter);

        loadUserData();


    }

    TextView tv_username;
    TextView tv_usermail;
    private void loadUserData() {
        /*
        tv_username = (TextView) findViewById(R.id.nav_header_name);
        tv_usermail = (TextView) findViewById(R.id.nav_header_email);
        tv_username.setText(Common.currentUser.getName());
        tv_usermail.setText(Common.currentUser.getEmail());
        */
    }


    public void modifyAdapter() {
        adapter.notifyDataSetChanged();
    }

    private void carruselListener(int position) {
        switch(position){
            case 0:
                textoCentro.setText("Bocadillos");
                listData.clear();
                setupList();
                modifyAdapter();
                break;

            case 1:
                textoCentro.setText("Menú");
                listData.clear();
                setupList();
                modifyAdapter();
                break;

            case 2:
                textoCentro.setText("Hoy");
                listData.clear();
                setupList();
                modifyAdapter();
                break;

            case 3:
                textoCentro.setText("Complementos");
                listData.clear();
                setupList();
                modifyAdapter();
                break;
            case 4:
                textoCentro.setText("Favoritos");
                listData.clear();
                setupList();
                modifyAdapter();
                break;


        }
    }

    private void setupList() {
        for (int i=0; i<15; i++) {
            Button btn = new Button(this);
            if(textoCentro.getText().equals("Bocadillos")){
                btn.setText("pr");
                btn.setBackgroundColor(getResources().getColor(R.color.bg_wheel));
            }
            else btn.setText(textoCentro.getText());
            listData.add(btn);
        }
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
        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent = new Intent(Home.this, Detail.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_adminPanel) {
            Intent intent = new Intent(Home.this,AdminPanel.class);
            startActivity(intent);

        } else if (id == R.id.nav_adminPending) {
            Intent intent = new Intent(Home.this,AdminHome.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout || id == R.id.nav_logout2){
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


    @Override
    public void onItemSelected(CursorWheelLayout parent, View view, int pos) {

        //Definición de fuentes e inicialización de textos

        textoCentro = (TextView) findViewById(R.id.id_wheel_menu_center_item);
        Typeface lobster = Typeface.createFromAsset(getAssets(), "fonts/Lobster-Regular.ttf");
        textoCentro.setTypeface(lobster);

    }

}