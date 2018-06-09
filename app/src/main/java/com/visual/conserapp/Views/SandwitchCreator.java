package com.visual.conserapp.Views;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.visual.conserapp.Adapter.AdapterData;
import com.visual.conserapp.Adapter.RecyclerClickListener;
import com.visual.conserapp.AlertFactory.AlertFactory;
import com.visual.conserapp.AlertFactory.AlertParent;
import com.visual.conserapp.Database.Database;
import com.visual.conserapp.Model.Favs;

import java.text.DecimalFormat;

import com.visual.conserapp.Model.Ingredient;
import com.visual.conserapp.Model.Order;
import com.visual.conserapp.R;

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
    ArrayList<Ingredient> listIngredientsFireBase;

    Order orderRes;

    int numButtons = 5;
    private Button[] btn = new Button[numButtons];
    private Button btn_unfocus;
    private int[] btn_id = {R.id.bMeat, R.id.bVeggies, R.id.bCheese, R.id.bSpecial, R.id.bSauces};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandwitch_creator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Sandwitch Creator");
        toolbar.setTitleTextColor(Color.rgb(255, 255, 255));
        setSupportActionBar(toolbar);


        linearLayout = (LinearLayout) findViewById(R.id.generalLinearLayout);

        listData = new ArrayList<String>();
        listSandwich = new ArrayList<String>();
        listPrice = new ArrayList<Double>();
        listIngredientsFireBase = new ArrayList<Ingredient>();

        recycler = (RecyclerView) findViewById(R.id.recyclerIngredientesId);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recycleDividerManager();

        recycler.addOnItemTouchListener(
                new RecyclerClickListener(this, new RecyclerClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        addToSandwitch(view, position);
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


        declareDatabase();
        obtainDataFirebase();

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

    private void setFocus(Button btn_unfocus, Button btn_focus) {
        btn_unfocus.setTextColor(Color.rgb(49, 50, 51));
        btn_unfocus.setBackgroundColor(Color.rgb(207, 207, 207));
        btn_focus.setTextColor(Color.rgb(255, 255, 255));
        btn_focus.setBackgroundColor(Color.rgb(3, 106, 150));
        this.btn_unfocus = btn_focus;
    }

    public void addToSandwitch(View view, int position) {
        TextView textView = (TextView) view.findViewById(R.id.idData);
        String ingredientName = textView.getText().toString();

        AlertFactory alertFactory = new AlertFactory();
        AlertParent alertParent = alertFactory.generateAlert("RepetitionSandwich");

        if (maxRepetitionIngredient(ingredientName)) {
            alertParent.printAlert(this);
        } else {
            listSandwich.add(ingredientName);
            listPrice.add(ingredientPrice);
            printIngredients();
        }
    }

    public boolean maxRepetitionIngredient(String ingredient) {
        int numRepetitions = 0;
        int maxRepetitions = 2;
        for (int i = 0; i < listSandwich.size(); i++) {
            String res = listSandwich.get(i);
            if (ingredient == res) numRepetitions++;
            if (numRepetitions == maxRepetitions) return true;
        }
        return false;
    }


    public void printIngredients() {
        TextView finalSandwitch = (TextView) linearLayout.findViewById(R.id.finalSandWitch);
        finalSandwitch.setText(listSandwich.toString());
    }

    public void addToCart(View view) {

        String orderId = "Order " + String.valueOf(System.currentTimeMillis());
        String quantity = "1";
        String price = String.valueOf(obtainPrice());
        String discount = "0";

        AlertFactory alertFactory = new AlertFactory();
        AlertParent alertParent = alertFactory.generateAlert("EmptySandwich");

        if (listSandwich.isEmpty()) alertParent.printAlert(this);
        else {
            orderRes = new Order(orderId, listSandwich.toString(), quantity, price, discount);

            new Database(getBaseContext()).addToCart(orderRes);
            Toast.makeText(SandwitchCreator.this, "Añadido al carrito!", Toast.LENGTH_SHORT).show();
        }

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
                generateIngredients("Carne_Pescado");
                ingredientPrice = 0.7;
                break;
            case R.id.bVeggies:
                setFocus(btn_unfocus, btn[1]);
                generateIngredients("Verduras");
                ingredientPrice = 0.7;
                break;
            case R.id.bCheese:
                setFocus(btn_unfocus, btn[2]);
                generateIngredients("Queso");
                ingredientPrice = 0.4;
                break;
            case R.id.bSpecial:
                setFocus(btn_unfocus, btn[3]);
                generateIngredients("Especial");
                ingredientPrice = 0.6;
                break;
            case R.id.bSauces:
                setFocus(btn_unfocus, btn[4]);
                generateIngredients("Salsas");
                ingredientPrice = 0.4;
                break;
        }

    }

    public void modifyAdapter() {
        AdapterData adapter = new AdapterData(listData);
        recycler.setAdapter(adapter);
    }

    public void addToFavs(View view) {

        AlertFactory alertFactory = new AlertFactory();
        AlertParent alertParent = alertFactory.generateAlert("EmptySandwich");

        if (listSandwich.size() == 0) alertParent.printAlert(this);
        else {

            Intent popUp = new Intent(SandwitchCreator.this, popFavs.class);

            String nameSandwichOfficial = listSandwich.toString();
            double price = obtainPrice();
            String listIngredients = listSandwich.toString();

            Bundle bundle = new Bundle();
            bundle.putString("nameSandwichOfficial", nameSandwichOfficial);
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

    public void declareDatabase() {
        database = FirebaseDatabase.getInstance();
        favs_table = database.getReference("Favs");

    }

    public void obtainDataFirebase() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("Ingredient");

        final ArrayList<Ingredient> test = new ArrayList<Ingredient>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                obtainIngredients(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void obtainIngredients(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            String name = ds.getValue(Ingredient.class).getName();
            String type = ds.getValue(Ingredient.class).getType();

            Ingredient ing = new Ingredient(name, type);

            listIngredientsFireBase.add(ing);
        }

        generateIngredients("Carne_Pescado");
    }

    public void generateIngredients(String ingredientType) {

        listData.clear();

        for (int i = 0; i < listIngredientsFireBase.size(); i++) {
            Ingredient ing = listIngredientsFireBase.get(i);

            String condition = ing.getType();

            if (condition.equals(ingredientType)) {
                listData.add(ing.getName());
            }
        }

        modifyAdapter();
    }
}