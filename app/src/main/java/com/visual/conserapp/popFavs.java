package com.visual.conserapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.visual.conserapp.Model.Favs;

public class popFavs extends Activity {

    Favs favs;
    DatabaseReference favs_table;
    FirebaseDatabase database;

    Intent intent;

    EditText textInputLayout;

    String nameOfficial;
    String nameUser;
    double price;
    String listIngredients;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.pop_favs);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width), (int) (height * 0.3));


        declareDatabase();

        intent = getIntent();

        getDataFromSandwichCreator(intent);

        textInputLayout = (EditText) findViewById(R.id.textInputNombre);


    }

    public void getDataFromSandwichCreator(Intent in) {
        Bundle extras = in.getExtras();
        nameOfficial = extras.getString("nameSandwichOfficial");
        price = extras.getDouble("price");
        Log.d("price_pop", String.valueOf(price));
        listIngredients = extras.getString("listIngredients");
    }

    public void generateFavs(View view) {

        nameUser = askName();

        if (nameUser.equals("")) emptyNameAlert();
        else {
            favs = new Favs(nameOfficial, nameUser, listIngredients, price);
            String id_favs = "Favs " + String.valueOf(System.currentTimeMillis());
            favs_table.child(id_favs).setValue(favs);

            finish();
        }

    }

    public void emptyNameAlert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Cuidado!");
        builder1.setMessage("Tu bocadillo no tiene nombre!");
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

    public void closePopUp(View view) {
        finish();
    }

    public String askName() {
        String userName = textInputLayout.getText().toString();

        return userName;
    }

    public void declareDatabase() {
        database = FirebaseDatabase.getInstance();
        favs_table = database.getReference("Favs");

    }
}
