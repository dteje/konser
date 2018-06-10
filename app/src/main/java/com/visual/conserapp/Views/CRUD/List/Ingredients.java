package com.visual.conserapp.Views.CRUD.List;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.visual.conserapp.Memento.CareTaker;
import com.visual.conserapp.Memento.Memento;
import com.visual.conserapp.Memento.Originator;
import com.visual.conserapp.Model.Ingredient;
import com.visual.conserapp.Adapter.CRUDList.CrudIngredientsAdapter;
import com.visual.conserapp.Views.CRUD.Modify.CrudEditIngredients;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 29/05/2018.
 */

public class Ingredients extends Crud {

    String toolbarTitle = "Ingredientes";


    ArrayList<Ingredient> ingredients;
    ArrayList<Ingredient> ingredientsfiltered;
    CareTaker careTaker = new CareTaker();
    Originator originator = new Originator();
    int savedFiles = 0, currentIngredient = 0;
    ArrayList<Memento> testListMemento = careTaker.getMementos();

    @Override
    protected void onCreateChild() {
        this.ingredients = new ArrayList<>();
        this.ingredientsfiltered = new ArrayList<>();
    }

    @Override
    Object getDataFromSnapshot(DataSnapshot dataSnapshot) {
        return dataSnapshot.getValue(Ingredient.class);
    }

    @Override
    DatabaseReference createTable() {
        return database.getReference("Ingredient");
    }

    @Override
    protected void setFABOnClick() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CrudEditIngredients.class);
                intent.putExtra("id", "new");
                intent.putExtra("newid", newId++ + "");
                startActivity(intent);
            }
        });
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentIngredient >= 1) {
                    currentIngredient--;

                    ArrayList<Ingredient> listIng = originator.restoreFromMemento(careTaker.getMemento(0));
                    ingredients = listIng;

                    Log.d("list undo", ingredients.toString());

                }
            }
        });

        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                originator.setListIngredient(ingredients);
                careTaker.addMemento(originator.storeInMemento());

                savedFiles++;
                currentIngredient++;

/*
                Log.d("memento funtionality 1", String.valueOf(testListMemento.get(0).getSavedListIngredient().size()));
                Log.d("memento funtionality 2", String.valueOf(testListMemento.get(1).getSavedListIngredient().size()));


                if((savedFiles - 1) > currentIngredient){
                    currentIngredient++;

                    ArrayList<Ingredient> listIng = originator.restoreFromMemento(careTaker.getMemento(currentIngredient));
                    ingredients = listIng;
                }
                */
            }
        });
    }

    @Override
    void displayData() {
        crudAdapter = new CrudIngredientsAdapter(objects, this);
        recyclerView.setAdapter(crudAdapter);

    }

    @Override
    protected void collectDataCrud(DataSnapshot dataSnapshot) {
        clearData(true);
        for (DataSnapshot d : dataSnapshot.getChildren()) {
            Ingredient ingredient = d.getValue(Ingredient.class);
            ingredients.add(ingredient);

            objects.add((Object) ingredient);
            newId = Integer.parseInt(ingredient.getId());
        }


        newId++;
        objectsfiltered = objects;
        displayData();
    }

    @Override
    String getToolbarTitle() {
        return toolbarTitle;
    }

    void clearData(boolean all) {
        ingredientsfiltered.clear();
        objectsfiltered.clear();
        objects.clear();
        if (all) ingredients.clear();
    }

    @Override
    protected boolean search(String s) {
        clearData(false);
        s = s.toLowerCase();
        boolean flag = false;
        for (Ingredient i : ingredients) {
            if (i.getName().toLowerCase().contains(s) || i.getType().toLowerCase().contains(s) || i.getId().contains(s)) {
                ingredientsfiltered.add(i);
                objectsfiltered.add((Object) i);
                flag = true;
            }
        }
        displayData();
        return flag;
    }
}
