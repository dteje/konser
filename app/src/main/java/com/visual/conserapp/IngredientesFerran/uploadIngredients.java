package com.visual.conserapp.IngredientesFerran;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.visual.conserapp.AlertFactory.AlertFactory;
import com.visual.conserapp.AlertFactory.AlertParent;
import com.visual.conserapp.Model.Favs;
import com.visual.conserapp.Model.Ingredient;
import com.visual.conserapp.R;

import java.util.ArrayList;
import java.util.Hashtable;

public class uploadIngredients extends AppCompatActivity {

    Favs favs;
    DatabaseReference ingredient_table;
    FirebaseDatabase database;

    String lastKey;
    TextInputLayout tv;
    RadioGroup radioGroup;

    Hashtable<Ingredient, String> ingredientKeyHashTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_ingredients);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Upload Ingredients");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        tv = (TextInputLayout) findViewById(R.id.nameIngredient);
        ingredientKeyHashTable = new Hashtable<Ingredient, String>();
        radioGroup = (RadioGroup) findViewById(R.id.radiobuttongroup);
        radioGroup.check(R.id.rbCarne);

        obtainDataFirebase();


    }

    public void declareDatabase() {
        database = FirebaseDatabase.getInstance();
        ingredient_table = database.getReference("Ingredient");

    }

    public void uploadIngredient(View view) {
        String ingregientType = obtainType(); //si esto peta añadirle el view
        String name = obtainName(view);
        AlertFactory alertFactory = new AlertFactory();
        AlertParent alertParent = alertFactory.generateAlert("nullIngredient");

        if (name.equals("")) alertParent.printAlert(this);
        else {
            Ingredient ingredient = new Ingredient(name, ingregientType);

            int idInt = Integer.parseInt(lastKey) + 1;
            String id = String.valueOf(idInt);


            ingredient_table.child(id).setValue(ingredient);
            Toast toast = Toast.makeText(uploadIngredients.this, "Se ha subido el ingrediente!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();

            tv.getEditText().getText().clear();
        }

    }


    public void obtainDataFirebase() {

        declareDatabase();

        final ArrayList<Ingredient> test = new ArrayList<Ingredient>();

        ingredient_table.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    obtainIngredients(dataSnapshot);
                } else lastKey = String.valueOf(999);
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

    public String obtainName(View view) {

        String name = tv.getEditText().getText().toString();
        return name;
    }

    public String obtainType() {
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
            View radioButton = radioGroup.findViewById(radioButtonID);
            int idx = radioGroup.indexOfChild(radioButton);

            RadioButton r = (RadioButton) radioGroup.getChildAt(idx);
            String type = r.getText().toString();

            return type;
    }
}
