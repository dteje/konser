package com.visual.conserapp.Views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Debug;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD:app/src/main/java/com/visual/conserapp/SandwitchCreator.java
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.visual.conserapp.Model.Favs;

import java.text.DecimalFormat;
=======
import com.visual.conserapp.AdapterData;
import com.visual.conserapp.R;
import com.visual.conserapp.RecyclerClickListener;
import com.visual.conserapp.Views.Cart;
import com.visual.conserapp.Views.Offers;

>>>>>>> develop:app/src/main/java/com/visual/conserapp/Views/SandwitchCreator.java
import java.util.ArrayList;

public class SandwitchCreator extends AppCompatActivity {

    ArrayList<String> listData;
    ArrayList<String> listSandwich;
    ArrayList<Double> listPrice;
    double ingredientPrice;
    RecyclerView recycler;
    LinearLayout linearLayout;

    Favs favs;
    DatabaseReference favs_table;
    FirebaseDatabase database;

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
        listSandwich = new ArrayList<String>();
        listPrice = new ArrayList<Double>();


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

        // Añadir el primer precio de los ingredientes, ya que no clickamos en el botón de Carne y por lo tanto no se cambia automaticamente
        ingredientPrice = 0.7;

        generateCarne();

        declareDatabase();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sandwitch_creator_menu, menu);
        return true;
    }

    public boolean onNavSuperior(MenuItem menuitem) {
        View view = menuitem.getActionView();
        int id = menuitem.getItemId();
        Intent intent;
        if (id == R.id.cart_id) intent = cartIntent;
        else intent = new Intent(this, Offers.class);
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
        if (maxRepetitionIngredient(ingredientName)) {
            showRepetitionAlert();
        } else {
            listSandwich.add(ingredientName);
            listPrice.add(ingredientPrice);
            printIngredients();
        }
    }

    // Max in this case is 2
    public boolean maxRepetitionIngredient(String ingredient) {
        int numRepetitions = 0;
        for (int i = 0; i < listSandwich.size(); i++) {
            String res = listSandwich.get(i);
            if (ingredient == res) numRepetitions++;
            if (numRepetitions == 2) return true;
        }
        return false;
    }

    public void showRepetitionAlert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Cuidado!");
        builder1.setMessage("Has añadido demasiados ingredientes iguales!");
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

    public void emptyAlert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Cuidado!");
        builder1.setMessage("Tienes un bocadillo vacio!");
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

    public void printIngredients() {
        TextView finalSandwitch = (TextView) linearLayout.findViewById(R.id.finalSandWitch);
        finalSandwitch.setText(listSandwich.toString());
    }

    public void addToCart(View view) {

        cartIntent = new Intent(getBaseContext(), Cart.class);
        cartIntent.putExtra("list", listSandwich.toString());

    }

    public void removeLastElement(View view) {
        int id = view.getId();
        if (listSandwich.size() != 0) {
            if (id == R.id.removeLastButton) {
                listSandwich.remove(listSandwich.size() - 1);
                listPrice.remove(listPrice.size() - 1);
            }
            printIngredients();
        }
    }

    public void removeAllElements(View view) {
        int id = view.getId();
        if (listSandwich.size() != 0) {
            if (id == R.id.removeAllButton) {
                listSandwich.clear();
                listPrice.clear();
            }
        }
        printIngredients();
    }

    public void recycleDividerManager() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler.getContext(),
                DividerItemDecoration.VERTICAL);
        recycler.addItemDecoration(dividerItemDecoration);
    }

// Más adelante tocará añadir que el precio se lo pueda especificar el restaurante. Entonces
// Lo obtendriamos de la firebase.

    public void buttonIngredient(View view) {

        switch (view.getId()) {
            case R.id.bMeat:
                setFocus(btn_unfocus, btn[0]);
                generateCarne();
                ingredientPrice = 0.7;
                break;
            case R.id.bVeggies:
                setFocus(btn_unfocus, btn[1]);
                generateVerduras();
                ingredientPrice = 0.7;
                break;
            case R.id.bCheese:
                setFocus(btn_unfocus, btn[2]);
                generateQueso();
                ingredientPrice = 0.4;
                break;
            case R.id.bSpecial:
                setFocus(btn_unfocus, btn[3]);
                generateEspecial();
                ingredientPrice = 0.6;
                break;
            case R.id.bSauces:
                setFocus(btn_unfocus, btn[4]);
                generateSalsas();
                ingredientPrice = 0.4;
                break;
        }

    }

    public void modifyAdapter() {
        AdapterData adapter = new AdapterData(listData);
        recycler.setAdapter(adapter);
    }
    /*
    public void addToFavs(View view) {

        String nameSandwichUser = askSandwichname();
        double price = obtainPrice();
        favs = new Favs(listSandwich.toString(), nameSandwichUser, listSandwich.toString(), price);
        String id_favs = "Favs " + String.valueOf(System.currentTimeMillis());
        favs_table.child(id_favs).setValue(favs);
    }*/

    public void addToFavs(View view) {

        if (listSandwich.size() == 0) emptyAlert();
        else {

            Intent popUp = new Intent(SandwitchCreator.this, popFavs.class);

            String nameSandwichUser = askSandwichname();
            String nameSandwichOfficial = listSandwich.toString();
            double price = obtainPrice();
            String listIngredients = listSandwich.toString();

            Bundle bundle = new Bundle();
            bundle.putString("nameSandwichOfficial", nameSandwichOfficial);
            bundle.putString("nameSandwichUser", nameSandwichUser);
            bundle.putDouble("price", price);
            bundle.putString("listIngredients", listIngredients);
            popUp.putExtras(bundle);

            startActivity(popUp);
        }
    }


    public double obtainPrice() {
        double res = 0;
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        for (int i = 0; i < listPrice.size(); i++) {
            res += listPrice.get(i);
            String reslog = "res " + i;
        }
        if (res < 2) res = 2;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        res = Double.parseDouble(decimalFormat.format(res));
        return res;
    }

    public double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.00");
        return Double.valueOf(twoDForm.format(d));
    }

    public String askSandwichname() {
        return "test";
    }


    public void declareDatabase() {
        database = FirebaseDatabase.getInstance();
        favs_table = database.getReference("Favs");

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
