package com.visual.conserapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.rey.material.widget.FloatingActionButton;
import com.visual.conserapp.AlertFactory.AlertFactory;
import com.visual.conserapp.AlertFactory.AlertParent;
import com.visual.conserapp.Database.Database;
import com.visual.conserapp.Model.MenuDia;
import com.visual.conserapp.Model.Order;
import com.visual.conserapp.R;
import com.visual.conserapp.Views.Detail;
import com.visual.conserapp.Views.DetailMenu;
import com.visual.conserapp.Views.Home;

import java.util.ArrayList;


class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public TextView txt_menuName, txt_platos;
    Button btn_detalles;
    android.support.design.widget.FloatingActionButton btn_anyadir;


    public void setMenuName(TextView menuName) {
        this.txt_menuName = menuName;
    }


    public RecyclerViewHolder(View itemView) {
        super(itemView);
        txt_menuName = (TextView) itemView.findViewById(R.id.home_adapter_item_name);
        txt_platos = (TextView) itemView.findViewById(R.id.home_adapter_platos);
        btn_anyadir = (android.support.design.widget.FloatingActionButton) itemView.findViewById(R.id.home_adapter_btn_anyadir);
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
    private Home home;

    public HomeRecyclerAdapter(ArrayList<MenuDia> listMenu, FirebaseDatabase database, DatabaseReference menu_table, Context context, Home home) {
        this.listMenu = listMenu;
        this.database = database;
        this.menu_table = menu_table;
        this.context = context;
        this.home = home;

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
        final Double menuPrice = listMenu.get(position).getPrice();
        String platos = listMenu.get(position).getPlatosOrdenados();


        holder.txt_menuName.setText(name);
        holder.txt_platos.setText(platos);

        final Context context3 = this.context;


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context3, DetailMenu.class);
                Gson gson = new Gson();
                intent.putExtra("menu", gson.toJson(listMenu.get(position)));
                home.startActivity(intent);
            }
        });

        final Context context2 = this.context;
        final String name2 = listMenu.get(position).getName();

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
                    orderRes = new Order(orderId, name2, quantity, price, discount);

                    new Database(context2).addToCart(orderRes);
                    Toast.makeText(context2, "Añadido al carrito!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMenu.size();
    }

}
