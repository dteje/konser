package com.visual.conserapp.Views.CRUD;

import android.view.View;

import com.visual.conserapp.Model.Food;
import com.visual.conserapp.Model.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 03/06/2018.
 */



public class CrudIngredientsAdapter extends CrudAdapter {

    List<Ingredient> listData;

    public CrudIngredientsAdapter(List<Object> listasobject, Ingredients ingredients) {
        super(listasobject, ingredients);

        this.listData = convertObjectsToIngredients(listDataObjects);
    }

    @Override
    public void onBindViewHolder(CrudViewHolder holder, int position) {
        holder.txt_name.setText(listData.get(position).getName());
        holder.txt_price.setText(listData.get(position).getType());
        /*
        holder.txt_id.setText(" ");*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("A");

            }
        });
    }

    private List<Ingredient> convertObjectsToIngredients(List<Object> listobjects){
        List<Ingredient> ings = new ArrayList<>();
        for(Object o : listobjects){
            ings.add(((Ingredient) o));
        }
        return ings;
    }


}

