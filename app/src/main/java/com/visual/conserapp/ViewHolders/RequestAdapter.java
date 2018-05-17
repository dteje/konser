package com.visual.conserapp.ViewHolders;

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
import com.visual.conserapp.Model.Order;
import com.visual.conserapp.Model.Request;
import com.visual.conserapp.Model.Request;
import com.visual.conserapp.R;
import com.visual.conserapp.Views.AdminHome;
import com.visual.conserapp.Views.Cart;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * Created by daniel on 15/05/2018.
 */

class RequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_name;
    public TextView txt_price;
    Button btn_done;

    public void setTxt_cart_price(TextView txt_price) {
        this.txt_price = txt_price;
    }

    public void setTxt_name(TextView txt_name) {
        this.txt_name = txt_name;
    }

    private ItemClickListener itemClickListener;

    public RequestViewHolder(View itemView) {
        super(itemView);
        txt_name = (TextView) itemView.findViewById(R.id.cart_adapter_item_name);
        txt_price = (TextView) itemView.findViewById(R.id.cart_adapter_item_price);
        btn_done = (Button) itemView.findViewById(R.id.item_admin_btn_done);

    }


    @Override
    public void onClick(View view) {
    }

}

public class RequestAdapter extends RecyclerView.Adapter<RequestViewHolder> {

    private List<Request> listData = new ArrayList<Request>();
    //private Context context;
    private AdminHome adminHome;

    public RequestAdapter(List<Request> listData, AdminHome adminHome) {
        this.adminHome = adminHome;
        this.listData = listData;
    }

    @Override
    public RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.adminHome);
        View itemView = layoutInflater.inflate(R.layout.item_admin_order, parent, false);
        return new RequestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RequestViewHolder holder, final int position) {

        String total = listData.get(position).getTotal();
        holder.txt_price.setText(total + "");
        String nombre = listData.get(position).getName();
        holder.txt_name.setText(nombre+"");
        holder.btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Request request = listData.get(position);
                request.setDone(true);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public Request getItem(int position) {
        return listData.get(position);
    }
}
