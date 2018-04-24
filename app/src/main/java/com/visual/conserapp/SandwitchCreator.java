package com.visual.conserapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class SandwitchCreator extends AppCompatActivity {

    ArrayList<String> listData;
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandwitch_creator);
        setTitle("Sandwitch Creator");

        recycler = (RecyclerView) findViewById(R.id.recyclerIngredientesId);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        listData = new ArrayList<String>();

        generateCarne();
    }

    public void buttonIngredient(View view){

        switch(view.getId()){
            case R.id.bMeat: generateCarne();
                break;
            case R.id.bVeggies: generateVerduras();
                break;
            case R.id.bCheese: generateQueso();
                break;
            case R.id.bSpecial: generateEspecial();
                break;
            case R.id.bSauces: generateSalsas();
                break;
        }

    }

    public void generateCarne (){

        listData.clear();

        listData.add("Jamón York");
        listData.add("Jamón York");
        listData.add("Jamón York");
        listData.add("Jamón York");
        listData.add("Jamón York");
        listData.add("Jamón York");
        listData.add("Jamón York");
        listData.add("Jamón York");
        listData.add("Jamón York");
        listData.add("Jamón York");
        listData.add("Jamón York");

        AdapterData adapter = new AdapterData(listData);
        recycler.setAdapter(adapter);
    }

    public void generateVerduras (){

        listData.clear();

        listData.add("Tomate");
        listData.add("Tomate");
        listData.add("Tomate");
        listData.add("Tomate");
        listData.add("Tomate");
        listData.add("Tomate");
        listData.add("Tomate");
        listData.add("Tomate");
        listData.add("Tomate");
        listData.add("Tomate");
        listData.add("Tomate");

        AdapterData adapter = new AdapterData(listData);
        recycler.setAdapter(adapter);
    }

    public void generateQueso (){

        listData.clear();

        listData.add("Manchego");
        listData.add("Manchego");
        listData.add("Manchego");
        listData.add("Manchego");
        listData.add("Manchego");
        listData.add("Manchego");
        listData.add("Manchego");
        listData.add("Manchego");
        listData.add("Manchego");
        listData.add("Manchego");
        listData.add("Manchego");

        AdapterData adapter = new AdapterData(listData);
        recycler.setAdapter(adapter);
    }

    public void generateEspecial (){

        listData.clear();

        listData.add("Huevo Frito");
        listData.add("Huevo Frito");
        listData.add("Huevo Frito");
        listData.add("Huevo Frito");
        listData.add("Huevo Frito");
        listData.add("Huevo Frito");
        listData.add("Huevo Frito");
        listData.add("Huevo Frito");
        listData.add("Huevo Frito");
        listData.add("Huevo Frito");
        listData.add("Huevo Frito");

        AdapterData adapter = new AdapterData(listData);
        recycler.setAdapter(adapter);
    }

    public void generateSalsas (){

        listData.clear();

        listData.add("Mayonesa");
        listData.add("Mayonesa");
        listData.add("Mayonesa");
        listData.add("Mayonesa");
        listData.add("Mayonesa");
        listData.add("Mayonesa");
        listData.add("Mayonesa");
        listData.add("Mayonesa");
        listData.add("Mayonesa");
        listData.add("Mayonesa");
        listData.add("Mayonesa");

        AdapterData adapter = new AdapterData(listData);
        recycler.setAdapter(adapter);
    }
}
