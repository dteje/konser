package com.visual.conserapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.visual.conserapp.Database.Database;
import com.visual.conserapp.Interface.ItemClickListener;
import com.visual.conserapp.Model.Drink;
import com.visual.conserapp.Model.Food;
import com.visual.conserapp.Model.Order;
import com.visual.conserapp.R;
import com.visual.conserapp.Views.Detail;
import com.visual.conserapp.Views.Home;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 10/06/2018.
 */

class DrinkHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView txt_name, txt_price;
    public ImageView img;
    ItemClickListener itemClickListener;
    public FloatingActionButton btn_add;


    public void setBtn_add(FloatingActionButton btn_add) {
        this.btn_add = btn_add;
    }

    public void setImage(ImageView img) {
        this.img = img;
    }

    public void setPrice(String txt_cart_price) {
        this.txt_price.setText(txt_cart_price);
    }

    public void setName(String txt_cart_name) {
        this.txt_name.setText(txt_cart_name);
    }


    public DrinkHolder(View itemView) {
        super(itemView);
        txt_name = (TextView) itemView.findViewById(R.id.item_drink_txt_name);
        txt_price = (TextView) itemView.findViewById(R.id.item_drink_txt_price);
        img = (ImageView) itemView.findViewById(R.id.item_drink_img);
        btn_add = (FloatingActionButton) itemView.findViewById(R.id.item_drink_fab);
    }


    @Override
    public void onClick(View view) {

    }
}

public class DrinkAdapter extends RecyclerView.Adapter<DrinkHolder> {

    private List<Drink> listData = new ArrayList<>();
    private Home home;
    private Context context;
    private Drink drink;

    public DrinkAdapter(List<Drink> listData, Home home) {
        this.home = home;
        this.listData = listData;
    }

    @Override
    public DrinkHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.home);
        View itemView = layoutInflater.inflate(R.layout.item_drink_home, parent, false);
        return new DrinkHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DrinkHolder holder, final int position) {
        drink = listData.get(position);
        holder.setName(drink.getName());
        holder.setPrice(drink.getPrice() + " €");
        String url = drink.getImage();
        if (!url.isEmpty())
            Picasso.with(home).load(url).into(holder.img);
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order pedido = new Order(listData.get(position).getID(), listData.get(position).getName(), "1",
                        drink.getPrice(), "0");
                new Database(home).addToCart(pedido);
                Toast.makeText(home, "Añadido al carrito!", Toast.LENGTH_SHORT).show();
            }
        });
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home, Detail.class);
                intent.putExtra("id",listData.get(position).getID());
                home.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public Drink getItem(int position) {
        return listData.get(position);
    }
}
