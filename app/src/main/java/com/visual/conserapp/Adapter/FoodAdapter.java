package com.visual.conserapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.visual.conserapp.Database.Database;
import com.visual.conserapp.Interface.ItemClickListener;
import com.visual.conserapp.Model.Food;
import com.visual.conserapp.Model.Order;
import com.visual.conserapp.R;
import com.visual.conserapp.Views.Cart;
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
    ImageView img;
    Button btn_add;
    ItemClickListener itemClickListener;


    public void setBtn(Button btn){this.btn_add = btn; }
    public void setImage(ImageView img) { this.img = img;}
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
        btn_add = (Button) itemView.findViewById(R.id.item_food_btn);
        img = (ImageView) itemView.findViewById(R.id.item_food_img);
    }


    @Override
    public void onClick(View view) {
    }

}

public class FoodAdapter extends RecyclerView.Adapter<FoodsHolder> {

    private List<Food> listData = new ArrayList<>();
    private Home home;

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
        holder.setName(listData.get(position).getName());
        holder.setPrice(listData.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
    public Food getItem(int position) {
        return listData.get(position);
    }
}
