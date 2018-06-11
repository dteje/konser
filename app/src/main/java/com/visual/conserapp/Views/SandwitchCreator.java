package com.visual.conserapp.Views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.visual.conserapp.Adapter.SandwichCreatorAdapter;
import com.visual.conserapp.AlertFactory.AlertFactory;
import com.visual.conserapp.AlertFactory.AlertParent;
import com.visual.conserapp.Database.Database;
import com.visual.conserapp.Memento.CareTaker;
import com.visual.conserapp.Memento.Memento;
import com.visual.conserapp.Memento.Originator;
import com.visual.conserapp.Model.Favs;

import java.text.DecimalFormat;

import com.visual.conserapp.Model.Ingredient;
import com.visual.conserapp.Model.Order;
import com.visual.conserapp.R;

import java.util.ArrayList;

public class SandwitchCreator extends AppCompatActivity {

    ArrayList<String> listDataCarne;
    ArrayList<String> listDataVerdura;
    ArrayList<String> listDataQueso;
    ArrayList<String> listDataSalsas;
    ArrayList<String> listDataEspecial;
    ArrayList<String> listSandwich;
    ArrayList<Double> listPrice;
    double ingredientPrice;
    double totalprice;
    String currentType;
    ArrayList<String> listPriceTypes;

    RecyclerView recycler;
    LinearLayout linearLayout;
    TextView finalSandwitch;

    Favs favs;
    DatabaseReference favs_table;
    DatabaseReference ing_table;
    FirebaseDatabase database;

    Originator originator;
    CareTaker careTaker;
    int saveFiles;
    int currentFiles;

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

        originator = new Originator();
        careTaker = new CareTaker();
        saveFiles = 0;
        currentFiles = 0;

        linearLayout = (LinearLayout) findViewById(R.id.generalLinearLayout);
        finalSandwitch = (TextView) linearLayout.findViewById(R.id.finalSandWitch);

        listDataCarne = new ArrayList<String>();
        listDataVerdura = new ArrayList<String>();
        listDataQueso = new ArrayList<String>();
        listDataSalsas = new ArrayList<String>();
        listDataEspecial = new ArrayList<String>();

        listSandwich = new ArrayList<String>();
        listPrice = new ArrayList<Double>();
        listPriceTypes = new ArrayList<String>();

        saveMementoState();

        recycler = (RecyclerView) findViewById(R.id.recyclerIngredientesId);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycleDividerManager();

        for (int i = 0; i < btn.length; i++) {
            btn[i] = (Button) findViewById(btn_id[i]);
            btn[i].setBackgroundColor(Color.rgb(207, 207, 207));
        }

        btn_unfocus = btn[0];
        setFocus(btn_unfocus, btn[0]);

        ingredientPrice = 0.7;

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

    public void addToSandwitch(View view, String ingredientName) {
        String type = currentType;


        AlertFactory alertFactory = new AlertFactory();
        AlertParent alertParent = alertFactory.generateAlert("RepetitionSandwich");

        if (maxRepetitionIngredient(ingredientName)) {
            alertParent.printAlert(this);
        } else {
            listSandwich.add(ingredientName);

            saveMementoState();

            manageIngredientPrice(ingredientPrice, "add");

            finalSandwitch.setText(listSandwich.toString());


        }
    }

    public void addToCart(View view) {

        String orderId = "Order " + String.valueOf(System.currentTimeMillis());
        String quantity = "1";
        String price = obtainPrice();
        String discount = "0";

        AlertFactory alertFactory = new AlertFactory();
        AlertParent alertParent = alertFactory.generateAlert("EmptySandwich");

        if (listSandwich.isEmpty()) alertParent.printAlert(this);
        else {
            orderRes = new Order(orderId, listSandwich.toString(), quantity, price, discount);

            new Database(getBaseContext()).addToCart(orderRes);
            Toast.makeText(SandwitchCreator.this, "Añadido al carrito!", Toast.LENGTH_SHORT).show();
        }

        Log.d("totalprice", obtainPrice());

    }

    public void addToFavs(View view) {

        AlertFactory alertFactory = new AlertFactory();
        AlertParent alertParent = alertFactory.generateAlert("EmptySandwich");

        if (listSandwich.size() == 0) alertParent.printAlert(this);
        else {

            Intent popUp = new Intent(SandwitchCreator.this, popFavs.class);

            String nameSandwichOfficial = listSandwich.toString();
            double price = Double.parseDouble(obtainPrice());
            String listIngredients = listSandwich.toString();

            Bundle bundle = new Bundle();
            bundle.putString("nameSandwichOfficial", nameSandwichOfficial);
            bundle.putDouble("price", price);
            bundle.putString("listIngredients", listIngredients);
            popUp.putExtras(bundle);

            startActivity(popUp);
        }
    }

    public void manageIngredientPrice(double price, String operation) {
        switch (operation) {
            case "add":
                totalprice += price;
                break;
            case "subs":
                totalprice -= price;
                break;
            case "clear":
                totalprice = 0;
                break;
        }
    }

    public void saveMementoState() {
        originator.setState((ArrayList<String>) listSandwich.clone());
        careTaker.add(originator.saveStateToMemento());
        saveFiles++;
        currentFiles++;

        Log.d("SAVE MEMETO>", "aqui llega correcamente siempre");
    }

    public void resetMemento() {
        careTaker.cleanMementoList();
        currentFiles = 0;
        saveFiles = 0;
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

    public void undoLastIngredient(View view) {
        if (currentFiles >= 1) {
            currentFiles--;
            originator.getStateFromMemento(careTaker.get(currentFiles));
            listSandwich = originator.getState();

            manageIngredientPrice(ingredientPrice, "subs");

            finalSandwitch.setText(listSandwich.toString());


        } else
            Toast.makeText(SandwitchCreator.this, "Ninguna acción para deshacer", Toast.LENGTH_SHORT).show();
    }

    public void redoLastIngredient(View view) {
        if ((saveFiles - 1) > currentFiles) {
            currentFiles++;
            originator.getStateFromMemento(careTaker.get(currentFiles));
            listSandwich = originator.getState();

            manageIngredientPrice(ingredientPrice, "add");

            finalSandwitch.setText(listSandwich.toString());
        } else
            Toast.makeText(SandwitchCreator.this, "Ninguna acción para rehacer", Toast.LENGTH_SHORT).show();
    }


    public void removeAllElements(View view) {
        if (listSandwich.size() != 0) {
            listSandwich.clear();
            listPrice.clear();

            resetMemento();

            manageIngredientPrice(ingredientPrice, "clear");
        }
        finalSandwitch.setText(listSandwich.toString());
    }

    public void recycleDividerManager() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler.getContext(),
                DividerItemDecoration.VERTICAL);
        recycler.addItemDecoration(dividerItemDecoration);
    }

    public void buttonIngredient(View view) {
        String type;

        switch (view.getId()) {
            case R.id.bMeat:
                setFocus(btn_unfocus, btn[0]);
                type = "Carne_Pescado";
                modifyAdapter(type);
                currentType = type;
                ingredientPrice = 0.7;
                break;
            case R.id.bVeggies:
                setFocus(btn_unfocus, btn[1]);
                type = "Verduras";
                modifyAdapter(type);
                currentType = type;
                ingredientPrice = 0.7;
                break;
            case R.id.bCheese:
                setFocus(btn_unfocus, btn[2]);
                type = "Queso";
                modifyAdapter(type);
                currentType = type;
                ingredientPrice = 0.4;
                break;
            case R.id.bSpecial:
                setFocus(btn_unfocus, btn[3]);
                type = "Especial";
                modifyAdapter(type);
                currentType = type;
                ingredientPrice = 0.6;
                break;
            case R.id.bSauces:
                setFocus(btn_unfocus, btn[4]);
                type = "Salsas";
                modifyAdapter(type);
                currentType = type;
                ingredientPrice = 0.4;
                break;
        }

    }

    public void modifyAdapter(String condition) {
        SandwichCreatorAdapter adapter = new SandwichCreatorAdapter();

        switch (condition) {
            case "Carne_Pescado":
                adapter = new SandwichCreatorAdapter(listDataCarne, this, this);
                break;
            case "Verduras":
                adapter = new SandwichCreatorAdapter(listDataVerdura, this, this);
                break;
            case "Queso":
                adapter = new SandwichCreatorAdapter(listDataQueso, this, this);
                break;
            case "Salsas":
                adapter = new SandwichCreatorAdapter(listDataSalsas, this, this);
                break;
            case "Especial":
                adapter = new SandwichCreatorAdapter(listDataEspecial, this, this);
                break;
        }
        recycler.setAdapter(adapter);
    }


    public String obtainPrice() {
        if (totalprice < 2) totalprice = 2;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        totalprice = Double.parseDouble(decimalFormat.format(totalprice));
        return String.valueOf(totalprice);
    }
    /*
    public String obtainPrice() {
        String res;
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        for (int i = 0; i < listPrice.size(); i++) {
            res = listSandwich.get(i);

        }
        if (res < 2) res = 2;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        res = Double.parseDouble(decimalFormat.format(res));
        return res;
    }
    */


    public void declareDatabase() {
        database = FirebaseDatabase.getInstance();
        favs_table = database.getReference("Favs");
        ing_table = database.getReference("Ingredient");
    }

    public void obtainDataFirebase() {
        declareDatabase();

        ing_table.addValueEventListener(new ValueEventListener() {
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

            String condition = ing.getType();

            switch (condition) {
                case "Carne_Pescado":
                    listDataCarne.add(ing.getName());
                    break;
                case "Verduras":
                    listDataVerdura.add(ing.getName());
                    break;
                case "Queso":
                    listDataQueso.add(ing.getName());
                    break;
                case "Salsas":
                    listDataSalsas.add(ing.getName());
                    break;
                case "Especial":
                    listDataEspecial.add(ing.getName());
                    break;
            }
        }

        modifyAdapter("Carne_Pescado");
    }

}