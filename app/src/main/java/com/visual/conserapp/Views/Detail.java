package com.visual.conserapp.Views;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.visual.conserapp.Database.Database;
import com.visual.conserapp.Model.Food;
import com.visual.conserapp.Model.Ingredient;
import com.visual.conserapp.Model.Order;
import com.visual.conserapp.R;

import java.io.File;
import java.util.List;

public class Detail extends AppCompatActivity {

    TextView tv_name, tv_price, tv_desc;
    ImageView image;
    FloatingActionButton btn_add;
    ElegantNumberButton elegantNumberButton;
    CollapsingToolbarLayout collapsingToolbarLayout;

    FirebaseDatabase database;
    DatabaseReference foods;

    Food food;
    String food_id;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Añadir plato");
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Food");

        tv_desc = (TextView) findViewById(R.id.tv_detail_description);
        tv_name = (TextView) findViewById(R.id.tv_detail_name);
        tv_price = (TextView) findViewById(R.id.tv_detail_price);

        image = (ImageView) findViewById(R.id.img_sandwich);

        elegantNumberButton = (ElegantNumberButton) findViewById(R.id.btn_detail_number);

        btn_add = (FloatingActionButton) findViewById(R.id.btn_addSandwich);


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order pedido = new Order(food_id, food.getName(), elegantNumberButton.getNumber().toString(),
                        food.getPrice(), food.getDiscount());
                new Database(getBaseContext()).addToCart(pedido);
                Toast.makeText(Detail.this, "Añadido al carrito!", Toast.LENGTH_SHORT).show();
            }

        });

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_bar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppbar);

        food_id = getIntent().getStringExtra("id");
        getDetails(food_id);
    }

    private void getDetails(final String foodid) {
        foods.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                food = (dataSnapshot.child(foodid)).getValue(Food.class);
                if(!food.getImage().isEmpty())
                Picasso.with(getBaseContext()).load(food.getImage()).into(image);
                collapsingToolbarLayout.setTitle(food.getName());
                if(food.getDescription().isEmpty())ingredientsToDescription(food.getListOfIngredients());
                else tv_desc.setText(food.getDescription());
                tv_name.setText(food.getName());
                tv_price.setText(food.getPrice().toString());
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    String ingredientes = "   ";

    private void ingredientsToDescription(final List<String> listOfIngredients) {
        DatabaseReference ingredients = database.getReference("Ingredient");
        ingredients.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (String ingredientId : listOfIngredients) {
                    Ingredient ingredient = (dataSnapshot.child(ingredientId)).getValue(Ingredient.class);
                    if(ingredient!=null)ingredientes += ingredient.getName() + ", ";
                }
                tv_desc.setText("Compuesto por: " + ingredientes.substring(0, ingredientes.length() - 2));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    public boolean onNavSuperior(MenuItem menuitem) {
        View view = menuitem.getActionView();
        int id = menuitem.getItemId();
        Intent intent;
        if (id == R.id.cart_id) intent = new Intent(this, Cart.class);
        else if (id == R.id.sandwitchCreator_id) intent = new Intent(this, SandwitchCreator.class);
        else intent = new Intent(this, Home.class);
        startActivity(intent);
        return true;
    }

}
