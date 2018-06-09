package com.visual.conserapp.ViewHolders.CRUDList;

import android.content.Intent;
import android.view.View;

import com.visual.conserapp.Model.Ingredient;
import com.visual.conserapp.Views.CRUD.List.Ingredients;
import com.visual.conserapp.Views.CRUD.Modify.CrudEditIngredients;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 03/06/2018.
 */


public class CrudIngredientsAdapter extends CrudAdapter {

    List<Ingredient> listData;

    public CrudIngredientsAdapter(List<Object> objects, Ingredients ingredients) {
        super(objects, ingredients);
        this.listData = convertObjectsToIngredients(listDataObjects);
    }

    @Override
    public void onBindViewHolder(CrudViewHolder holder, final int position) {
        holder.txt_name.setText(listData.get(position).getName());
        holder.txt_price.setText(listData.get(position).getType());
        holder.txt_id.setText(listData.get(position).getId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(crud, CrudEditIngredients.class);
                intent.putExtra("id",listData.get(position).getId());
                crud.startActivity(intent);
            }
        });
    }

    private List<Ingredient> convertObjectsToIngredients(List<Object> listobjects) {
        List<Ingredient> ings = new ArrayList<>();
        for (Object o : listobjects) {
            ings.add(((Ingredient) o));
        }
        return ings;
    }

}

