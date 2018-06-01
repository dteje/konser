package com.visual.conserapp.Views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.visual.conserapp.Model.Favs;
import com.visual.conserapp.Model.Ingredient;
import com.visual.conserapp.R;

import java.util.ArrayList;

public class uploadIngredients extends AppCompatActivity {

    Favs favs;
    DatabaseReference ingredient_table;
    FirebaseDatabase database;

    ArrayList<Ingredient> listIngredientsFireBase;
    String lastKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_ingredients);

        listIngredientsFireBase = new ArrayList<Ingredient>();

        declareDatabase();
        obtainDataFirebase();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sandwitch_creator_menu, menu);
        return true;
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

    public void declareDatabase() {
        database = FirebaseDatabase.getInstance();
        ingredient_table = database.getReference("Ingredient");

    }

    public void uploadIngredient (View view){
        String ingregientType = obtainType(); //si esto peta añadirle el view
        String name = obtainName(view);

        Ingredient ingredient = new Ingredient(name, ingregientType);

        int idInt = Integer.parseInt(lastKey) + 1;
        String id = String.valueOf(idInt);

        ingredient_table.child(id).setValue(ingredient);


    }

    public void obtainDataFirebase() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("Ingredient");

        final ArrayList<Ingredient> test = new ArrayList<Ingredient>();

        ref.addValueEventListener(new ValueEventListener() {
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
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            lastKey = ds.getKey();
        }
    }

    public String obtainName(View view){
        TextInputLayout tv = (TextInputLayout) findViewById(R.id.nameIngredient);
        String name = tv.getEditText().getText().toString();
        return name;
    }

    public String obtainType(){
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiobuttongroup);
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        int idx = radioGroup.indexOfChild(radioButton);

        RadioButton r = (RadioButton)  radioGroup.getChildAt(idx);
        String type = r.getText().toString();

        return type;
    }
}
