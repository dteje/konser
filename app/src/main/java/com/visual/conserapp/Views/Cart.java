package com.visual.conserapp.Views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.visual.conserapp.Common.Common;
import com.visual.conserapp.Database.Database;
import com.visual.conserapp.Model.Order;
import com.visual.conserapp.Model.Request;
import com.visual.conserapp.R;
import com.visual.conserapp.Adapter.CartAdapter;

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

        txt_totalprice = (TextView) findViewById(R.id.cart_tv_total);
        btn_placeorder = (FButton) findViewById(R.id.cart_btn_placeorder);

        database = FirebaseDatabase.getInstance();
        requests_table = database.getReference("Requests");

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
                    showHourDialog();

                }
            }
        });


    }

    private void showHourDialog() {
        final AlertDialog.Builder hourdialog = new AlertDialog.Builder(Cart.this);
        hourdialog.setTitle("Solo falta un detalle");
        hourdialog.setMessage("Indica la hora a la que recogeras tu pedido");

        final TimePicker timePicker = new TimePicker(Cart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        timePicker.setLayoutParams(lp);

        final EditText edt_address = new EditText(Cart.this);
        edt_address.setLayoutParams(lp);
        hourdialog.setView(timePicker);

        hourdialog.setPositiveButton("Hecho", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String key = String.valueOf(System.currentTimeMillis());
                Request request = new Request(key, Common.currentUser.getName(), txt_totalprice.getText().toString(), cart, timePicker.getHour() + ":" + timePicker.getMinute());
                requests_table.child(key).setValue(request);
                Toast.makeText(Cart.this, "Gracias! Pedido realizado", Toast.LENGTH_SHORT).show();
                new Database(getBaseContext()).cleanCart();
                finish();
            }
        });

        hourdialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        hourdialog.show();
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
        else if (id == R.id.home_id) intent = new Intent(this, Home.class);
        else intent = new Intent(this, SandwitchCreator.class);
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    public void updateCart() {
        double total = 0;
        for (Order o : cart) {
            total += (Double.parseDouble(o.getPrice())) * (Integer.parseInt(o.getQuantity()));
        }
        Locale locale = new Locale("es", "es");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txt_totalprice.setText(fmt.format(total));
    }
}