package com.visual.conserapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.visual.conserapp.AlertFactory.AlertFactory;
import com.visual.conserapp.AlertFactory.AlertParent;
import com.visual.conserapp.Model.MenuDia;
import com.visual.conserapp.Model.Order;
import com.visual.conserapp.R;

import java.util.ArrayList;


class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public TextView txt_menuName;
    Button btn_detalles;
    Button btn_anyadir;


    public void setMenuName(TextView menuName) {
        this.txt_menuName = menuName;
    }


    public RecyclerViewHolder(View itemView) {
        super(itemView);
        txt_menuName = (TextView) itemView.findViewById(R.id.home_adapter_item_name);
        btn_detalles = (Button) itemView.findViewById(R.id.home_adapter_btn_detalles);
        btn_anyadir = (Button) itemView.findViewById(R.id.home_adapter_btn_anyadir);
    }


    @Override
    public void onClick(View v) {

    }

}

public class HomeRecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    private ArrayList<MenuDia> listMenu = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference menu_table;
    private Context context;
    Order orderRes;

    public HomeRecyclerAdapter(ArrayList<MenuDia> listMenu, FirebaseDatabase database, DatabaseReference menu_table, Context context) {
        this.listMenu = listMenu;
        this.database = database;
        this.menu_table = menu_table;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.manage_menu_layout, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        String name = listMenu.get(position).getName();
        String type = listMenu.get(position).getName();
        final Double menuPrice = listMenu.get(position).getPrice();

        holder.txt_menuName.setText(name);

        holder.btn_detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.btn_anyadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orderId = "Order " + String.valueOf(System.currentTimeMillis());
                String quantity = "1";
                String price = String.valueOf(menuPrice);
                String discount = "0";

                AlertFactory alertFactory = new AlertFactory();
                AlertParent alertParent = alertFactory.generateAlert("EmptySandwich");

                if (listMenu.isEmpty()) alertParent.printAlert(context);
                else {
                    orderRes = new Order(orderId, listMenu.toString(), quantity, price, discount);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMenu.size();
    }

}
