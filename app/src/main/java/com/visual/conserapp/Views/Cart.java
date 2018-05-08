package com.visual.conserapp.Views;

import android.content.Intent;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.visual.conserapp.Database.Database;
import com.visual.conserapp.Model.Order;
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
    DatabaseReference orders_table;

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
        setSupportActionBar(toolbar);

        txt_totalprice = (TextView) findViewById(R.id.cart_tv_total);
        btn_placeorder = (FButton) findViewById(R.id.cart_btn_placeorder);

        database = FirebaseDatabase.getInstance();
        orders_table = database.getReference("Orders");

        recyclerView = (RecyclerView) findViewById(R.id.cart_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadCart();
    }

    private void loadCart() {

        cart = new Database(this).getCarts();
        cartAdapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(cartAdapter);
        int total = 0;

        for(Order o : cart){
            total += (Double.parseDouble(o.getPrice()))*(Integer.parseInt(o.getQuantity()));
        }
        Locale locale = new Locale("es","es");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txt_totalprice.setText(fmt.format(total));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
}
