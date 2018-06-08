package com.visual.conserapp.Views;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.InflateException;
import android.view.View;
import android.widget.EditText;
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

public class EditIngredient extends AppCompatActivity {

    Favs favs;
    DatabaseReference ingredient_table;
    FirebaseDatabase database;

    String lastKey;
    TextInputLayout tv;
    RadioGroup radioGroup;

    Hashtable<Ingredient, String> ingredientKeyHashTable;

    String name;
    String type;
    String key;

    String newName;
    String newType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ingredients);

        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        type = bundle.getString("type");
        key = bundle.getString("key");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        tv = (TextInputLayout) findViewById(R.id.nameIngredient);
        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText(name);

        radioGroup = (RadioGroup) findViewById(R.id.radiobuttongroup);
        radioGroupDefault();

        declareDatabase();
    }

    public void radioGroupDefault() {
        switch (type) {
            case "Carne_Pescado":
                radioGroup.check(R.id.rbCarne);
                break;
            case "Verduras":
                radioGroup.check(R.id.rbVerduras);
                break;
            case "Queso":
                radioGroup.check(R.id.rbQueso);
                break;
            case "Salsas":
                radioGroup.check(R.id.rbSalsas);
                break;
            case "Especial":
                radioGroup.check(R.id.rbEspecial);
                break;
        }
    }

    public void declareDatabase() {
        database = FirebaseDatabase.getInstance();
        ingredient_table = database.getReference("Ingredient");

    }

    public void editIngredient(View view) {
        newType = obtainType();
        newName = obtainName(view);

        AlertFactory alertFactory = new AlertFactory();
        AlertParent alertParent = alertFactory.generateAlert("nullIngredient");

        if (newName.equals("")) alertParent.printAlert(this);
        else {
            Ingredient ingredient = new Ingredient(newName, newType);

            ingredient_table.child(key).setValue(ingredient);
            Toast toast = Toast.makeText(EditIngredient.this, "Se ha modificado el ingrediente!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();

            finish();
        }
    }

    public void cancelEdit(View view) {
        finish();
    }


    public String obtainName(View view) {
        String name = tv.getEditText().getText().toString();
        return name;
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

