package com.visual.conserapp.Views.CRUD.List;

import android.content.Intent;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.visual.conserapp.Model.Ingredient;
import com.visual.conserapp.Adapter.CRUDList.CrudIngredientsAdapter;
import com.visual.conserapp.Views.CRUD.Modify.CrudEditIngredients;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 29/05/2018.
 */

public class IngredientList extends CrudList {

    String toolbarTitle = "Ingredientes";

    List<Ingredient> ingredients, ingredientsfiltered;

    @Override
    protected void onCreateChild() {
        this.ingredients = new ArrayList<>();
        this.ingredientsfiltered = new ArrayList<>();
    }

    @Override
    protected DatabaseReference getTableFromSubclass() {
        return database.getReference("Ingredient");
    }

    @Override
    protected void setFABOnClick() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, CrudEditIngredients.class);
                intent.putExtra("id","new");
                intent.putExtra("newid",newId++ +"");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void displayData() {
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
    protected String getToolbarTitleFromSubclass() {
        return toolbarTitle;
    }

    void clearData(boolean all){
        ingredientsfiltered.clear();
        objectsfiltered.clear();
        objects.clear();
        if(all)ingredients.clear();
    }

    @Override
    protected boolean search(String s) {
        clearData(false);
        s = s.toLowerCase();
        boolean flag = false;
        for(Ingredient i : ingredients){
            if(i.getName().toLowerCase().contains(s) || i.getType().toLowerCase().contains(s) || i.getId().contains(s)){
                ingredientsfiltered.add(i);
                objectsfiltered.add((Object) i);
                flag = true;
            }
        }
        displayData();
        return flag;
    }
}
