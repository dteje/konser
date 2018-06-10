package com.visual.conserapp.Views;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.visual.conserapp.Database.Database;
import com.visual.conserapp.Model.Food;
import com.visual.conserapp.Model.Ingredient;
import com.visual.conserapp.Model.MenuDia;
import com.visual.conserapp.Model.Order;
import com.visual.conserapp.R;

import java.util.List;

public class DetailMenu extends AppCompatActivity {

    TextView plato1, plato2, plato3, idsPlato1, idsPlato2, idsPlato3;
    ImageView image;
    FloatingActionButton btn_add;
    ElegantNumberButton elegantNumberButton;
    CollapsingToolbarLayout collapsingToolbarLayout;

    FirebaseDatabase database;
    DatabaseReference foods;
    MenuDia menuDia;
    List<Food> listaPlatos;

    Food food;
    String food_id1, food_id2, food_id3;
    TextView nombreMenu;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_menu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Recogemos food de la firebase

        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Food");

        //Recibimos el Gson

        String gson = getIntent().getStringExtra("menu");
        menuDia = (new Gson()).fromJson(gson, MenuDia.class);


        //Asignamos elementos a los de la interfaz

        plato1 = (TextView) findViewById(R.id.nombre_plato_1);
        plato2 = (TextView) findViewById(R.id.nombre_plato_2);
        plato3 = (TextView) findViewById(R.id.nombre_plato_3);
        idsPlato1 = (TextView) findViewById(R.id.ingredientes_plato1);
        idsPlato2 = (TextView) findViewById(R.id.ingredientes_plato2);
        idsPlato3 = (TextView) findViewById(R.id.ingredientes_plato3);
        nombreMenu = (TextView) findViewById(R.id.nombre_menu);


        image = (ImageView) findViewById(R.id.img_sandwich);

        elegantNumberButton = (ElegantNumberButton) findViewById(R.id.btn_detail_number);

        btn_add = (FloatingActionButton) findViewById(R.id.btn_add);


        //Añadimos funcionalidad al botón añadir al carrito

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order pedido = new Order(menuDia.getName(),menuDia.getName(),"1",
                        menuDia.getPrice().toString(),"0");
                new Database(getBaseContext()).addToCart(pedido);
                Toast.makeText(DetailMenu.this,"Añadido al carrito!", Toast.LENGTH_SHORT).show();
            }

        });

        //Más asignaciones pero ahora cuando se mueva

        //collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_bar);
        //collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.ExpandedAppbar);
        //collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppbar);

        listaPlatos = menuDia.getListaPlatos();

        buildFood();
    }


    private void buildFood() {

        nombreMenu.setText(menuDia.getName().toUpperCase());

        //Plato1
        String nameP1 = listaPlatos.get(0).getName();
        plato1.setText("Plato1: "+ nameP1);
        ingredientsToDescription(listaPlatos.get(0).getListOfIngredientes(), 0);

        //Plato2
        String nameP2 = listaPlatos.get(1).getName();
        plato2.setText("Plato2: "+ nameP2);
        ingredientsToDescription(listaPlatos.get(1).getListOfIngredientes(), 1);


        //Plato3
        String nameP3 = listaPlatos.get(2).getName();
        plato3.setText("Plato3: "+ nameP3);
        ingredientsToDescription(listaPlatos.get(2).getListOfIngredientes(), 2);



    }

    /*private void getDetails(final String foodid) {
        foods.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                food = (dataSnapshot.child(foodid)).getValue(Food.class);
                System.out.println(food.toString());
                //ingredientsToDescription(food.getListOfIngredientes());
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }*/

    String ingredientes = "";
    private void ingredientsToDescription(final List<String> listOfIngredients, final int plato) {
        final DatabaseReference ingredients = database.getReference("Ingredient");
        ingredients.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(String ingredientId : listOfIngredients){
                    Ingredient ingredient = (dataSnapshot.child(ingredientId)).getValue(Ingredient.class);
                    ingredientes += ingredient.getName() + ", ";
                }
                if(plato==0) {
                    idsPlato1.setText("Compuesto por: " + ingredientes.substring(0, ingredientes.length() - 2));
                    ingredientes = "";
                }
                else if(plato==1){
                    idsPlato2.setText("Compuesto por: " + ingredientes.substring(0, ingredientes.length() - 2));
                    ingredientes = "";
                }
                else if(plato==2){
                    idsPlato3.setText("Compuesto por: " + ingredientes.substring(0, ingredientes.length() - 2));
                    ingredientes = "";
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

}
