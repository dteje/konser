package com.visual.conserapp.ViewHolders;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.visual.conserapp.Database.Database;
import com.visual.conserapp.Interface.ItemClickListener;
import com.visual.conserapp.Model.Order;
import com.visual.conserapp.R;
import com.visual.conserapp.Views.Cart;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * Created by daniel on 07/05/2018.
 */

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_cart_name;
    ElegantNumberButton btn_detail_number;
    public TextView txt_cart_price;
    public ImageView img;
    Button btn_eliminar;

    public void setTxt_cart_price(TextView txt_cart_price) {
        this.txt_cart_price = txt_cart_price;
    }

    public void setTxt_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }

    public String getQuantity() {
        return btn_detail_number.getNumber();
    }

    private ItemClickListener itemClickListener;

    public CartViewHolder(View itemView) {
        super(itemView);

        txt_cart_name = (TextView) itemView.findViewById(R.id.cart_adapter_item_name);
        txt_cart_price = (TextView) itemView.findViewById(R.id.cart_adapter_item_price);
        btn_detail_number = (ElegantNumberButton) itemView.findViewById(R.id.cart_adapter_btn_detail_number);
        btn_eliminar = (Button) itemView.findViewById(R.id.cart_adapter_btn_eliminar);

    }


    @Override
    public void onClick(View view) {
    }

}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> listData = new ArrayList<Order>();
    //private Context context;
    private Cart cart;

    public CartAdapter(List<Order> listData, Cart cart) {
        this.cart = cart;
        this.listData = listData;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(this.cart);
        View itemView = layoutInflater.inflate(R.layout.cart_layout, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CartViewHolder holder, final int position) {
        int quant = parseInt(listData.get(position).getQuantity());
        holder.btn_detail_number.setNumber(quant + "");
        holder.btn_detail_number.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Order order = listData.get(position);
                order.setQuantity(String.valueOf(newValue));
                new Database(cart).updateCart(order);
                cart.updateCart();
            }
        });
        double price = parseDouble(listData.get(position).getPrice());
        double total = quant * price;
        String name = listData.get(position).getProductName();
        holder.txt_cart_price.setText(price + " €");
        holder.txt_cart_name.setText(name + "");
        holder.btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = listData.get(position);
                new Database(cart).removeFromCart(order);
                cart.loadCart();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public Order getItem(int position) {
        return listData.get(position);
    }
}
