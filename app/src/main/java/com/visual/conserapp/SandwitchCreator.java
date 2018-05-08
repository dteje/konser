package com.visual.conserapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    Intent cartIntent;

    int numButtons = 5;
    private Button[] btn = new Button[numButtons];
    private Button btn_unfocus;
    private int[] btn_id = {R.id.bMeat, R.id.bVeggies, R.id.bCheese, R.id.bSpecial, R.id.bSauces};

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

        recycler.addOnItemTouchListener(
                new RecyclerClickListener(this, new RecyclerClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        storeIngredients(view, position);
                    }
                }));

        for (int i = 0; i < btn.length; i++) {
            btn[i] = (Button) findViewById(btn_id[i]);
            btn[i].setBackgroundColor(Color.rgb(207, 207, 207));
        }

        btn_unfocus = btn[0];
        setFocus(btn_unfocus, btn[0]);

        generateCarne();

    }

    public void removeLastElement(View view){
        int id = view.getId();
        if(listSandWitch.size() != 0) {
            if (id == R.id.removeLastButton) {
                listSandWitch.remove(listSandWitch.size() - 1);
            }
            printIngredients();
        }
    }

    public void removeAllElements(View view){
        int id = view.getId();
        if(listSandWitch.size() != 0){
            if(id == R.id.removeAllButton){
                listSandWitch.clear();
            }
        }
        printIngredients();
    }
    //test commit

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sandwitch_creator_menu, menu);
        return true;
    }

    public boolean onNavSuperior(MenuItem menuitem){
        View view = menuitem.getActionView();
        int id = menuitem.getItemId();
        Intent intent;
        if(id == R.id.cart_id)  intent = cartIntent;
        else intent = new Intent(this,Offers.class);
        startActivity(intent);

        return true;
    }


    private void setFocus(Button btn_unfocus, Button btn_focus) {
        btn_unfocus.setTextColor(Color.rgb(49, 50, 51));
        btn_unfocus.setBackgroundColor(Color.rgb(207, 207, 207));
        btn_focus.setTextColor(Color.rgb(255, 255, 255));
        btn_focus.setBackgroundColor(Color.rgb(3, 106, 150));
        this.btn_unfocus = btn_focus;
    }

    public void storeIngredients(View view, int position) {
        TextView textView = (TextView) view.findViewById(R.id.idData);
        String ingredientName = textView.getText().toString();
        listSandWitch.add(ingredientName);
        printIngredients();
    }

    public void printIngredients(){
        TextView finalSandwitch = (TextView) linearLayout.findViewById(R.id.finalSandWitch);
        finalSandwitch.setText(listSandWitch.toString());
    }

    public void addToCart(View view) {

        cartIntent = new Intent(getBaseContext(), Cart.class);
        cartIntent.putExtra("list", listSandWitch.toString());

    }

    public void recycleDividerManager() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler.getContext(),
                DividerItemDecoration.VERTICAL);
        recycler.addItemDecoration(dividerItemDecoration);
    }


    public void buttonIngredient(View view) {

        switch (view.getId()) {
            case R.id.bMeat:
                setFocus(btn_unfocus, btn[0]);
                generateCarne();
                break;
            case R.id.bVeggies:
                setFocus(btn_unfocus, btn[1]);
                generateVerduras();
                break;
            case R.id.bCheese:
                setFocus(btn_unfocus, btn[2]);
                generateQueso();
                break;
            case R.id.bSpecial:
                setFocus(btn_unfocus, btn[3]);
                generateEspecial();
                break;
            case R.id.bSauces:
                setFocus(btn_unfocus, btn[4]);
                generateSalsas();
                break;
        }

    }

    public void modifyAdapter() {
        AdapterData adapter = new AdapterData(listData);
        recycler.setAdapter(adapter);
    }


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
