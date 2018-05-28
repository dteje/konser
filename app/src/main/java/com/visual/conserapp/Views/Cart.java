package com.visual.conserapp.Views;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.visual.conserapp.Common.Common;
import com.visual.conserapp.Database.Database;
import com.visual.conserapp.Model.Order;
import com.visual.conserapp.Model.Request;
import com.visual.conserapp.R;
import com.visual.conserapp.ViewHolders.CartAdapter;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests_table;

    TextView txt_totalprice;
    FButton btn_placeorder;

    List<Order> cart;
    CartAdapter cartAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Carrito");
        toolbar.setTitleTextColor(Color.rgb(255, 255, 255));
        setSupportActionBar(toolbar);

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        //        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.addDrawerListener(toggle);
        //toggle.syncState();


        txt_totalprice = (TextView) findViewById(R.id.cart_tv_total);
        btn_placeorder = (FButton) findViewById(R.id.cart_btn_placeorder);

        database = FirebaseDatabase.getInstance();
        requests_table = database.getReference("Orders");

        recyclerView = (RecyclerView) findViewById(R.id.cart_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadCart();

        btn_placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txt_totalprice.getText().toString().equals("0,00 €"))
                    Toast.makeText(Cart.this, "Carrito vacio", Toast.LENGTH_SHORT).show();
                else {
                    Request request = new Request(Common.currentUser.getName(), txt_totalprice.getText().toString(), cart);
                    requests_table.child(String.valueOf(System.currentTimeMillis())).setValue(request);
                    Toast.makeText(Cart.this, "Gracias! Pedido realizado", Toast.LENGTH_SHORT).show();
                    new Database(getBaseContext()).cleanCart();
                    finish();
                }
            }
        });


    }

    public void loadCart() {

        cart = new Database(this).getCarts();
        cartAdapter = new CartAdapter(cart, this);
        recyclerView.setAdapter(cartAdapter);
        updateCart();
    }


    public boolean onNavSuperior(MenuItem menuitem) {
        View view = menuitem.getActionView();
        int id = menuitem.getItemId();
        Intent intent;
        if (id == R.id.cart_id) intent = new Intent(this, Cart.class);
        else intent = new Intent(this, Offers.class);
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    public void updateCart(){
        double total = 0;
        for (Order o : cart) {
            total += (Double.parseDouble(o.getPrice())) * (Integer.parseInt(o.getQuantity()));
        }
        Locale locale = new Locale("es", "es");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txt_totalprice.setText(fmt.format(total));
    }
}