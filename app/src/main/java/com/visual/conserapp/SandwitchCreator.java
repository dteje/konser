package com.visual.conserapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SandwitchCreator extends AppCompatActivity {

    ArrayList<String> listData;
    ArrayList<String> listSandWitch;
    RecyclerView recycler;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandwitch_creator);
        setTitle("Sandwitch Creator");

        linearLayout = (LinearLayout) findViewById(R.id.generalLinearLayout);

        listData = new ArrayList<String>();
        listSandWitch = new ArrayList<String>();

        recycler = (RecyclerView) findViewById(R.id.recyclerIngredientesId);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recycleDividerManager();

        recycler.addOnItemTouchListener( // and the click is handled
                new RecyclerClickListener(this, new RecyclerClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        storeIngredients(view, position);
                    }

                }));

        generateCarne();

    }

    public void storeIngredients(View view, int position) {
        TextView textView = (TextView) view.findViewById(R.id.idData);
        String ingredientName = textView.getText().toString();
        listSandWitch.add(ingredientName);
        TextView finalSandwitch = (TextView) linearLayout.findViewById(R.id.finalSandWitch);
        finalSandwitch.setText(listSandWitch.toString());


    }

    public void recycleDividerManager() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler.getContext(),
                DividerItemDecoration.VERTICAL);
        recycler.addItemDecoration(dividerItemDecoration);
    }


    public void buttonIngredient(View view) {

        switch (view.getId()) {
            case R.id.bMeat:
                generateCarne();
                break;
            case R.id.bVeggies:
                generateVerduras();
                break;
            case R.id.bCheese:
                generateQueso();
                break;
            case R.id.bSpecial:
                generateEspecial();
                break;
            case R.id.bSauces:
                generateSalsas();
                break;
        }

    }

    public void modifyAdapter() {
        AdapterData adapter = new AdapterData(listData);
        recycler.setAdapter(adapter);
    }

    // test modification to go back 2


    public void generateCarne() {

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

        modifyAdapter();
    }

    public void generateVerduras() {

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

        modifyAdapter();
    }

    public void generateQueso() {

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

        modifyAdapter();
    }

    public void generateEspecial() {

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

        modifyAdapter();
    }

    public void generateSalsas() {

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

        modifyAdapter();
    }
}
