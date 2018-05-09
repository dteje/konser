package com.visual.conserapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.visual.conserapp.Model.Favs;

public class popFavs extends Activity{

    Favs favs;
    DatabaseReference favs_table;
    FirebaseDatabase database;

    Intent intent;

    TextInputLayout textInputLayout;


    public  class Pop extends Activity {
        @Override
        public void onCreate (Bundle saveInstanceState) {
            super.onCreate(saveInstanceState);
            setContentView(R.layout.pop_favs);

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);

            int width = dm.widthPixels;
            int height = dm.heightPixels;

            getWindow().setLayout((int) (width * 0.8), (int) (height * 0.8));

            declareDatabase();

            intent = getIntent();

            generateFavs(intent);

            textInputLayout = (TextInputLayout) findViewById(R.id.textInputLayout);


        }
    }

    public void generateFavs(Intent in){
        Bundle extras = in.getExtras();
        String nameOfficial = extras.getString("nameSandwichOfficial");
        String nameUser = askName();
        Double price = extras.getDouble("price");
        String listIngredients = extras.getString("listIngredients");

        favs = new Favs(nameOfficial, nameUser, listIngredients, price);
        String id_favs = "Favs " + String.valueOf(System.currentTimeMillis());
        favs_table.child(id_favs).setValue(favs);

    }

    public void closePopUp (View view){
        //Button cancel = (Button) view.findViewById(R.id.cancelAddToFavs);

    }

    public String askName(){
        String userName = textInputLayout.getEditText().getText().toString();

        return userName;
    }

    public void declareDatabase() {
        database = FirebaseDatabase.getInstance();
        favs_table = database.getReference("Favs");

    }
}
