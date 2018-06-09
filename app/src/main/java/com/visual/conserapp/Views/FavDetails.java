package com.visual.conserapp.Views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.visual.conserapp.R;

import java.util.ArrayList;

public class FavDetails extends AppCompatActivity {

    TextView tv_nameSandwichOfficial;
    TextView tv_nameSandwichUser;
    TextView tv_ingredients;
    TextView tv_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_favs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detalles");
        toolbar.setTitleTextColor(Color.rgb(255, 255, 255));
        setSupportActionBar(toolbar);

        tv_nameSandwichOfficial = (TextView) findViewById(R.id.tv_nameSandwichOfficial);
        tv_nameSandwichUser = (TextView) findViewById(R.id.tv_nameSandwichUser);
        tv_ingredients = (TextView) findViewById(R.id.tv_ingredients);
        tv_price = (TextView) findViewById(R.id.tv_price);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String nameSandwichOfficial = extras.getString("nameSandwichOfficial");
        String nameSandwichUser = extras.getString("nameSandwichUser");
        String ingredients = extras.getString("Ingredients");
        Double price = extras.getDouble("price");

        tv_nameSandwichOfficial.setText(nameSandwichOfficial);
        tv_nameSandwichUser.setText(nameSandwichUser);
        tv_ingredients.setText(ingredients);
        tv_price.setText(String.valueOf(price));
    }

}
