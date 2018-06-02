package com.visual.conserapp.Views;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

    String lastKey;
    TextInputLayout tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_ingredients);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        tv = (TextInputLayout) findViewById(R.id.nameIngredient);

        declareDatabase();
        obtainDataFirebase();


    }

    public void declareDatabase() {
        database = FirebaseDatabase.getInstance();
        ingredient_table = database.getReference("Ingredient");

    }

    public void uploadIngredient(View view) {
        String ingregientType = obtainType(); //si esto peta añadirle el view
        String name = obtainName(view);

        if (name.equals("")) alertNoName();
        else {
            Ingredient ingredient = new Ingredient(name, ingregientType);

            int idInt = Integer.parseInt(lastKey) + 1;
            String id = String.valueOf(idInt);


            ingredient_table.child(id).setValue(ingredient);
            Toast toast = Toast.makeText(uploadIngredients.this, "Se ha subido el ingrediente!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();

            tv.getEditText().getText().clear();
        }

    }


    public void obtainDataFirebase() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("Ingredient");

        final ArrayList<Ingredient> test = new ArrayList<Ingredient>();

        ref.addValueEventListener(new ValueEventListener() {
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

    public void alertNoName() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Cuidado!");
        builder1.setMessage("No has especificado el nombre del ingrediente");
        builder1.setCancelable(true);
        builder1.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertRepetition = builder1.create();
        alertRepetition.show();
    }


    public String obtainType() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiobuttongroup);
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        int idx = radioGroup.indexOfChild(radioButton);

        RadioButton r = (RadioButton) radioGroup.getChildAt(idx);
        String type = r.getText().toString();

        return type;
    }
}
