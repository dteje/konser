package com.visual.conserapp.Views;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.visual.conserapp.Adapter.DrinkAdapter;
import com.visual.conserapp.Adapter.FavsAdapter;
import com.visual.conserapp.Adapter.FoodAdapter;
import com.visual.conserapp.Adapter.HomeRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;


import com.google.firebase.database.FirebaseDatabase;
import com.visual.conserapp.Adapter.IngredientAdapter;
import com.visual.conserapp.Common.Common;
import com.visual.conserapp.Model.Drink;
import com.visual.conserapp.Model.Favs;
import com.visual.conserapp.Model.Food;
import com.visual.conserapp.Model.Ingredient;
import com.visual.conserapp.IngredientesFerran.AdminIngredientsMenu;
import com.visual.conserapp.Model.UserFavs;
import com.visual.conserapp.Model.MenuDia;
import com.visual.conserapp.R;

import org.w3c.dom.Text;

import in.goodiebag.carouselpicker.CarouselPicker;
import io.paperdb.Paper;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    CarouselPicker.CarouselViewAdapter imageAdapter;
    CarouselPicker carouselPicker;
    TextView textoCentro;
    RecyclerView homeRecycler;
    RecyclerView.LayoutManager layoutManager;

    HomeRecyclerAdapter adapter;
    ArrayList<MenuDia> listMenu;
    private List<Button> listData = new ArrayList<>();
    ArrayList<Favs> listFavs;

    FirebaseDatabase database;
    DatabaseReference requests_table;

    ArrayList<Food> listaFoodFirebase;
    ArrayList<Drink> listaDrinkFirebase;
    DatabaseReference userfavs_table;
    String userFavId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (Common.currentUser.getAdmin()) {
            setContentView(R.layout.activity_home_admin);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Inicio");
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        requests_table = database.getReference("Menu");

        Paper.init(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        listMenu = new ArrayList<>();
        obtainDataFirebase();

        userFavId = Common.currentUser.getEmailAsId();
        listFavs = new ArrayList<Favs>();
        obtainDataFirebaseUserFavs();

        View headerView = navigationView.getHeaderView(0);
        tv_usermail = (TextView) headerView.findViewById(R.id.nav_header_email);
        tv_username = (TextView) headerView.findViewById(R.id.nav_header_name);
        tv_usermail.setText(Common.currentUser.getEmail());
        tv_username.setText(Common.currentUser.getName());

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


        homeRecycler = (RecyclerView) findViewById(R.id.rec1);
        homeRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        homeRecycler.setLayoutManager(layoutManager);
        //adapter = new HomeRecyclerAdapter(listMenu, database, requests_table, this);
        //homeRecycler.setAdapter(adapter);

        loadUserData();


    }


    //MÉTODOS NUEVOS

    private void obtainDataFirebase() {
        declareDatabase();

        requests_table.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                obtainMenus(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Fail"+databaseError.getCode());
            }
        });

    }

    private void obtainMenus(DataSnapshot dataSnapshot) {
        //listMenu.clear();
        System.out.println("12123");

        for(DataSnapshot ds : dataSnapshot.getChildren()){

            MenuDia menu = ds.getValue(MenuDia.class);
            listMenu.add(menu);
        }


        loadMenus();
    }

    private void loadMenus() {
        adapter = new HomeRecyclerAdapter(listMenu, database, requests_table, getApplicationContext(), this);
        homeRecycler.setAdapter(adapter);
    }

    private void declareDatabase() {
        database = FirebaseDatabase.getInstance();
        requests_table = database.getReference("Menu");
        userfavs_table = database.getReference("UserFavs");
    }

    TextView tv_username;
    TextView tv_usermail;

    private void loadUserData() {

    }


    public void modifyAdapter() {
        adapter.notifyDataSetChanged();
    }

    private void carruselListener(int position) {
        switch (position) {
            case 0:
                textoCentro.setText("Platos");
                getFoodsFromFirebaseAndSetAdapter();
                break;

            case 1:
                textoCentro.setText("Menú");
                modifyAdapter();
                homeRecycler.setAdapter(adapter);
                break;

            case 2:
                textoCentro.setText("Hoy");
                break;

            case 3:
                textoCentro.setText("Bebidas");
                getDrinksFromFirebaseAndSetAdapter();
                break;
            case 4:
                textoCentro.setText("Favoritos");
                initializeFavs();
                break;
        }
    }


    private void getDrinksFromFirebaseAndSetAdapter(){
        listaDrinkFirebase = new ArrayList<>();
        final DatabaseReference table_drinks = database.getReference("Drink");
        table_drinks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    listaDrinkFirebase.add(d.getValue(Drink.class));
                }
                setDrinkAdapter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setDrinkAdapter() {
        Collections.sort(listaDrinkFirebase);
        DrinkAdapter drinkAdapter = new DrinkAdapter(listaDrinkFirebase,this);
        homeRecycler.setAdapter(drinkAdapter);
    }

    private void getFoodsFromFirebaseAndSetAdapter(){
        listaFoodFirebase = new ArrayList<>();
        final DatabaseReference table_foods = database.getReference("Food");
        table_foods.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    listaFoodFirebase.add(d.getValue(Food.class));
                }
                setFoodAdapter();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    void setFoodAdapter(){
        Collections.sort(listaFoodFirebase);
        FoodAdapter foodAdapter = new FoodAdapter(listaFoodFirebase,this);
        homeRecycler.setAdapter(foodAdapter);
    }



    public void initializeFavs() {
        UserFavs userFavs = new UserFavs(Common.currentUser.getName(), listFavs, userFavId);
        FavsAdapter favsAdapter = new FavsAdapter(userFavs, getApplicationContext(), listFavs, database, this);
        homeRecycler.setAdapter(favsAdapter);
    }


    public void obtainFavs(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            if (ds.getKey().equals(userFavId)) {
                listFavs = ds.getValue(UserFavs.class).getListFavs();
            }

        }
    }

    public void obtainDataFirebaseUserFavs() {
        declareDatabase();
        listFavs.clear();

        userfavs_table.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                obtainFavs(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }



    private void setupList() {
        for (int i = 0; i < 15; i++) {
            Button btn = new Button(this);
            btn.setText(textoCentro.getText());
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
            intent.putExtra("id","100");
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(Home.this, AdminIngredientsMenu.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_adminPanel) {
            Intent intent = new Intent(Home.this, AdminPanel.class);
            startActivity(intent);

        } else if (id == R.id.nav_adminPending) {
            Intent intent = new Intent(Home.this, AdminHome.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout || id == R.id.nav_logout2) {
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
        if (id == R.id.cart_id) intent = new Intent(this, Cart.class);
        else if (id == R.id.sandwitchCreator_id) intent = new Intent(this, SandwitchCreator.class);
        else intent = new Intent(this, Home.class);
        startActivity(intent);
        return true;
    }

}