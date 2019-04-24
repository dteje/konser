package com.visual.conserapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.visual.conserapp.Database.Database;
import com.visual.conserapp.Interface.ItemClickListener;
import com.visual.conserapp.Model.Food;
import com.visual.conserapp.Model.Order;
import com.visual.conserapp.R;
import com.visual.conserapp.Views.CRUD.Modify.CrudEditIngredients;
import com.visual.conserapp.Views.Cart;
import com.visual.conserapp.Views.Detail;
import com.visual.conserapp.Views.Home;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * Created by daniel on 10/06/2018.
 */

class FoodsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView txt_cart_name, txt_cart_price;
    public ImageView img;
    public FloatingActionButton btn_add;
    ItemClickListener itemClickListener;


    public void setBtn(FloatingActionButton btn) {
        this.btn_add = btn;
    }

    public void setImage(ImageView img) {
        this.img = img;
    }

    public void setPrice(String txt_cart_price) {
        this.txt_cart_price.setText(txt_cart_price);
    }

    public void setName(String txt_cart_name) {
        this.txt_cart_name.setText(txt_cart_name);
    }


    public FoodsHolder(View itemView) {
        super(itemView);
        txt_cart_name = (TextView) itemView.findViewById(R.id.item_food_txt_name);
        txt_cart_price = (TextView) itemView.findViewById(R.id.item_food_txt_price);
        img = (ImageView) itemView.findViewById(R.id.item_food_img);
        btn_add = (FloatingActionButton) itemView.findViewById(R.id.item_food_fab);
    }


    @Override
    public void onClick(View view) {

    }
}

public class FoodAdapter extends RecyclerView.Adapter<FoodsHolder> {

    private List<Food> listData = new ArrayList<>();
    private Home home;
    private Context context;
    private Food food;

    public FoodAdapter(List<Food> listData, Home home) {
        this.home = home;
        this.listData = listData;
    }

    @Override
    public FoodsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.home);
        View itemView = layoutInflater.inflate(R.layout.item_food_home, parent, false);
        return new FoodsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FoodsHolder holder, final int position) {
        food = listData.get(position);
        holder.setName(food.getName());
        holder.setPrice(food.getPrice() + " €");
        String url = food.getImage();
        if (!url.isEmpty())
            Glide.with(home).load(url).into(holder.img);
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order pedido = new Order(listData.get(position).getID(), listData.get(position).getName(), "1",
                        listData.get(position).getPrice(), listData.get(position).getDiscount());
                new Database(home).addToCart(pedido);
                Toast.makeText(home, "Añadido al carrito!", Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home, Detail.class);
                intent.putExtra("id",listData.get(position).getID());
                home.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public Food getItem(int position) {
        return listData.get(position);
    }
}
