package com.visual.conserapp.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.visual.conserapp.Interface.ItemClickListener;
import com.visual.conserapp.Model.Ingredient;
import com.visual.conserapp.R;


class IngredientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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

    public IngredientViewHolder(View itemView) {
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

