package com.visual.conserapp.ViewHolders.CRUDList;

import android.content.Intent;
import android.view.View;

import com.visual.conserapp.Model.Food;
import com.visual.conserapp.Views.CRUD.Retrieve.Crud;
import com.visual.conserapp.Views.CRUD.Retrieve.Foods;
import com.visual.conserapp.Views.CRUD.Update.CrudEditFood;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 03/06/2018.
 */



public class CrudFoodAdapter extends CrudAdapter {

    List<Food> listData;

    public CrudFoodAdapter(List<Object> objects, Foods foods) {
        super(objects, foods);
        this.listData = convertObjectsToFoods(listDataObjects);
    }

    @Override
    public void onBindViewHolder(CrudViewHolder holder, final int position) {
        holder.txt_name.setText(listData.get(position).getName());
        holder.txt_price.setText(listData.get(position).getPrice() + "€");
        holder.txt_id.setText(listData.get(position).getID());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(crud, CrudEditFood.class);
                intent.putExtra("id", listData.get(position).getID());
                crud.startActivity(intent);
            }
        });
    }

    private List<Food> convertObjectsToFoods(List<Object> listobjects){
        List<Food> foods = new ArrayList<>();
        for(Object o : listobjects){
            foods.add(((Food) o));
        }
        return foods;
    }


}

