package com.visual.conserapp.Views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.visual.conserapp.Model.Favs;
import com.visual.conserapp.Model.Ingredient;
import com.visual.conserapp.R;

import java.util.ArrayList;

public class AdminIngredientsMenu extends AppCompatActivity {

    Favs favs;
    DatabaseReference ingredient_table;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ingredients);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Admin Ingredients");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

    public void goToManage(View view){
        Intent intent = new Intent(this, ManageIngredients.class);
        startActivity(intent);
    }

    public void goToUpload(View view){
        Intent intent = new Intent(this, uploadIngredients.class);
        startActivity(intent);
    }

}
