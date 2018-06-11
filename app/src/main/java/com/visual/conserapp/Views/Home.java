package com.visual.conserapp.Views;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.visual.conserapp.Adapter.DrinkAdapter;
import com.squareup.picasso.Picasso;
import com.visual.conserapp.Adapter.FavsAdapter;
import com.visual.conserapp.Adapter.FoodAdapter;
import com.visual.conserapp.Adapter.MenuAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import com.google.firebase.database.FirebaseDatabase;
import com.visual.conserapp.Common.Common;
import com.visual.conserapp.Model.Drink;
import com.visual.conserapp.Database.Database;
import com.visual.conserapp.Model.Favs;
import com.visual.conserapp.Model.Food;
import com.visual.conserapp.Model.Order;
import com.visual.conserapp.Model.UserFavs;
import com.visual.conserapp.Model.MenuDia;
import com.visual.conserapp.R;

import in.goodiebag.carouselpicker.CarouselPicker;
import io.paperdb.Paper;

import static java.time.LocalDate.now;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MenuAdapter menuAdapter;
    CarouselPicker.CarouselViewAdapter imageAdapter;
    CarouselPicker carouselPicker;
    RecyclerView homeRecycler;
    RecyclerView.LayoutManager layoutManager;
    ImageView img_dailysandiwch, img1, img2;
    TextView textoCentro, txt_dailysandwich_name, txt_dailysandwich_price, txt_n1, txt_n2, tv_username, tv_usermail;
    ;
    LinearLayout hoy_layout;

    private List<Button> listData = new ArrayList<>();
    ArrayList<MenuDia> listMenu;
    ArrayList<Favs> listFavs;
    ArrayList<Food> listaFoodFirebase;
    ArrayList<Drink> listaDrinkFirebase;

    String userFavId;
    Food f, f1, f2;

    FirebaseDatabase database;
    DatabaseReference requests_table, userfavs_table, menu_table, table_foods, config_table;
    ;


    @RequiresApi(api = Build.VERSION_CODES.O)
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

        textoCentro = (TextView) findViewById(R.id.carruselText);
        textoCentro.setText("Hoy");
        Typeface lobster = Typeface.createFromAsset(getAssets(), "fonts/Lobster-Regular.ttf");
        textoCentro.setTypeface(lobster);

        iniciarCarrusel();

        AssetManager am = getApplicationContext().getAssets();

        homeRecycler = (RecyclerView) findViewById(R.id.rec1);
        homeRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        homeRecycler.setLayoutManager(layoutManager);
        homeRecycler.setVisibility(View.INVISIBLE);

        hoy_layout = (LinearLayout) findViewById(R.id.layout_hoy);
        hoy_layout.setVisibility(View.VISIBLE);

        txt_dailysandwich_name = (TextView) findViewById(R.id.txt_dailysandwich_name);
        txt_dailysandwich_price = (TextView) findViewById(R.id.txt_dailysandwich_price);
        img_dailysandiwch = (ImageView) findViewById(R.id.img_dailysandwich);

        txt_n1 = (TextView) findViewById(R.id.name1);
        txt_n2 = (TextView) findViewById(R.id.name2);
        img1 = (ImageView) findViewById(R.id.img_p1);
        img2 = (ImageView) findViewById(R.id.img_p2);

        getDailySandwich();

    }

    private void iniciarCarrusel() {
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
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getDailySandwich() {
        declareDatabase();
        String day = now().getDayOfWeek().name();
        switch (day) {
            case "TUESDAY":
                config_table = config_table.child("dailysandwich").child("2tuesday");
                menu_table = menu_table.child("dailymenu").child("2tuesday");
                break;
            case "WEDNESDAY":
                config_table = config_table.child("dailysandwich").child("3wednesday");
                menu_table = menu_table.child("dailymenu").child("3wednesday");
                break;
            case "THURSDAY":
                config_table = config_table.child("dailysandwich").child("4thursday");
                menu_table = menu_table.child("dailymenu").child("4thursday");

                break;
            case "FRIDAY":
                config_table = config_table.child("dailysandwich").child("5friday");
                menu_table = menu_table.child("dailymenu").child("5friday");
                break;
            default:
                config_table = config_table.child("dailysandwich").child("1monday");
                menu_table = menu_table.child("dailymenu").child("1monday");
                break;
        }
        config_table.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                displayDailySandwich(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        menu_table.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                displayDailyMenu(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void displayDailySandwich(String id) {
        table_foods.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                f = dataSnapshot.getValue(Food.class);
                txt_dailysandwich_name.setText(f.getName() + " y bebida");
                txt_dailysandwich_price.setText("3.00 €");
                if (!f.getImage().isEmpty())
                    Picasso.with(getBaseContext()).load(f.getImage()).into(img_dailysandiwch);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    private void displayDailyMenu(String id) {

        DatabaseReference p1 = database.getReference("Menu").child(id).child("listaPlatos").child("0");
        DatabaseReference p2 = database.getReference("Menu").child(id).child("listaPlatos").child("1");
        p1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                f1 = dataSnapshot.getValue(Food.class);
                txt_n1.setText(f1.getName());
                if (!f1.getImage().isEmpty())
                    Picasso.with(getBaseContext()).load(f1.getImage()).into(img1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        p2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                f2 = dataSnapshot.getValue(Food.class);
                txt_n2.setText(f2.getName());
                if (!f2.getImage().isEmpty())
                    Picasso.with(getBaseContext()).load(f2.getImage()).into(img2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void obtainDataFirebase() {
        declareDatabase();
        listMenu = new ArrayList<>();

        requests_table.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                obtainMenus(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Fail" + databaseError.getCode());
            }
        });
    }

    private void obtainMenus(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            MenuDia menu = ds.getValue(MenuDia.class);
            listMenu.add(menu);
        }
             generateAdapter("Menu");
    }

    private void declareDatabase() {
        database = FirebaseDatabase.getInstance();
        requests_table = database.getReference("Menu");
        userfavs_table = database.getReference("UserFavs");
        table_foods = database.getReference("Food");
        config_table = database.getReference("Config");
        menu_table = database.getReference("Config");
    }

    private void carruselListener(int position) {
        switch (position) {
            case 0:
                textoCentro.setText("Platos");
                hoy_layout.setVisibility(View.INVISIBLE);
                homeRecycler.setVisibility(View.VISIBLE);
                getFoodsFromFirebaseAndSetAdapter();
                break;

            case 1:
                textoCentro.setText("Menú");
                hoy_layout.setVisibility(View.INVISIBLE);
                homeRecycler.setVisibility(View.VISIBLE);
                obtainDataFirebase();
                break;

            case 2:
                textoCentro.setText("Hoy");
                hoy_layout.setVisibility(View.VISIBLE);
                homeRecycler.setVisibility(View.INVISIBLE);
                break;

            case 3:
                textoCentro.setText("Bebidas");
                getDrinksFromFirebaseAndSetAdapter();
                hoy_layout.setVisibility(View.INVISIBLE);
                homeRecycler.setVisibility(View.VISIBLE);
                break;
            case 4:
                textoCentro.setText("Favoritos");
                hoy_layout.setVisibility(View.INVISIBLE);
                generateAdapter("Favs");
                break;
        }
    }

    private void generateAdapter(String adapterType){
        switch (adapterType){
            case "Menu":
                Collections.sort(listMenu);
                menuAdapter = new MenuAdapter(listMenu, getApplicationContext(), this);
                homeRecycler.setAdapter(menuAdapter);
                break;
            case "Drink":
                Collections.sort(listaDrinkFirebase);
                DrinkAdapter drinkAdapter = new DrinkAdapter(listaDrinkFirebase, this);
                homeRecycler.setAdapter(drinkAdapter);
                break;
            case "Food":
                Collections.sort(listaFoodFirebase);
                FoodAdapter foodAdapter = new FoodAdapter(listaFoodFirebase, this);
                homeRecycler.setAdapter(foodAdapter);
                break;
            case "Favs":
                UserFavs userFavs = new UserFavs(Common.currentUser.getName(), listFavs, userFavId);
                FavsAdapter favsAdapter = new FavsAdapter(userFavs, getApplicationContext(), listFavs, database, this);
                homeRecycler.setAdapter(favsAdapter);
                break;
        }

    }


    private void getDrinksFromFirebaseAndSetAdapter() {
        listaDrinkFirebase = new ArrayList<>();
        final DatabaseReference table_drinks = database.getReference("Drink");
        table_drinks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    listaDrinkFirebase.add(d.getValue(Drink.class));
                }
                generateAdapter("Drink");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getFoodsFromFirebaseAndSetAdapter() {
        declareDatabase();
        listaFoodFirebase = new ArrayList<>();
        table_foods.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    listaFoodFirebase.add(d.getValue(Food.class));
                }
                generateAdapter("Food");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void obtainDataFirebaseUserFavs() {
        declareDatabase();
        listFavs.clear();

        userfavs_table.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                obtainFavsFromDatabase(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void obtainFavsFromDatabase(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            if (ds.getKey().equals(userFavId)) {
                listFavs = ds.getValue(UserFavs.class).getListFavs();
            }

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
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_sandwich) {
            Intent intent = new Intent(Home.this, SandwichCreator.class);
            startActivity(intent);
        } else if (id == R.id.nav_cart) {
            Intent intent = new Intent(Home.this, Cart.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(Home.this, UserConfig.class);
            startActivity(intent);
        } else if (id == R.id.nav_adminPanel) {
            Intent intent = new Intent(Home.this, AdminPanel.class);
            startActivity(intent);

        } else if (id == R.id.nav_adminPending) {
            Intent intent = new Intent(Home.this, AdminOrders.class);
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
        else if (id == R.id.sandwitchCreator_id) intent = new Intent(this, SandwichCreator.class);
        else intent = new Intent(this, Home.class);
        startActivity(intent);
        return true;
    }

    public void btn_add_swch(View view) {
        Order pedido = new Order(f.getID(), "Bocadillo del día", "1",
                "3.00", f.getDiscount());
        new Database(getBaseContext()).addToCart(pedido);
        Toast.makeText(Home.this, "Añadido al carrito!", Toast.LENGTH_SHORT).show();
    }

    public void btn_add_menu(View view) {
        Order pedido = new Order(f1.getID(), f1.getName(), "1",
                "2.50", f.getDiscount());
        Order pedido2 = new Order(f2.getID(), f2.getName(), "1",
                "2.50", f.getDiscount());
        new Database(getBaseContext()).addToCart(pedido);
        new Database(getBaseContext()).addToCart(pedido2);
        Toast.makeText(Home.this, "Añadido al carrito!", Toast.LENGTH_SHORT).show();
    }


}