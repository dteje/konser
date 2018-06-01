package com.visual.conserapp.Views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.visual.conserapp.R;

public class uploadIngredients extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_ingredients);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Sandwitch Creator");
        //setSupportActionBar(toolbar);


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

    public void uploadIngredient (View view){
        String ingregientType = obtainType(view);
    }

    public String obtainType(View view){
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiobuttongroup);
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        int idx = radioGroup.indexOfChild(radioButton);

        RadioButton r = (RadioButton)  radioGroup.getChildAt(idx);
        String type = r.getText().toString();

        Log.d("type", type);

        return type;
    }
}
