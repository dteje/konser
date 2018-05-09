package com.visual.conserapp.ViewHolders;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.visual.conserapp.Interface.ItemClickListener;
import com.visual.conserapp.Model.Order;
import com.visual.conserapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * Created by daniel on 07/05/2018.
 */

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txt_cart_name;

    public void setTxt_cart_price(TextView txt_cart_price) {
        this.txt_cart_price = txt_cart_price;
    }

    public TextView txt_cart_price;
    public ImageView img;

    public void setTxt_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }

    private ItemClickListener itemClickListener;

    public CartViewHolder(View itemView) {
        super(itemView);
        txt_cart_name = (TextView) itemView.findViewById(R.id.cart_adapter_item_name);
        txt_cart_price = (TextView) itemView.findViewById(R.id.cart_adapter_item_price);
        img = (ImageView) itemView.findViewById(R.id.cart_adapter_item_image);


    }

    @Override
    public void onClick(View view){

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>{

    private List<Order> listData = new ArrayList<Order>();
    private Context context;

    public CartAdapter(List<Order> cart,Context context){
        this.context = context;
        this.listData = cart;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View itemView = layoutInflater.inflate(R.layout.cart_layout,parent,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        int quant = parseInt(listData.get(position).getQuantity());
        double price = parseDouble(listData.get(position).getPrice());
        double total = quant * price;
        String name = listData.get(position).getProductName();
        TextDrawable textDrawable = TextDrawable.builder().buildRound(""+quant, Color.RED);
        holder.img.setImageDrawable(textDrawable);
        holder.txt_cart_price.setText(price+" €");
        holder.txt_cart_name.setText(name+"");

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
