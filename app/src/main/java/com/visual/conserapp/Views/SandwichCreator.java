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
import com.visual.conserapp.Adapter.SandwichCreatorAdapter;
import com.visual.conserapp.AlertFactory.AlertEnum;
import com.visual.conserapp.AlertFactory.AlertFactory;
import com.visual.conserapp.AlertFactory.AlertParent;
import com.visual.conserapp.Database.Database;
import com.visual.conserapp.Memento.CareTaker;
import com.visual.conserapp.Memento.Originator;

import java.text.DecimalFormat;

import com.visual.conserapp.Model.Ingredient;
import com.visual.conserapp.Model.Order;
import com.visual.conserapp.R;

import java.util.ArrayList;

public class SandwichCreator extends AppCompatActivity {

    ArrayList<String> listIngredientsCarne;
    ArrayList<String> listIngredientsVerdura;
    ArrayList<String> listIngredientsQueso;
    ArrayList<String> listIngredientsSalsas;
    ArrayList<String> listIngredientsEspecial;
    ArrayList<String> listSandwich;
    double ingredientPrice;
    double totalprice;
    String currentType;

    RecyclerView recycler;
    LinearLayout linearLayout;
    TextView txt_sandwich;

    DatabaseReference favs_table;
    DatabaseReference ing_table;
    FirebaseDatabase database;

    Originator originator;
    CareTaker careTaker;
    int totalSavedMementos;
    int positionMemento;

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
        toolbar.setTitle("Crear mi bocadillo");
        toolbar.setTitleTextColor(Color.rgb(255, 255, 255));
        setSupportActionBar(toolbar);

        originator = new Originator();
        careTaker = new CareTaker();
        totalSavedMementos = 0;
        positionMemento = -1;

        linearLayout = (LinearLayout) findViewById(R.id.generalLinearLayout);
        txt_sandwich = (TextView) linearLayout.findViewById(R.id.txt_sandwich);

        listIngredientsCarne = new ArrayList<String>();
        listIngredientsVerdura = new ArrayList<String>();
        listIngredientsQueso = new ArrayList<String>();
        listIngredientsSalsas = new ArrayList<String>();
        listIngredientsEspecial = new ArrayList<String>();

        listSandwich = new ArrayList<String>();

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
        else intent = new Intent(this, Home.class);
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

    public void addIngredientToSandwich(View view, String ingredientName) {
        String type = currentType;


        AlertFactory alertFactory = new AlertFactory();
        AlertParent alertParent = alertFactory.generateAlert(AlertEnum.REPETITION_SANDWICH);

        if (maxRepetitionIngredient(ingredientName)) {
            alertParent.printAlert(this);
        } else {
            listSandwich.add(ingredientName);

            saveMementoState();

            manageIngredientPrice(ingredientPrice, "add");

            txt_sandwich.setText(listSandwich.toString());
            
        }
    }

    public void addToCart(View view) {

        String productID = "Order " + String.valueOf(System.currentTimeMillis());
        String quantity = "1";
        String price = obtainPrice();
        String discount = "0";

        AlertFactory alertFactory = new AlertFactory();
        AlertParent alertParent = alertFactory.generateAlert(AlertEnum.EMPTY_SANDWICH);

        if (listSandwich.isEmpty()) alertParent.printAlert(this);
        else {
            orderRes = new Order(productID, listSandwich.toString(), quantity, price, discount);

            new Database(getBaseContext()).addToCart(orderRes);
            Toast.makeText(SandwichCreator.this, "Añadido al carrito!", Toast.LENGTH_SHORT).show();
        }

    }


    public void addToFavs(View view) {

        AlertFactory alertFactory = new AlertFactory();
        AlertParent alertParent = alertFactory.generateAlert(AlertEnum.EMPTY_SANDWICH);

        if (listSandwich.size() == 0) alertParent.printAlert(this);
        else {

            Intent popUp = new Intent(SandwichCreator.this, FavsPopWindow.class);

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
        totalSavedMementos++;
        positionMemento++;
    }

    public void resetMemento() {
        careTaker.cleanMementoList();
        positionMemento = -1;
        totalSavedMementos = 0;
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
        if (positionMemento >= 1) {
            positionMemento--;
            originator.getStateFromMemento(careTaker.get(positionMemento));
            listSandwich = originator.getState();
            listSandwich = (ArrayList<String>) listSandwich.clone();

            manageIngredientPrice(ingredientPrice, "subs");

            txt_sandwich.setText(listSandwich.toString());


        } else
            Toast.makeText(SandwichCreator.this, "Ninguna acción para deshacer", Toast.LENGTH_SHORT).show();
    }


    public void redoLastIngredient(View view) {
        if ((totalSavedMementos - 1) > positionMemento) {
            positionMemento++;
            originator.getStateFromMemento(careTaker.get(positionMemento));
            listSandwich = originator.getState();
            listSandwich = (ArrayList<String>) listSandwich.clone();

            manageIngredientPrice(ingredientPrice, "add");

            txt_sandwich.setText(listSandwich.toString());
        } else
           Toast.makeText(SandwichCreator.this, "Ninguna acción para rehacer", Toast.LENGTH_SHORT).show();
    }


    public void removeAllElements(View view) {
        if (listSandwich.size() != 0) {
            listSandwich.clear();

            resetMemento();
            saveMementoState();

            manageIngredientPrice(ingredientPrice, "clear");
            txt_sandwich.setText(listSandwich.toString());
        } else {
            Toast.makeText(SandwichCreator.this, "Ya tienes un bocadillo vacio!", Toast.LENGTH_SHORT).show();
        }

    }

    public void recycleDividerManager() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler.getContext(),
                DividerItemDecoration.VERTICAL);
        recycler.addItemDecoration(dividerItemDecoration);
    }

    public void buttonIngredientsManager(View view) {
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
                adapter = new SandwichCreatorAdapter(listIngredientsCarne, this, this);
                break;
            case "Verduras":
                adapter = new SandwichCreatorAdapter(listIngredientsVerdura, this, this);
                break;
            case "Queso":
                adapter = new SandwichCreatorAdapter(listIngredientsQueso, this, this);
                break;
            case "Salsas":
                adapter = new SandwichCreatorAdapter(listIngredientsSalsas, this, this);
                break;
            case "Especial":
                adapter = new SandwichCreatorAdapter(listIngredientsEspecial, this, this);
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
                    listIngredientsCarne.add(ing.getName());
                    break;
                case "Verduras":
                    listIngredientsVerdura.add(ing.getName());
                    break;
                case "Queso":
                    listIngredientsQueso.add(ing.getName());
                    break;
                case "Salsas":
                    listIngredientsSalsas.add(ing.getName());
                    break;
                case "Especial":
                    listIngredientsEspecial.add(ing.getName());
                    break;
            }
        }

        modifyAdapter("Carne_Pescado");
    }

}