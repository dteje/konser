package com.visual.conserapp.Views.CRUD;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.visual.conserapp.Interface.ItemClickListener;
import com.visual.conserapp.Model.Food;
import com.visual.conserapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 03/06/2018.
 */



public class CrudFoodAdapter extends CrudAdapter {

    List<Food> listData;

    public CrudFoodAdapter(List<Object> listasobject, Foods foods) {
        super(listasobject, foods);

        this.listData = convertObjectsToFoods(listDataObjects);
    }

    @Override
    public void onBindViewHolder(CrudViewHolder holder, int position) {
        holder.txt_name.setText(listData.get(position).getName());
        holder.txt_price.setText(listData.get(position).getPrice() + "€");
        holder.txt_id.setText(listData.get(position).getID());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("A");
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

