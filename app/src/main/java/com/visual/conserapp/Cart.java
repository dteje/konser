package com.visual.conserapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    ArrayList<String> listSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setTitle("Cart");

        Bundle extras = getIntent().getExtras();
         String s = extras.getString("list");

        Log.d("test", s);

        TextView tv_test = (TextView) (TextView) findViewById(R.id.tv_test);
        tv_test.setText(s);
    }
}
