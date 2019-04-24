package com.visual.conserapp.IngredientesFerran;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.visual.conserapp.Model.Favs;
import com.visual.conserapp.Model.Ingredient;
import com.visual.conserapp.R;
import com.visual.conserapp.Adapter.IngredientAdapter;

import java.util.ArrayList;
import java.util.Hashtable;

public class ManageIngredients extends AppCompatActivity{

    Favs favs;
    DatabaseReference ingredient_table;
    FirebaseDatabase database;

    ArrayList<Ingredient> listIngredientsFireBase;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    Context context;

    Hashtable<Ingredient, String> ingredientKeyHashTable;

    IngredientAdapter ingredientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_ingredients);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Manage Ingredients");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        listIngredientsFireBase = new ArrayList<Ingredient>();
        ingredientKeyHashTable = new Hashtable<Ingredient, String>();

        recyclerView = (RecyclerView) findViewById(R.id.manage_ingredients_recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        obtainDataFirebase();

    }

    public void loadIngredients() {
        ingredientAdapter = new IngredientAdapter(getApplicationContext(), listIngredientsFireBase, ingredientKeyHashTable, database, this);
        recyclerView.setAdapter(ingredientAdapter);
    }

    public void obtainDataFirebase() {
        declareDatabase();

        ingredient_table.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    obtainIngredients(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void obtainIngredients(DataSnapshot dataSnapshot) {
        listIngredientsFireBase.clear();
        ingredientKeyHashTable.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            String name = ds.getValue(Ingredient.class).getName();
            String type = ds.getValue(Ingredient.class).getType();

            Ingredient ing = new Ingredient(name, type);
            String key = ds.getKey();

            listIngredientsFireBase.add(ing);
            ingredientKeyHashTable.put(ing, key);
        }

        loadIngredients();

    }


    public void declareDatabase() {
        database = FirebaseDatabase.getInstance();
        ingredient_table = database.getReference("Ingredient");
    }


}
